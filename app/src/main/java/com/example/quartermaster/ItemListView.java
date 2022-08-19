package com.example.quartermaster;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ItemListView extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mItemViewBtn;
    TextView mUID, mEnterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_view);
        mItemViewBtn = findViewById(R.id.searchBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mUID = findViewById(R.id.UID);
        mEnterId = findViewById(R.id.ItemIdSearch);


        mUID.setText(Html.fromHtml("<b>Item ID's:</b>"));

        fStore.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<QueryDocumentSnapshot> docList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    docList.add(document);

//                    mUID.append(document.getId());

                    fStore.collection("Items").document(document.getId()).get().addOnCompleteListener(tasks -> {
                        if (tasks.isSuccessful()) {
                            DocumentSnapshot document2 = tasks.getResult();
                            if (document2.exists()) {

                                mUID.append("\n");
                                mUID.append("\n -> ");
                                mUID.append(document.getId());
                                Map<String, Object> map = document2.getData();
                                assert map != null;
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    mUID.append("\n");
                                    mUID.append(entry.getValue().toString());


                                }
                            }
                        } else {
                            Toast.makeText(ItemListView.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });


        mItemViewBtn.setOnClickListener(v -> {
            String Uid = mEnterId.getText().toString();
            Intent i = new Intent(getApplicationContext(), ItemView.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        });
    }


}
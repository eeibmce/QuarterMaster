package com.example.quartermaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ItemView extends AppCompatActivity {

    TextView mUID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Task<QuerySnapshot> itemsId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
String[] arrayOfItems = {"Hello", "World"};
String listOfItems = "";
Button itemViewButton;
TextView enterId;
TextView objectInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        mUID = findViewById(R.id.UID);
        itemViewButton = findViewById(R.id.searchButton);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        enterId = findViewById(R.id.editTextTextPersonName);
        objectInfo = findViewById(R.id.textView5);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        String documentReference = fStore.collection("Items").getParent().toString();


        mUID.setText("Item ID's");

        fStore.collection("Items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<QueryDocumentSnapshot> docList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docList.add(document);
                        mUID.append("\n");
                        mUID.append(document.getId());

                    }
//                   mUID.setText((CharSequence) docList);
                }
            }
        });

        itemViewButton.setOnClickListener(v -> {
            fStore.collection("Items").document(enterId.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            Map<String, Object> map = document.getData();
                            objectInfo.setText("");
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                objectInfo.append("\n");
                                objectInfo.append(entry.getValue().toString());
//                                Log.d("TAG", entry.getValue().toString());

                            }
                        }
                    }else{
                        Toast.makeText(ItemView.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        });
    }








}
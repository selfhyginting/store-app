package com.example.appsselfhy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;

import com.example.appsselfhy.adapterRegister.ListAdapter;
import com.example.appsselfhy.adapterRegister.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText name, messageReview;
    ListView listView;
    List<User> listIndex;
    String selectedID;
    Button tambah, edit, hapus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.list_view);
        name = findViewById(R.id.name);
        messageReview = findViewById(R.id.messageReviewet);
        tambah = findViewById(R.id.tambah);
        edit = findViewById(R.id.edit);
        hapus = findViewById(R.id.hapus);
        listIndex = new ArrayList<>();
        tambah.setVisibility(View.VISIBLE);
        edit.setVisibility(View.INVISIBLE);
        hapus.setVisibility(View.INVISIBLE);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                User user = listIndex.get(position);
                name.setText(user.getName());
                messageReview.setText(user.getmessageReview());
                selectedID = user.getId();
                tambah.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                hapus.setVisibility(View.VISIBLE);
                return true;
            }
        });
        GetData();


    }// tutup onCreate


    private void GetData() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listIndex.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    listIndex.add(user);
                }

                ListAdapter listAdapter = new ListAdapter(ReviewActivity.this, listIndex);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDatabase.child("users").addValueEventListener(valueEventListener);
    }// tutup getData

    public void tambahuser(View view) {
        String name = this.name.getText().toString();
        String messageReview = this.messageReview.getText().toString();

        String id = String.valueOf(System.currentTimeMillis());
        User user = new User(id, name, messageReview);

        mDatabase.child("users").child(id).setValue(user);
    }

    public void Edit(View view) {
        DatabaseReference reference = mDatabase.child("users").child(selectedID);
        User user = new User(selectedID, name.getText().toString(), messageReview.getText().toString());
        reference.setValue(user);

        selectedID = "";
        name.setText("");
        messageReview.setText("");
        tambah.setVisibility(View.VISIBLE);
        edit.setVisibility(View.INVISIBLE);
        hapus.setVisibility(View.INVISIBLE);
    }

    public void Hapus(View view) {
        DatabaseReference reference = mDatabase.child("users").child(selectedID);
        reference.removeValue();
        selectedID = "";
        name.setText("");
        messageReview.setText("");
        tambah.setVisibility(View.VISIBLE);
        edit.setVisibility(View.INVISIBLE);
        hapus.setVisibility(View.INVISIBLE);
    }
}
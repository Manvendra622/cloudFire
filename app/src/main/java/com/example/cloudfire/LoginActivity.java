package com.example.cloudfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //I have learned cloud firestore from Source: https://www.youtube.com/watch?v=lz6euLh6zAM
    EditText emailId,txtpassword;
    FirebaseFirestore firebaseFirestore;
//    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailId=findViewById(R.id.email);
        txtpassword=findViewById(R.id.password);
        Button login=findViewById(R.id.loginButton);
        TextView register=findViewById(R.id.newRegisterButton);

        firebaseFirestore=FirebaseFirestore.getInstance();

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                if(emailId.getText().toString().equals("")){
                    Toast.makeText(this, "Enter the email", Toast.LENGTH_SHORT).show();
                }
                else if(txtpassword.getText().toString().equals("")){
                    Toast.makeText(this, "Enter the password", Toast.LENGTH_SHORT).show();
                }

                firebaseFirestore.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                        String emailCapture=documentSnapshot.getString("Email");
                                        String passwordCapture=documentSnapshot.getString("Password");

                                        String emailMatch=emailId.getText().toString().trim();
                                        String passwordMatch=txtpassword.getText().toString().trim();

                                        if(emailCapture.equalsIgnoreCase(emailMatch) & passwordCapture.equals(passwordMatch)){
                                            Intent home= new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(home);
                                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                break;

            case R.id.newRegisterButton:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
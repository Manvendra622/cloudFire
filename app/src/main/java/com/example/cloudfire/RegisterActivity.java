package com.example.cloudfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //I have learned cloud firestore from Source: https://www.youtube.com/watch?v=lz6euLh6zAM
    EditText txtName,txtPhone,emailId, password,confirmPassword;

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName=findViewById(R.id.name);
        txtPhone=findViewById(R.id.mobile);
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.passwordConfirm);

        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("users").document();
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtName.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the name", Toast.LENGTH_SHORT).show();

                } else if (emailId.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the valid email", Toast.LENGTH_SHORT).show();

                } else if (txtPhone.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the mobile number", Toast.LENGTH_SHORT).show();

                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();

                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("city","Land");
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Log.d("einv","Sorry");
                                Toast.makeText(RegisterActivity.this, "Sorry, This user already Exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> register_User = new HashMap<>();
                                register_User.put("Name", txtName.getText().toString());
                                register_User.put("Phone", txtPhone.getText().toString());
                                register_User.put("Email", emailId.getText().toString());
                                register_User.put("Password", password.getText().toString());
                                Log.d("Name",txtName.getText().toString());

                                firebaseFirestore.collection("users")
                                        .add(register_User)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("Email","Users added");
                                                Toast.makeText(RegisterActivity.this, "User Added sucessfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });

        findViewById(R.id.loginNowButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginPage);
            }
        });

    }

}
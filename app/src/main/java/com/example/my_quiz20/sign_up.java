package com.example.my_quiz20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.my_quiz20.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class sign_up extends AppCompatActivity {
ActivitySignUpBinding binding;
FirebaseAuth firebaseAuth;
ProgressBar progressBar;
FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
// check whether the user is new or purana
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(sign_up.this, MainActivity.class));
            finish();
        }
        binding.createnewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, name,refercode;
                email = binding.emailBox.getText().toString();
                password = binding.PasswordBox.getText().toString();
                name = binding.nameBox.getText().toString();
                refercode = binding.ReferBox.getText().toString();

                User user=new User(name,email,password,refercode);
                if (TextUtils.isEmpty(name)) {
                    binding.nameBox.setError("Name cannot be Empty");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    binding.emailBox.setError("Email cannot be Empty");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.PasswordBox.setError("Password cannot be Empty");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid=task.getResult().getUser().getUid();
                            database.collection("users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                  if(task.isSuccessful()){
                                      progressBar.setVisibility(View.INVISIBLE);
                                      startActivity(new Intent(sign_up.this, MainActivity.class));
                                      finish();
                                  }else {
                                      Toast.makeText(sign_up.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                  }
                                }
                            });
                        } else {
                            Toast.makeText(sign_up.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        binding.LOGINid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_up.this, login.class));
                finish();
            }
        });



    }
}
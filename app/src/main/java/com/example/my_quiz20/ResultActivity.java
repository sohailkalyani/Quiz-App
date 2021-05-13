package com.example.my_quiz20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_quiz20.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
ActivityResultBinding binding;
int POINTS=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswer=getIntent().getIntExtra("correct",0);
        int totalQuestion=getIntent().getIntExtra("total",0);
        long points=correctAnswer*POINTS;

        binding.score.setText(String.format("%d/%d",correctAnswer,totalQuestion));
        binding.earned.setText(String.valueOf(points));

        FirebaseFirestore database =FirebaseFirestore.getInstance();
        database.collection("users").document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultActivity.this,QuizActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
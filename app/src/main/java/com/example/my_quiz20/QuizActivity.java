package com.example.my_quiz20;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_quiz20.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
ArrayList<Question> questions;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer=0;
int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions=new ArrayList<>();
        database=FirebaseFirestore.getInstance();
        String catId=getIntent().getStringExtra("catId");

        Random random=new Random();
        int rand=random.nextInt(1);
        database.collection("categories").document(catId)
                .collection("questions").whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index").limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size()<10){
                    database.collection("categories").document(catId)
                            .collection("questions").whereLessThanOrEqualTo("index",rand)
                            .orderBy("index").limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot snapshot: queryDocumentSnapshots){
                                    Question question =snapshot.toObject(Question.class);
                                    questions.add(question);
                            }
                            setNextQuestion();

                        }
                    });
                }
                else {
                    for(DocumentSnapshot snapshot: queryDocumentSnapshots){
                        Question question =snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }

            }
        });
        resetTimer();

        binding.quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void resetTimer(){
        timer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }


    void showAnswer(){
        if(question.getAnswer().equals(binding.option1.getText().toString())){
            setNextQuestion();
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(question.getAnswer().equals(binding.option2.getText().toString())){
            setNextQuestion();
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if(question.getAnswer().equals(binding.option3.getText().toString())){
            setNextQuestion();
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (question.getAnswer().equals(binding.option4.getText().toString())){
            setNextQuestion();
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }


    }

    void setNextQuestion(){
        if(timer!=null)
            timer.cancel();
        timer.start();
        if(index<questions.size()){
            binding.qnsCounter.setText(String.format("%d/%d",(index+1),questions.size()));
            question=questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }

    public void checkAnswer(TextView textView){
        String selectedAnswer=textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
            setNextQuestion();
        }
        else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
            setNextQuestion();
        }
    }

public void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
}

    public void onClick(View view){
        switch (view.getId()){
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if(timer!=null)
                    timer.cancel();
                TextView selected=(TextView) view;
                checkAnswer(selected);
                break;
            case R.id.next:
                reset();
                if(index<=questions.size()){
                    index++;
                    setNextQuestion();
                }
                else {
                    Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswer);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
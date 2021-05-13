package com.example.my_quiz20;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_quiz20.SpinWheel.LuckyWheelView;
import com.example.my_quiz20.SpinWheel.model.LuckyItem;
import com.example.my_quiz20.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {
ActivitySpinnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<LuckyItem> data =new ArrayList<>();

        LuckyItem item1=new LuckyItem();
        item1.topText="5";
        item1.secondaryText="Coins";
        item1.color = Color.parseColor("#000000");
        item1.textColor = Color.parseColor("#ffffff");
        data.add(item1);

        LuckyItem item2=new LuckyItem();
        item2.topText="10";
        item2.secondaryText="Coins";
        item2.color = Color.parseColor("#000000");
        item2.textColor = Color.parseColor("#ffffff");
        data.add(item2);

        LuckyItem item3 =new LuckyItem();
        item3.topText="15";
        item3.secondaryText="Coins";
        item3.color = Color.parseColor("#000000");
        item3.textColor = Color.parseColor("#ffffff");
        data.add(item3);

        LuckyItem item4 =new LuckyItem();
        item4.topText="0";
        item4.secondaryText="Coins";
        item4.color = Color.parseColor("#000000");
        item4.textColor = Color.parseColor("#ffffff");
        data.add(item4);

        LuckyItem item5 =new LuckyItem();
        item5.topText="20";
        item5.secondaryText="Coins";
        item5.color = Color.parseColor("#000000");
        item5.textColor = Color.parseColor("#ffffff");
        data.add(item5);

        LuckyItem item6 =new LuckyItem();
        item6.topText="25";
        item6.secondaryText="Coins";
        item6.color = Color.parseColor("#000000");
        item6.textColor = Color.parseColor("#ffffff");
        data.add(item6);

        LuckyItem item7 =new LuckyItem();
        item7.topText="0";
        item7.secondaryText="Coins";
        item7.color = Color.parseColor("#000000");
        item7.textColor = Color.parseColor("#ffffff");
        data.add(item7);

        LuckyItem item8 =new LuckyItem();
        item8.topText="50";
        item8.secondaryText="Coins";
        item8.color = Color.parseColor("#000000");
        item8.textColor = Color.parseColor("#ffffff");
        data.add(item8);

        binding.luckyWheelView.setData(data);
        binding.luckyWheelView.setRound(5);

        binding.ghumbaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomNo=r.nextInt(8);

                binding.luckyWheelView.startLuckyWheelWithTargetIndex(randomNo);
            }
        });
        binding.luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCash(index);
            }
        });

        }
        public void updateCash(int index){
        long cash=0;
        switch (index){
            case 0:
                cash=5;
                break;
            case 1:
                cash=10;
                break;
            case 2:
                cash=15;
                break;
            case 3:
                cash=0;
                break;
            case 4:
                cash=20;
                break;
            case 5:
                cash=25;
                break;
            case 6:
                cash=0;
                break;
            case 7:
                cash=50;
                break;
        }
            FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection("users").document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SpinnerActivity.this, "Coins has been Addded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        }
    }

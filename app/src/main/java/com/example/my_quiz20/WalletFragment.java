package com.example.my_quiz20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.my_quiz20.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {


    public WalletFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentWalletBinding binding;
    FirebaseFirestore database;
User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentWalletBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();

        database.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user=documentSnapshot.toObject(User.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));
            }
        });

binding.sendReq.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(user.getCoins()>50000){
            String uid=FirebaseAuth.getInstance().getUid();
            String upi=binding.emailBox.getText().toString();
            WithdrawReq request = new WithdrawReq(uid,upi,user.getName());
            database
                    .collection("withdraw")
                    .document()
                    .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Request send sucessfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getContext(), "You need more coins to get withdraw", Toast.LENGTH_SHORT).show();
        }

    }
});


        return binding.getRoot();


    }
}
package com.example.my_quiz20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.my_quiz20.databinding.FragmentLeaderShipBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderShipFragment extends Fragment {


    public LeaderShipFragment() {

    }

//    public static LeaderShipFragment newInstance(String param1, String param2) {
//        LeaderShipFragment fragment = new LeaderShipFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentLeaderShipBinding binding;
        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
binding=FragmentLeaderShipBinding.inflate(inflater,container,false);
            FirebaseFirestore database= FirebaseFirestore.getInstance();
            ArrayList<User> users=new ArrayList<>();
            LeaderboardsAdapter adapter =new LeaderboardsAdapter(getContext(),users);

            binding.recycle.setAdapter(adapter);
            binding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));
            database.collection("users")
                    .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot snapshot :queryDocumentSnapshots){
                        User user=snapshot.toObject(User.class);
                        users.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            });


            return binding.getRoot();
        }
    }
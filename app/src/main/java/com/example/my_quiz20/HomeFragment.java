package com.example.my_quiz20;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.my_quiz20.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FirebaseFirestore database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);

        database=FirebaseFirestore.getInstance();

        ArrayList<CategoryModel> categories= new ArrayList<>();

        CategoryAdapter adapter=new CategoryAdapter(getContext(),categories);

        database.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                categories.clear();
                for(DocumentSnapshot snapshot:value.getDocuments()){
                    CategoryModel model=snapshot.toObject(CategoryModel.class);
                    model.setCategoryId(snapshot.getId());
                    categories.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        });
        binding.spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SpinnerActivity.class));
            }
        });
        binding.RecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.RecycleView.setAdapter(adapter);

        return binding.getRoot();
    }
}
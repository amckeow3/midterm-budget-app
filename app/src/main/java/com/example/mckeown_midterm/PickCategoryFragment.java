package com.example.mckeown_midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mckeown_midterm.databinding.FragmentPickCategoryBinding;

import java.util.ArrayList;

public class PickCategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    PickCategoryFragment.PickCategoryFragmentListener mListener;
    FragmentPickCategoryBinding binding;
    ArrayAdapter<String> adapter;
    ArrayList<String> categories = new ArrayList<>();

    public PickCategoryFragment() {
        // Required empty public constructor
    }

    public static PickCategoryFragment newInstance(String param1, String param2) {
        PickCategoryFragment fragment = new PickCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        categories = new ArrayList<>();
        categories = DataServices.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_category, container, false);

        ListView listView = view.findViewById(R.id.listViewCategories);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private static final String TAG = "Pick Category ";

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = categories.get(position);
                Log.d(TAG, "Category Selected: " + category);
                mListener.categorySelected(category);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pick Category");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PickCategoryFragment.PickCategoryFragmentListener) context;
    }

    // Interface to handle communication with Main Activity
    public interface PickCategoryFragmentListener {
        void categorySelected(String category);
    }
}
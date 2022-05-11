package com.example.mckeown_midterm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mckeown_midterm.databinding.ExpenseLineItemBinding;
import com.example.mckeown_midterm.databinding.FragmentExpensesBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExpensesFragment extends Fragment {
    ExpensesFragment.ExpensesFragmentListener mListener;
    FragmentExpensesBinding binding;
    private ArrayList<Expense> mExpenses;
    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Double> expense_amounts;
    ExpenseRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    double sum = 0.00;

    public static final String ARG_PARAM_EXPENSES = "arg_param_expenses";

    public ExpensesFragment() {
        // Required empty public constructor
    }

    public static ExpensesFragment newInstance(ArrayList<Expense> expenses) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM_EXPENSES, expenses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExpenses = (ArrayList<Expense>) getArguments().getSerializable(ARG_PARAM_EXPENSES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        getActivity().setTitle("Expenses");

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExpenseRecyclerViewAdapter(mExpenses);
        recyclerView.setAdapter(adapter);

        binding.textViewNoOfRecords.setText("" + mExpenses.size() + "");

        for (Expense expense : mExpenses) {
            sum += expense.amount;
        }

        binding.textViewExpensesTotal.setText("$" + sum);

        binding.buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createExpense();
            }
        });
    }

    public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ExpenseViewHolder> {
        public ExpenseRecyclerViewAdapter(ArrayList<Expense> data){
            mExpenses = data;
        }

        @NonNull
        @Override
        public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ExpenseLineItemBinding binding = ExpenseLineItemBinding.inflate(getLayoutInflater(), parent, false);
            return new ExpenseViewHolder(binding);
        }


        @Override
        public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
            Expense expense = mExpenses.get(position);
            holder.setupData(expense);
        }

        @Override
        public int getItemCount() {
            return mExpenses.size();
        }

        public class ExpenseViewHolder extends RecyclerView.ViewHolder {
            ExpenseLineItemBinding mBinding;
            Expense mExpense;


            public ExpenseViewHolder(ExpenseLineItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;

            }

            public void setupData(Expense expense) {
                mExpense = expense;

                mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExpenses.clear();
                        mListener.deleteAndRefresh(expense);
                        adapter.notifyDataSetChanged();
                    }
                });
                SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy");
                mBinding.textViewName.setText(mExpense.getName());
                mBinding.textViewDate.setText(sdf.format(mExpense.getDate()));
                mBinding.textViewAmount.setText("$" + String.valueOf(mExpense.getAmount()));
                mBinding.textViewCategory.setText("(" + mExpense.getCategory() + ")");
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ExpensesFragment.ExpensesFragmentListener) context;
    }

    // Interface to handle communication with Main Activity
    public interface ExpensesFragmentListener {
        void createExpense();
        void sendExpensesList();

        ArrayList<Expense> deleteAndRefresh(Expense expense);
    }
}
package com.example.mckeown_midterm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mckeown_midterm.databinding.FragmentAddExpenseBinding;

import java.util.Calendar;

public class AddExpenseFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Add Expense ";

    private String mParam1;
    private String mParam2;

    AddExpenseFragment.AddExpenseFragmentListener mListener;
    DatePickerDialog datePicker;
    FragmentAddExpenseBinding binding;
    private Expense expenseObject;

    String categorySelected;
    String dateSelected;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        binding.textViewDateSelected.setText(dateSelected);

        // Date Picker Dialog called when "Set Date" button clicked
        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dateString = String.format("%02d/%02d/%d", monthOfYear + 1, dayOfMonth, year);
                                setDateSelected(dateString);
                                binding.textViewDateSelected.setText(dateString);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add New Expense");

        binding.textViewCategorySelected.setText(categorySelected);
        binding.textViewDateSelected.setText(dateSelected);

        binding.buttonPickCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.pickCategory();
            }
        });

        binding.buttonCancelAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBack();
                mListener.sendExpensesList();
            }
        });

        binding.buttonSubmitNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = binding.editTextExpenseName.getText().toString();
                String expenseAmount = binding.editTextExpenseAmt.getText().toString();
                String expenseDate = binding.textViewDateSelected.getText().toString();
                String expenseCategory = binding.textViewCategorySelected.getText().toString();

                Double amountToDouble = Double.parseDouble(expenseAmount);
                if (expenseName.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Expense name is required.", Toast.LENGTH_SHORT).show();
                } else if (expenseAmount.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Expense amount is required.", Toast.LENGTH_SHORT).show();
                } else if (expenseDate.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Expense date is required.", Toast.LENGTH_SHORT).show();
                }
                else if (expenseCategory.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Expense category is required.", Toast.LENGTH_SHORT).show();
                }
                else {
                    expenseObject = new Expense(expenseName, amountToDouble, expenseDate, expenseCategory);
                    mListener.sendNewExpense(expenseObject);
                    mListener.sendExpensesList();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddExpenseFragment.AddExpenseFragmentListener) context;
    }

    // Interface to handle communication with Main Activity
    public interface AddExpenseFragmentListener {
        void goBack();
        void sendExpensesList();
        void sendNewExpense(Expense expenseObject);
        void pickCategory();
    }

    public void setCategorySelected(String category) {
        Log.d(TAG, "Category Received: " + category);
        categorySelected = category;
    }

    public void setDateSelected(String date) {
        dateSelected = date;
    }


}
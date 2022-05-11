

// Midterm
// Adrianna McKeown

package com.example.mckeown_midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddExpenseFragment.AddExpenseFragmentListener, ExpensesFragment.ExpensesFragmentListener,
        ExpensesSummaryFragment.ExpensesSummaryFragmentListener, PickCategoryFragment.PickCategoryFragmentListener {

    final ArrayList<Expense> expenses = new ArrayList<>();    //Array list for Expenses
    Expense expense;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToDoList fragment called when onCreate() is called in Main Activity
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, ExpensesFragment.newInstance(expenses), "expenses_fragment")
                .commit();
    }

    @Override
    public void createExpense() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddExpenseFragment(), "add_expense_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void sendExpensesList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ExpensesFragment.newInstance(expenses), "fragment")
                .commit();
    }

    @Override
    public ArrayList<Expense> deleteAndRefresh(Expense expense) {
        expenses.remove(expense);
        return expenses;
    }

    @Override
    public void sendNewExpense(Expense expenseObject) {
        expense = expenseObject;
        expenses.add(expense);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ExpensesFragment.newInstance(expenses), "category_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void pickCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new PickCategoryFragment(), "category_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void categorySelected(String category) {
        AddExpenseFragment addExpenseFragment = (AddExpenseFragment) getSupportFragmentManager().findFragmentByTag("add_expense_fragment");
        if (addExpenseFragment != null) {
            getSupportFragmentManager()
                    .popBackStack();
            addExpenseFragment.setCategorySelected(category);
        }
    }
}
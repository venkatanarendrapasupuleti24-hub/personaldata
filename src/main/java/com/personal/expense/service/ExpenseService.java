package com.personal.expense.service;

import com.personal.expense.model.Expense;
import com.personal.expense.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    public Expense getExpenseById(Long id) {

        return expenseRepository.findById(id).orElse(null);

    }

    public void updateExpense(Expense expense) {

        expenseRepository.save(expense);

    }
}
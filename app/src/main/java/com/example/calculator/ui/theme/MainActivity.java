package com.example.calculator.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String input = "";
    private String operator = "";
    private String firstNumber = "";
    private String secondNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView);

        // Set up number button listeners
        setupNumberButtons();

        // Set up operator buttons
        setupOperatorButtons();

        // Set up the equals button
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            if (input.isEmpty() || firstNumber.isEmpty() || operator.isEmpty()) {
                // Show a toast if input, firstNumber, or operator is empty
                Toast.makeText(this, "Please complete the operation", Toast.LENGTH_SHORT).show();
                return;
            }
            secondNumber = input;
            double result = calculate(firstNumber, secondNumber, operator);
            display.setText(String.valueOf(result));
            input = String.valueOf(result);
            firstNumber = ""; // Reset firstNumber for new calculations
            operator = ""; // Reset operator
        });

        // Set up the clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
    }

    private void setupNumberButtons() {
        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                String number = button.getText().toString();
                appendToInput(number);
            });
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtons = {
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                if (!input.isEmpty()) {
                    operator = button.getText().toString();
                    firstNumber = input;
                    input = ""; // Clear input for the next number
                    updateDisplay();
                }
            });
        }
    }

    private void appendToInput(String value) {
        input += value;
        updateDisplay();
    }

    private void updateDisplay() {
        String displayText = firstNumber; // Start with the first number
        if (!firstNumber.isEmpty() && !operator.isEmpty()) {
            displayText += " " + operator + " "; // Add operator if available
        }
        displayText += input; // Add current input
        display.setText(displayText); // Update the display
    }

    private double calculate(String first, String second, String operator) {
        // Check if both numbers are valid
        if (first.isEmpty() || second.isEmpty()) {
            return 0; // Return 0 if either number is empty
        }

        double firstNum = Double.parseDouble(first);
        double secondNum = Double.parseDouble(second);

        switch (operator) {
            case "+":
                return firstNum + secondNum;
            case "-":
                return firstNum - secondNum;
            case "*":
                return firstNum * secondNum;
            case "/":
                if (secondNum == 0) {
                    throw new ArithmeticException("Cannot divide by zero"); // Handle division by zero
                }
                return firstNum / secondNum;
            default:
                return 0;
        }
    }

    private void clear() {
        input = "";
        operator = "";
        firstNumber = "";
        secondNumber = "";
        display.setText("0");
    }
}

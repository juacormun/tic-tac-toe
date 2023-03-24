package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static final String CROSS = "❌";
    static final String NOUGHT = "⭕";

    private String firstTurn = CROSS;
    private String currentTurn = CROSS;

    private int crossesScore = 0;
    private int noughtsScore = 0;

    private ActivityMainBinding binding;
    private final List<Button> boardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBoard();
    }

    private void initBoard() {
        boardList.add(binding.a1);
        boardList.add(binding.a2);
        boardList.add(binding.a3);
        boardList.add(binding.b1);
        boardList.add(binding.b2);
        boardList.add(binding.b3);
        boardList.add(binding.c1);
        boardList.add(binding.c2);
        boardList.add(binding.c3);
    }

    public void boardTapped(View view) {
        if (!(view instanceof Button)) {
            return;
        }
        addToBoard((Button) view);

        if (checkForVictory(CROSS)) {
            crossesScore++;
            result("¡Ganan las X!");
            return;
        } else if (checkForVictory(NOUGHT)) {
            noughtsScore++;
            result("¡Ganan las O!");
            return;
        }

        if (fullBoard()) {
            result("Empate");
        }
    }

    private boolean checkForVictory(String s) {
        // Horizontal victory
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s))
            return true;
        if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s))
            return true;
        if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s))
            return true;

        // Vertical victory
        if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s))
            return true;
        if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s))
            return true;
        if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s))
            return true;

        // Diagonal victory
        if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s))
            return true;
        if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s))
            return true;

        return false;
    }

    private boolean match(Button button, String symbol) {
        return button.getText().toString().equals(symbol);
    }

    private void result(String title) {
        String message = "\n❌ ➜ " + crossesScore + "\n\n⭕ ➜ " + noughtsScore;
        AlertDialog builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Reiniciar", (dialog, which) -> resetBoard())
                .setCancelable(false)
                .create();

        builder.show();
    }

    private void resetBoard() {
        for (Button button : boardList) {
            button.setText("");
        }
        if (firstTurn.equals(CROSS)) {
            firstTurn = NOUGHT;
        } else {
            firstTurn = CROSS;
        }
        currentTurn = firstTurn;
        setTurnLabel();
    }

    private boolean fullBoard() {
        for (Button button : boardList) {
            if (button.getText().toString().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void addToBoard(Button button) {
        if (!button.getText().toString().equals("")) {
            return;
        }
        if (currentTurn.equals(CROSS)) {
            button.setText(CROSS);
            currentTurn = NOUGHT;
        } else {
            button.setText(NOUGHT);
            currentTurn = CROSS;
        }
        setTurnLabel();
    }

    private void setTurnLabel() {
        String turnText;
        if (currentTurn.equals(CROSS)) {
            turnText = "TURNO DE ❌";
        } else {
            turnText = "TURNO DE ⭕";
        }
        binding.turnTV.setText(turnText);
    }
}
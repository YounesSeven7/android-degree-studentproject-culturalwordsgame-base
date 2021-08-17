package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    private TextView textView;
    private int numberIndex;
    private String []answers;
    private String []answersDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        textView = findViewById(R.id.text_view_answer);
        numberIndex = getIntent().getIntExtra("number_index", -1);
        answers = getResources().getStringArray(R.array.answers);
        answersDescription = getResources().getStringArray(R.array.answer_description);
        textView.setText(answers[numberIndex] + " : " +answersDescription[numberIndex]);
    }

    public void onButtonReturnClicked(View view){
        finish();
    }
}
package com.example.quizapp.Custom;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.data.States;

import java.util.List;
import java.util.Random;

public class QuizView extends LinearLayout {

    private States currentState;
    private int currentOptionId;
    private RadioGroup optionsRadioGroup;

    public QuizView(Context context) {
        super(context);
        initRadios();
    }

    private void initRadios() {
        optionsRadioGroup = new RadioGroup(getContext());
        optionsRadioGroup.setId(View.generateViewId());
    }

    public void setData(List<States> states, int value) {
        Random random = new Random(System.currentTimeMillis());
        int correctOption = random.nextInt(value);
        currentState = states.get(correctOption);

        TextView questionTv = new TextView(getContext());
        String question = "What is the capital of " + currentState.getState() + "?";
        questionTv.setText(question);

        this.addView(questionTv);
        this.addView(optionsRadioGroup);

        RadioButton[] radios = new RadioButton[4];
        //radios[correctOption]

    }
}

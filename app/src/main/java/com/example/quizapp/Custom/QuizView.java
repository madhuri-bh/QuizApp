package com.example.quizapp.Custom;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.quizapp.data.States;

import java.util.List;
import java.util.Random;

public class QuizView extends LinearLayout {

    private States correctState;
    private int correctOptionId;
    private RadioGroup optionsRadioGroup;

    private OptionsClickListener optionsClickListener;

    public QuizView(Context context) {
        super(context);
        initRadios();
    }

    public QuizView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRadios();
    }

    private void initRadios() {
        optionsRadioGroup = new RadioGroup(getContext());
        optionsRadioGroup.setId(View.generateViewId());
    }

    public interface OptionsClickListener {
        void optionsClick(boolean result);
    }

    public void setOptionsClickListener(OptionsClickListener optionsClickListener) {
        this.optionsClickListener = optionsClickListener;
    }

    public void setData(List<States> states) {
        Random random = new Random(System.currentTimeMillis());
        int correctOption = random.nextInt(4);
        correctState = states.get(correctOption);

        TextView questionTv = new TextView(getContext());
        String question = "What is the capital of " + correctState.getState() + "?";
        questionTv.setText(question);

        this.addView(questionTv);
        this.addView(optionsRadioGroup);

        RadioButton[] radios = new RadioButton[4];
        radios[correctOption] = new RadioButton(getContext());
        radios[correctOption].setId(View.generateViewId());
        radios[correctOption].setText(correctState.getCapital());

        correctOptionId = radios[correctOption].getId();

        for (int i = 0, j = 0; i < 4 && j < 4; i++, j++) {
            if (i == correctOption) {
                optionsRadioGroup.addView(radios[correctOption]);
                continue;
            } else {
                radios[i] = new RadioButton(getContext());
                radios[i].setId(View.generateViewId());
                radios[i].setText(states.get(j).getCapital());
                optionsRadioGroup.addView(radios[i]);
            }
            initListeners();
        }
    }

    private void initListeners() {
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (optionsClickListener != null) {
                    if (checkedId == correctOptionId) {
                        optionsClickListener.optionsClick(true);
                    } else {
                        optionsClickListener.optionsClick(false);
                    }
                }
            }
        });
    }

    public void resetGame() {
        optionsRadioGroup.removeAllViews();
        this.removeAllViews();
    }
}

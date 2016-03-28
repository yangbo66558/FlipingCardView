package com.tiger.jump.high.flipingcardview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout flContainer;
    private View cardFront;
    private View cardBack;

    private AnimatorSet inSet;
    private AnimatorSet outSet;

    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flContainer = (FrameLayout) findViewById(R.id.fl_container);
        cardBack = findViewById(R.id.card_back);
        cardFront = findViewById(R.id.card_front);
        flContainer.setOnClickListener(this);

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_out);
        outSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                flContainer.setClickable(false);
            }
        });
        inSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);
                flContainer.setClickable(true);
            }
        });

        cardFront.setCameraDistance(cardFront.getCameraDistance() * 50/*getResources().getDisplayMetrics().density * 160000*/);
        cardBack.setCameraDistance(cardFront.getCameraDistance() * 50/*getResources().getDisplayMetrics().density * 160000*/);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_container:
                if (!isBack) {
                    outSet.setTarget(cardFront);
                    inSet.setTarget(cardBack);
                    outSet.start();
                    inSet.start();
                    isBack = true;
                } else { // 背面朝上
                    outSet.setTarget(cardBack);
                    inSet.setTarget(cardFront);
                    outSet.start();
                    inSet.start();
                    isBack = false;
                }
                break;
        }
    }

}

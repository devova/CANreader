package com.autowp.canreader;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


abstract public class TripInfoFragment extends ServiceConnectedFragment{
    private boolean isFontSizeLarge = false;
    protected ArrayList<TextView> textViews = new ArrayList<>();

    public TripInfoFragment() {
        // Required empty public constructor
    }

    private void setTypeFace() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/digital_7.ttf");

        for (TextView tv: textViews) {
            tv.setTypeface(tf);
        }
    }

    protected void setScale(float scale) {
        final View panel = getView();
        float startScale = panel.getScaleX();
        ValueAnimator animator = ValueAnimator.ofFloat(startScale, scale);
        animator.setDuration(500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                panel.setScaleX(animatedValue);
                panel.setScaleY(animatedValue);
            }
        });

        animator.start();
    }

    public void toggleScale() {
        isFontSizeLarge = !isFontSizeLarge;
        if (isFontSizeLarge) {
            setScale(1.1F);
        } else {
            setScale(1);
        }
    }

    abstract void setHandlers();

    @Override
    protected void afterConnect() {
        setHandlers();
        setTypeFace();
    }

    @Override
    protected void beforeDisconnect() {

    }
}

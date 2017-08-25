package com.autowp.canreader;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
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

    protected void setFontSize(float size) {
        for (final TextView tv: textViews) {
            float startSize = tv.getTextSize();
            ValueAnimator animator = ValueAnimator.ofFloat(startSize, size);
            animator.setDuration(500);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedValue = (float) valueAnimator.getAnimatedValue();
                    tv.setTextSize(animatedValue);
                }
            });

            animator.start();
        }
    }

    protected void toggleFontSize() {
        isFontSizeLarge = !isFontSizeLarge;
        if (isFontSizeLarge) {
            setFontSize(40);
        } else {
            setFontSize(24);
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

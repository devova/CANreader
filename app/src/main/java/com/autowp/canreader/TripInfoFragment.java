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

    protected void setScale(float scale) {
        for (final TextView tv: textViews) {
            float startScale = tv.getScaleX();
            ValueAnimator animator = ValueAnimator.ofFloat(startScale, scale);
            animator.setDuration(500);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedValue = (float) valueAnimator.getAnimatedValue();
                    tv.setScaleX(animatedValue);
                    tv.setScaleY(animatedValue);
                }
            });

            animator.start();
        }
    }

    protected void toggleScale() {
        isFontSizeLarge = !isFontSizeLarge;
        if (isFontSizeLarge) {
            setScale((float) 1.2);
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

package com.autowp.canreader;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sccomponents.gauges.ScCopier;
import com.sccomponents.gauges.ScGauge;
import com.sccomponents.gauges.ScLinearGauge;
import com.sccomponents.gauges.ScNotches;
import com.sccomponents.gauges.ScPointer;
import com.sccomponents.gauges.ScWriter;

import org.apache.commons.lang.ObjectUtils;


public class RadioTuneFragment extends Fragment {
    private ScLinearGauge gauge;
    private float minFreq = 80F;
    private float maxFreq = 108F;
    private float freq;


    public RadioTuneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio_tune, container, false);

        // Create a drawable
//        final Bitmap indicator = BitmapFactory.decodeResource(this.getResources(), R.drawable.indicator);

        gauge = (ScLinearGauge) view.findViewById(R.id.tune);
        gauge.getHighValueAnimator().setDuration(500);
        gauge.setOnDrawListener(new ScGauge.OnDrawListener() {
            @Override
            public void onBeforeDrawCopy(ScCopier.CopyInfo copyInfo) {

            }

            @Override
            public void onBeforeDrawNotch(ScNotches.NotchInfo info) {
                // The notch length
                info.length = gauge.dipToPixel(info.index % 10 == 0 ? 20 : 10);
//                info.offset = gauge.dipToPixel(info.index % 8 == 0 ? 0 : info.index % 4 == 0 ? 5 : 10);
            }

            @Override
            public void onBeforeDrawPointer(ScPointer.PointerInfo pointerInfo) {

            }

            @Override
            public void onBeforeDrawToken(ScWriter.TokenInfo info) {
                // Set angle and text
                info.angle = 0.0f;
                info.text = String.format("%2.1f MHz", freq);
                Rect bounds = new Rect();
                info.source.getPainter().getTextBounds(info.text, 0, info.text.length(), bounds);

//                 Set the position
                float distance = info.source.getDistance(gauge.getHighValue());
                info.offset.x =  Math.min(distance - bounds.width()/2, info.source.getDistance(100F) - bounds.width());
                info.offset.x =  Math.max(0, info.offset.x);
                info.offset.y = -12;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            setFrequency(savedInstanceState.getFloat("freq", minFreq));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putFloat("freq", freq);
    }

    public void setFrequency(float freq) {
        this.freq = freq;
        gauge.setHighValue(freq, minFreq, maxFreq);
    }

}

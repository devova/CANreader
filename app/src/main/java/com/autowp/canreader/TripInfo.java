package com.autowp.canreader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citroen.handlers.radioActivity.AVG1Distance;
import com.citroen.handlers.radioActivity.AVG1FuelConsumption;
import com.citroen.handlers.radioActivity.AVG1Speed;
import com.jvit.bus.Signal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TripInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TripInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripInfo extends ServiceConnectedFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TripInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static TripInfo newInstance(String param1, String param2) {
        TripInfo fragment = new TripInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected void setHandlers() {
        TextView tripFuelConsumption = (TextView) getActivity().findViewById(R.id.tripFuelConsumption);
        Signal signal = canReaderService.bus.addSignalHandler(
                AVG1FuelConsumption.getInstance().setView(tripFuelConsumption));
        AVG1FuelConsumption.getInstance().handle(signal, null);

        TextView tripAvgSpeed = (TextView) getActivity().findViewById(R.id.tripAvgSpeed);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Speed.getInstance().setView(tripAvgSpeed));
        AVG1Speed.getInstance().handle(signal, null);

        TextView tripDistance = (TextView) getActivity().findViewById(R.id.tripDistance);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Distance.getInstance().setView(tripDistance));
        AVG1Distance.getInstance().handle(signal, null);
    }

    @Override
    protected void afterConnect() {
        setHandlers();
    }

    @Override
    protected void beforeDisconnect() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

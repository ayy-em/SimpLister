package com.example.jtodolister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleAddFragment extends Fragment  {

    public static final String fragStringKey = "SAF_STR_KEY";
    public static final String fragIntKey = "SAF_NUMBER_KEY";
    private TextView tv;


    public static SimpleAddFragment newInstance(String str, int fragNumber) {

        final SimpleAddFragment fragment = new SimpleAddFragment();

        //create a bundle with fragment text and number
        final Bundle params = new Bundle();
        params.putString(fragStringKey,str);
        params.putInt(fragIntKey,fragNumber);
        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_add_simple,container,false);

        //what was initially there
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //swipe ontouch to delete it from main screen
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }


        @Override
        public void onSwipeRight() {
                //TODO: confirm
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_out_right,R.anim.slide_in_left)
                    .remove(SimpleAddFragment.this).commit();
            super.onSwipeRight();
        }
    });

        //set user's text to that fragment
        tv = (TextView) view.findViewById(R.id.safTextView);
        Bundle params = getArguments();
        tv.setText(params.getString(fragStringKey));
    }
}

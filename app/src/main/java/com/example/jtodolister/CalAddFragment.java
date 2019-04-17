package com.example.jtodolister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalAddFragment extends Fragment {

    public static final String fragTextKey = "CAF_FRAG_TEXT";
    public static final String fragDateTextKey = "CAF_FRAG_DATETEXT";
    public static final String fragTimestampKey = "CAF_FRAG_TIMESTAMP";
    private TextView tvText;
    private TextView tvTimeStamp;
    private TextView tvDateGiven;
    private ImageButton shareButtonCalFrag;


    public static CalAddFragment newInstance(String text, String dateText, Date date, int fragNumber){
        CalAddFragment fragment = new CalAddFragment();
        //bundle everything we need for fragment creation
        Bundle params = new Bundle();
        params.putString(fragTextKey,text);
        params.putString(fragDateTextKey,dateText);

        //add date to fragments params
        SimpleDateFormat format = new SimpleDateFormat("dd.MM", Locale.US);
        String dateOfPost = format.format(date);
        params.putString(fragTimestampKey,dateOfPost);

        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_add_cal,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set text, timestamp, dateGiven to what it should be (arguments)
        Bundle params = getArguments();
        tvText = (TextView) view.findViewById(R.id.cafTextView);
        tvText.setText(params.getString(fragTextKey));
        tvDateGiven = (TextView) view.findViewById(R.id.cafDateSetTextView);
        tvDateGiven.setText(params.getString(fragDateTextKey));
        tvTimeStamp = (TextView) view.findViewById(R.id.cafTimeStamp);
        tvTimeStamp.setText(params.getString(fragTimestampKey));

        //share button declaration & onclick
        shareButtonCalFrag = (ImageButton) view.findViewById(R.id.cafShare);
        shareButtonCalFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String subjectDate = getString(R.string.reminder) + tvDateGiven.getText().toString().trim();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subjectDate);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tvText.getText().toString().trim());
                startActivity(Intent.createChooser(sharingIntent,getString(R.string.share)));
            }
        });

        //swipe ontouch to delete this fragment from main screen
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
                        .remove(CalAddFragment.this).commit();
                super.onSwipeRight();
            }
        });
    }
}

package com.example.jtodolister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class LongAddFragment extends Fragment {

    public static final String fragStringContentKey = "LONG_STR_KEY";
    public static final String fragStringTitleKey = "LONG_STR_KEY";
    public static final String fragIntKey = "LONG_NUMBER_KEY";
    private TextView tvTitle;
    private TextView tvContent;
    private ImageButton longShareButton;


    public static LongAddFragment newInstance(String strTitle, String strContent, int fragNumber) {

        final LongAddFragment fragment = new LongAddFragment();

        //create a bundle with fragment text and number
        final Bundle params = new Bundle();
        params.putString(fragStringTitleKey,strTitle);
        params.putString(fragStringContentKey,strContent);
        params.putInt(fragIntKey,fragNumber);
        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_add_long,container,false);

        //what was initially there
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                //TODO: confirm, sweet animations
                getFragmentManager().beginTransaction().remove(LongAddFragment.this).commit();
                super.onSwipeRight();
            }
        });

        //set user's text to that fragment
        tvTitle = (TextView) view.findViewById(R.id.titleLongTextView);
        tvContent = (TextView) view.findViewById(R.id.contentLongTextView);
        Bundle params = getArguments();
        tvTitle.setText(params.getString(fragStringTitleKey));
        tvContent.setText(params.getString(fragStringContentKey));

        longShareButton = (ImageButton) view.findViewById(R.id.shareLong);
        longShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, tvTitle.getText().toString().trim());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tvContent.getText().toString().trim());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
            }
        });
    }
}

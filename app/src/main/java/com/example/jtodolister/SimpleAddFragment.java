package com.example.jtodolister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleAddFragment extends Fragment  {

    public static final String fragStringKey = "SAF_STR_KEY";
    public static final String fragIntKey = "SAF_NUMBER_KEY";
    public static final String fragDateKey = "SAF_DATE_KEY";
    private TextView tv;
    private ImageButton safShareButton;
    private TextView safTimeStamp;
    private Boolean isExpanded;

    public static SimpleAddFragment newInstance(String str, Date date, int fragNumber) {

        final SimpleAddFragment fragment = new SimpleAddFragment();
        fragment.isExpanded = false;

        //create a bundle with fragment text and number
        final Bundle params = new Bundle();
        params.putString(fragStringKey,str);
        params.putInt(fragIntKey,fragNumber);

        //add date to fragments params
        SimpleDateFormat format = new SimpleDateFormat("dd.MM",Locale.US);
        String dateToStr = format.format(date);

        params.putString(fragDateKey,dateToStr);
        fragment.setArguments(params);
        return fragment;
    }

    public void expandThis() {
        //TODO: animate this - doesn't work, like, at all
        final Bundle params = new Bundle();
        params.putString(fragStringKey,tv.getText().toString().trim());
        params.putInt(fragIntKey,getArguments().getInt(fragIntKey));
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
        final View thisView = this.getView();

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

        //set date to fragment
        safTimeStamp = (TextView) view.findViewById(R.id.safTimeStamp);
        safTimeStamp.setText(params.getString(fragDateKey));

        //share button stuff
        safShareButton = (ImageButton) view.findViewById(R.id.safShare);
        safShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv.getText().toString().trim());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ExpandCollapse.Collapse(thisView);
                    tv.setMaxLines(1);
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    isExpanded = false;
                } else {
                    tv.setMaxLines(Integer.MAX_VALUE);
                    tv.setEllipsize(null);
                    ExpandCollapse.Expand(thisView);
                    isExpanded = true;
                }
            }
        });
    }
}

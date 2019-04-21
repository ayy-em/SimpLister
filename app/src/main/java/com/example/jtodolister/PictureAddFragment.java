package com.example.jtodolister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PictureAddFragment extends Fragment {

    public static final String fragStringKey = "PAF_STR_KEY";
    public static final String fragPathKey = "PAF_PATH_KEY";
    public static final String fragIntKey = "PAF_INT_KEY";
    public static final String fragDateKey = "PAF_DATE_KEY";
    private ImageView imageForFragment;
    private ImageView expandedImageView;
    private TextView textForFragment;
    private TextView timeStamp;
    private ImageButton pafShareButton;
    private Boolean isExpanded;

    public static PictureAddFragment newInstance(String imagePath, String text, Date date, int fragNumber) {

        //TODO: clicking an image enlarges it
        final PictureAddFragment fragment = new PictureAddFragment();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM", Locale.US);
        String dateToStr = format.format(date);
        fragment.isExpanded = false;

        //create a bundle with fragment text and number
        final Bundle params = new Bundle();
        params.putString(fragStringKey,text);
        params.putInt(fragIntKey,fragNumber);
        params.putString(fragPathKey,imagePath);
        params.putString(fragDateKey,dateToStr);
        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_add_pic,container,false);

        //what was initially there
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View thisView = this.getView();

        //swipe ontouch to delete it from main screen
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                //literally nothing happens
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                //TODO: confirm if not empty
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_out_right,R.anim.slide_in_left)
                        .remove(PictureAddFragment.this).commit();
                super.onSwipeRight();
            }
        });

        Bundle params = getArguments();
        //set the timestamp
        timeStamp = (TextView) view.findViewById(R.id.pafTimeStamp);
        timeStamp.setText(params.getString(fragDateKey));

        //set user's text to that fragment
        textForFragment = (TextView) view.findViewById(R.id.pafTextView);
        textForFragment.setText(params.getString(fragStringKey));

        //and set the pic to what the user chose
        imageForFragment = (ImageView) view.findViewById(R.id.pafPic);
        final Bitmap imageBitmap = BitmapFactory.decodeFile(params.getString(fragPathKey));
        imageForFragment.setImageBitmap(imageBitmap);

        //now expand animation on image click
        imageForFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomPic(imageForFragment,imageBitmap,v);
            }
        });

        textForFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ExpandCollapse.CollapseImg(thisView,imageForFragment,textForFragment);
                    textForFragment.setMaxLines(1);
                    textForFragment.setEllipsize(TextUtils.TruncateAt.END);
                    isExpanded = false;
                } else {
                    textForFragment.setMaxLines(Integer.MAX_VALUE);
                    textForFragment.setEllipsize(null);
                    ExpandCollapse.ExpandImg(thisView,imageForFragment,textForFragment);
                    isExpanded = true;
                }
            }
        });

        //share button stuff
        //TODO: shares not only text, but the image as well
        pafShareButton = (ImageButton) view.findViewById(R.id.pafShare);
        pafShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, textForFragment.getText().toString().trim());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
            }
        });
    }

    public void zoomPic(ImageView smallImage, Bitmap bm, View view) {
        ((MainActivity)getActivity()).zoomImageFromThumb(smallImage,bm,view);
    }

}

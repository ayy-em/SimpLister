package com.example.jtodolister;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandCollapse {

    public static void Expand(final View v) {

        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //final int targetHeight = v.getMeasuredHeight();
        final int targetHeight = v.getMeasuredHeight();
        //final int targetHeight = v.findViewById(R.id.list_layout_for_title_and_content).getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.setVisibility(View.VISIBLE);

        //let's set a minimum height to 64dp... first get density/scale
        final float scale = v.getContext().getResources().getDisplayMetrics().density;
        //then transform dp to pixels
        final int pixelsHeightInitial = (int) (64 * scale + 0.5f);

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.getLayoutParams().height = targetHeight;
                } else {
                    //let's get the total amount of expansion required
                    int expansionRequired = targetHeight - pixelsHeightInitial;
                    //and slowly but surely, you know
                    v.getLayoutParams().height = pixelsHeightInitial + (int) (expansionRequired * interpolatedTime);
                }
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
        v.startAnimation(a);
    }

    public static void Collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        final float scale = v.getContext().getResources().getDisplayMetrics().density;
        final int pixelsHeightNormal = (int) (64 * scale + 0.5f);

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                // v.getLayoutParams().height = pixelsHeightNormal;

                if (interpolatedTime == 1) {
                    v.getLayoutParams().height = pixelsHeightNormal;
                } else {
                    //let's get the total amount of expansion required
                    int collapseRequired = initialHeight - pixelsHeightNormal;
                    //and slowly but surely, you know
                    v.getLayoutParams().height = initialHeight - (int) (collapseRequired * interpolatedTime);
                }
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
        v.startAnimation(a);
    }
}

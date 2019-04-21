package com.example.jtodolister;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MapAddFragment extends Fragment implements OnMapReadyCallback {

    private TextView tv;
    private TextView ts;
    private MapView mv;
    private static GoogleMap gm;
    private ImageButton mafShare;
    private static final String mafStringKey = "MAF_FRAG_TEXT_KEY";
    private static final String mafDateKey = "MAF_FRAG_DATE_KEY";
    private static final String mafMapKey = "MAF_MAP_KEY";
    private static final String mafMapLatKey = "MAF_MAP_LAT_KEY";
    private static final String mafMapLongKey = "MAF_MAP_LONG_KEY";
    private Boolean isExpanded;
    private GoogleApiClient mGoogleApiClient;
    //TODO: add border to map fragment's map


    public static MapAddFragment newInstance(String text, Date date, double latitude, double longitude, int fragNumber) {

        MapAddFragment fragment = new MapAddFragment();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM", Locale.US);
        String dateToStr = format.format(date);
        fragment.isExpanded = false;
        //create a bundle with fragment text and number
        final Bundle params = new Bundle();
        params.putString(mafStringKey,text);
        params.putDouble(mafMapLatKey,latitude);
        params.putDouble(mafMapLongKey,longitude);
        params.putString(mafDateKey,dateToStr);
        fragment.setArguments(params);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraglayout_add_loc,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle params = getArguments();
        final View thisView = this.getView();

        //map stuff
        mv = (MapView) view.findViewById(R.id.mafMap);
        mv.onCreate(savedInstanceState);
        mv.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gm = googleMap;

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                //drop a marker on saved location
                LatLng locGiven = new LatLng(params.getDouble(mafMapLatKey),params.getDouble(mafMapLongKey));
                googleMap.addMarker(new MarkerOptions().position(locGiven).
                        title("Title")
                        .snippet("TitleName")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                        .alpha(0.7f));

                //zoom to marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(locGiven).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                        (cameraPosition ));
            }
        });

        //set user's text to that fragment
        tv = (TextView) view.findViewById(R.id.mafTextView);
        tv.setText(params.getString(mafStringKey));
        //and the timestamp
        ts = (TextView) view.findViewById(R.id.mafTimeStamp);
        ts.setText(params.getString(mafDateKey));

        //share button OCL
        //TODO: shares not only text, but the location as well
        mafShare = (ImageButton) view.findViewById(R.id.mafShare);
        mafShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tv.getText().toString().trim());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
            }
        });

        //expand/collapse if note is long
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ExpandCollapse.CollapseMap(thisView,mv,tv);
                    tv.setMaxLines(1);
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    isExpanded = false;
                } else {
                    tv.setMaxLines(Integer.MAX_VALUE);
                    tv.setEllipsize(null);
                    ExpandCollapse.ExpandMap(thisView,mv,tv);
                    isExpanded = true;
                }
            }
        });

        //swipe ontouch to delete it from main screen
        view.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                //literally nothing happens YET
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                //TODO: confirm if not empty
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_out_right,R.anim.slide_in_left)
                        .remove(MapAddFragment.this).commit();
                super.onSwipeRight();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            //TODO: figure this shit out
            @Override
            public void onMapClick(LatLng latLng) {
                Bundle args = getArguments();
                Uri gmIntentUri = Uri.parse("google.streetview:cbll=" + args.getDouble(mafMapLatKey) + "," + args.getDouble(mafMapLongKey));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        gm = googleMap;
    }
}

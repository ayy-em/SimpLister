package com.example.jtodolister;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab_add,fab_long,fab_pic,fab_loc,fab_time;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    public Boolean isNightModeChecked = false;
    private ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        //TODO: landscape icons for settings

        getNiceFloatingActionButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        //SHARE button
        MenuItem item_share = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item_share);
        Intent shareButtonIntent = new Intent(Intent.ACTION_SEND);
        shareButtonIntent.setType("message/rfc822");
        shareButtonIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text));
        shareActionProvider.setShareIntent(shareButtonIntent);

        MenuItem item_about = menu.findItem(R.id.about_menu);
        item_about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity.this.goToAbout(item);
                return false;
            }
        });
        return true;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab_action_add:
                Log.d("XXX", "Fab add");
                break;
            case R.id.fab_action_add_long:
                Log.d("XXX", "Fab long");
                break;
            case R.id.fab_action_add_loc:
                Log.d("XXX", "Fab loc");
                break;
            case R.id.fab_action_add_pic:
                Log.d("XXX", "Fab pic");
                break;
            case R.id.fab_action_add_time:
                Log.d("XXX", "Fab time");
                break;
        }
    }

    public void goToSettings(MenuItem item){
        //TODO: intent to settings page
        //if fab is open, close it before going to about
        if (isFabOpen) {
            findViewById(R.id.fab).callOnClick();
        }
        Log.d("XXX","Going to settings");
    }

    public void goToAbout(MenuItem item) {
        //if fab is open, close it before going to about
        if (isFabOpen) {
            findViewById(R.id.fab).callOnClick();
        }
        Intent aboutIntent = new Intent(this,AboutScreen.class);
        startActivity(aboutIntent);
        Log.d("XXX","Going to about");
    }

    public void toggleNightMode(MenuItem item){
        //TODO: Night mode
        if (isNightModeChecked) {
            item.setChecked(false);
            isNightModeChecked = false;
            Log.d("XXX","turning night mode off");
        } else {
            item.setChecked(true);
            isNightModeChecked = true;
            Log.d("XXX","turning night mode on");
        }
    }
    //----------------------FAB STUFF------------------------------
    //-------------------------------------------------------------
    private void getNiceFloatingActionButton() {
        //TODO: FAB horizontal on rotation
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_action_add);
        fab_long = (FloatingActionButton) findViewById(R.id.fab_action_add_long);
        fab_loc = (FloatingActionButton) findViewById(R.id.fab_action_add_loc);
        fab_pic = (FloatingActionButton) findViewById(R.id.fab_action_add_pic);
        fab_time = (FloatingActionButton) findViewById(R.id.fab_action_add_time);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fab.setOnClickListener(this);
        fab_add.setOnClickListener(this);
        fab_long.setOnClickListener(this);
        fab_loc.setOnClickListener(this);
        fab_pic.setOnClickListener(this);
        fab_time.setOnClickListener(this);
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab_add.startAnimation(fab_close);
            fab_long.startAnimation(fab_close);
            fab_loc.startAnimation(fab_close);
            fab_pic.startAnimation(fab_close);
            fab_time.startAnimation(fab_close);
            fab_add.setClickable(false);
            fab_long.setClickable(false);
            fab_loc.setClickable(false);
            fab_pic.setClickable(false);
            fab_time.setClickable(false);
            isFabOpen = false;
            Log.d("XXX", "close");
        } else {
            fab.startAnimation(rotate_forward);
            fab_add.startAnimation(fab_open);
            fab_long.startAnimation(fab_open);
            fab_loc.startAnimation(fab_open);
            fab_pic.startAnimation(fab_open);
            fab_time.startAnimation(fab_open);
            fab_add.setClickable(true);
            fab_long.setClickable(true);
            fab_loc.setClickable(true);
            fab_pic.setClickable(true);
            fab_time.setClickable(true);
            isFabOpen = true;
            Log.d("XXX","open");
        }
    }
    //-------------------------------------------------------------
    //----------------------FAB STUFF------------------------------

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private final LayoutInflater myInflater;
        private final ArrayList myData;

        public ListAdapter(final Context context,final ArrayList data) {
            myInflater = LayoutInflater.from(context);
            myData = data;
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            final View view = myInflater.inflate(android.R.layout.simple_list_item_2, viewGroup,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.textView.setText(myData.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}

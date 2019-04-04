package com.example.jtodolister;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab_add,fab_long,fab_pic,fab_loc,fab_time;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    public Boolean isNightModeChecked = false;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ViewGroup fragData;
    private TextView literallyNothingAdded;
    private static FragmentTransaction ft_general;
    //TODO: edit text focus listener to deflate FAB
    private static int fragCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        //my beautiful expanding FAB
        getNiceFloatingActionButton();

        //nothing text gone if fragment placed
        literallyNothingAdded = (TextView) findViewById(R.id.nothing_here_textview);
    }

    //-------------------------------------------------------------
    //------------------TOOLBAR/MENU STUFF-------------------------
    //TODO: landscape mode settings (icons)
    //TODO: toolbar height dynamically changes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        //SHARE button - if it doesn't work, convert SAP to class variables
        MenuItem item_share = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item_share);
        Intent shareButtonIntent = new Intent(Intent.ACTION_SEND);
        shareButtonIntent.setType("message/rfc822");
        shareButtonIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_text));
        shareActionProvider.setShareIntent(shareButtonIntent);

        //ABOUT button in the menu
        MenuItem item_about = menu.findItem(R.id.about_menu);
        item_about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MainActivity.this.goToAbout(item);
                return false;
            }
        });
        return true;
        //settings and night mode below
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
        //go to about (class: AboutScreen, layout about_screen.xml (port/land separately)
        Intent aboutIntent = new Intent(this,AboutScreen.class);
        startActivity(aboutIntent);
        Log.d("XXX","Going to about");
    }

    public void toggleNightMode(MenuItem item){
        //TODO: Night mode
        //check if night mode is on/off and toggle to a different position
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
    //------------------TOOLBAR/MENU STUFF-------------------------
    //-------------------------------------------------------------


    //-------------------------------------------------------------
    //----------------FRAGMENT-RELATED STUFF-----------------------

    private void removeNothingIfItsPresent() {
        if (literallyNothingAdded.getVisibility() != View.GONE) {
            literallyNothingAdded.setVisibility(View.GONE);
        }
    }

    private void addSimpleFragment(String str) {
        fragCount++;
        removeNothingIfItsPresent();
        FragmentTransaction ft_add_simple = getSupportFragmentManager().beginTransaction();
        ft_add_simple.add(R.id.content_main,SimpleAddFragment.newInstance(str,getFragCount()));
        ft_add_simple.commit();
    }

    private void addLongFragment(String title, String content) {
        fragCount++;
        removeNothingIfItsPresent();
        FragmentTransaction ft_add_long = getSupportFragmentManager().beginTransaction();
        ft_add_long.add(R.id.content_main,LongAddFragment.newInstance(title,content,getFragCount()));
        ft_add_long.commit();
    }

    //----------------FRAGMENT-RELATED STUFF-----------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //----------------------FAB STUFF------------------------------

    private void getNiceFloatingActionButton() {
        //TODO: FAB horizontal on rotation
        //fabs for add/long/loc/pic/time are invisible and unclickable if (!isFabOpen)
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //action based on button pressed
        //initial fab press - first case, expands/hides the speed dial
        switch (id){
            case R.id.fab:
                //if edit_text is not empty, create a simplefragment and add it to layout
                if (!editTextMainEmpty()) {
                    addSimpleFragment(getMainEditTextStringAndClearIt());
                    hideKeyBoardLoseFocus(v);
                } else {
                    animateFAB();
                }
                break;
            case R.id.fab_action_add:
                animateFAB();
                SADinit();
                break;
            case R.id.fab_action_add_long:
                animateFAB();
                LADinit();
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

    public void animateFAB(){
        if(isFabOpen){
            //then close animations, set unclickable
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
            //open animations, set clickable
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
    //----------------------FAB STUFF------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //------------------------UTILITY------------------------------

    public static int getFragCount() {
        return fragCount;
    }

    private boolean editTextMainEmpty() {
        EditText editTextMain = (EditText) findViewById(R.id.entryEditTextMain);
        return editTextMain.getText().toString().matches("");
    }

    private String getMainEditTextStringAndClearIt() {
        EditText editTextMain = (EditText) findViewById(R.id.entryEditTextMain);
        String text = editTextMain.getText().toString().trim();
        editTextMain.setText("");
        return text;

    }

    private void hideKeyBoardLoseFocus(View v) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //------------------------UTILITY------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //------------------------DIALOG------------------------------

    private void SADinit() {
        //show dialog with custom layout
        AlertDialog.Builder xBuilder = new AlertDialog.Builder(MainActivity.this);
        View xView = getLayoutInflater().inflate(R.layout.dialog_add_simple,null);
        final EditText dasET = xView.findViewById(R.id.das_editText);
        xBuilder.setView(xView);
        final AlertDialog dialog = xBuilder.create();

        //----YES BUTTON----
        xView.findViewById(R.id.das_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: what happens if the field is empty
                Toast.makeText(MainActivity.this,getString(R.string.note_added),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                addSimpleFragment(dasET.getText().toString().trim());
            }
        });
        //----NO BUTTON----
        xView.findViewById(R.id.das_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void LADinit() {
        //show dialog with custom layout
        AlertDialog.Builder xBuilder = new AlertDialog.Builder(MainActivity.this);
        View xView = getLayoutInflater().inflate(R.layout.dialog_add_long,null);
        final EditText dalTitle = xView.findViewById(R.id.dal_editText_title);
        final EditText dalContent = xView.findViewById(R.id.dal_editText_content);
        xBuilder.setView(xView);
        final AlertDialog dialog = xBuilder.create();

        //----YES BUTTON----
        xView.findViewById(R.id.dal_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: what happens if the field is empty
                Toast.makeText(MainActivity.this,getString(R.string.note_added),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                addLongFragment(dalTitle.getText().toString().trim(),dalContent.getText().toString().trim());
            }
        });
        //----NO BUTTON----
        xView.findViewById(R.id.dal_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}


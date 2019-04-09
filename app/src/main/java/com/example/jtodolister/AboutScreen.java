package com.example.jtodolister;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import static com.example.jtodolister.R.layout.about_screen;

public class AboutScreen extends AppCompatActivity {

    private final static String website = "https://jm-ams.nl";
    private final static String github_link = "https://github.com/ayy-em/jToDo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we dont need app title and toolbar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(about_screen);
    }

    public void mailMeAbout(View view) {
        //onClick for mail button in about
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mironov-hse@yandex.ru"});
        i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        i.putExtra(Intent.EXTRA_TEXT   , "Hey there!");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AboutScreen.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToWeb(View view){
        //onClick for website button in about
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        startActivity(websiteIntent);
    }

    public void goToGithub(View view){
        //onClick for GitHub button in about
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(github_link));
        startActivity(websiteIntent);
    }
}

package com.cegrano.android.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cegrano.android.postcardview.PostCardHelper;
import com.cegrano.android.postcardview.PostCardView;
import com.cegrano.android.postcardview.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    int index;
    private PostCardView postCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                try {
                    postCardView.saveViewToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        postCardView = (PostCardView) findViewById(R.id.post_card);

        postCardView.setCardStyle(PostCardHelper.getTemper(index, null));
        postCardView.setBackgroundResource(R.mipmap.text_bg);
        postCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                postCardView.setCardStyle(PostCardHelper.getTemper(index % PostCardHelper.COUNT, null));
                postCardView.invalidate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, LiteratureTestActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

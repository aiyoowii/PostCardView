package com.cegrano.android.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cegrano.android.postcardview.Literature;
import com.cegrano.android.postcardview.LiteratureView;
import com.cegrano.android.postcardview.R;

import java.util.ArrayList;
import java.util.List;

public class LiteratureTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literature_test);
        LiteratureView literatureView = (LiteratureView) findViewById(R.id.view_literature);
        List<Literature> literature = new ArrayList<>();
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literature.add(new Literature());
        literatureView.setLiteratureList(literature);
        literatureView.notifyDateChange();
    }
}

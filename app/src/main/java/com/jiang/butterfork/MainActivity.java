package com.jiang.butterfork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jiang.annotation.BindView;


/**
 * 实现BUtterKnife的BindView
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterFork.bind(this);

        button.setText("搞定");
    }
}

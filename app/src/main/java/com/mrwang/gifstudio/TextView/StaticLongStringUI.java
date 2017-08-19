package com.mrwang.gifstudio.TextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrwang.gifstudio.R;

public class StaticLongStringUI extends AppCompatActivity {

    private StaticLayoutView staticLayoutView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_long_string_ui);
        
        staticLayoutView = (StaticLayoutView) findViewById(R.id.static_layout_view);
        
        staticLayoutView.setLayout(StaticLayoutManager.getInstance().getLongStringLayout());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}

package com.mrwang.gifstudio.TextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mrwang.gifstudio.R;

public class NormalLongStringUI extends AppCompatActivity {
    
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_long_string_ui);
        
        textView = (TextView) findViewById(R.id.text);
        textView.setText(TestSpan.getLongSpanString());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

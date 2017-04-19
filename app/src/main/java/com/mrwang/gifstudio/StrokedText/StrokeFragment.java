package com.mrwang.gifstudio.StrokedText;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * User: chengwangyong(chengwangyong@blinnnk.com)
 * Date: 2017/4/17
 * Time: 下午5:27
 */
public class StrokeFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    TextView textView = new TextView(getActivity());
    textView.setText("hhhhhhhhhh");
    return textView;
  }
}

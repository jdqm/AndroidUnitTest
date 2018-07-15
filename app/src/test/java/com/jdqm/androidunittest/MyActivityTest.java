package com.jdqm.androidunittest;

import android.widget.Button;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;


/**
 * Created by Jdqm on 2018/7/15.
 */

@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Button button =  activity.findViewById(R.id.button);
        TextView results = activity.findViewById(R.id.tvResult);
        //模拟点击按钮，调用OnClickListener#onClick
        button.performClick();
        Assert.assertEquals("Robolectric Rocks!", results.getText().toString());
    }
}
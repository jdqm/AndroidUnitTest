package com.jdqm.androidunittest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Jdqm on 2018-7-17.
 */
// @RunWith 只在混合使用 JUnit3 和 JUnit4 需要，若只使用JUnit4，可省略
@RunWith(AndroidJUnit4.class)
public class SharedPreferenceDaoTest {

    public static final String TEST_KEY = "instrumentedTest";
    public static final String TEST_STRING = "玉刚说";

    SharedPreferenceDao spDao;

    @Before
    public void setUp() {
        spDao = new SharedPreferenceDao(App.getContext());
    }

    @Test
    public void sharedPreferenceDaoWriteRead() {
        spDao.put(TEST_KEY, TEST_STRING);
        Assert.assertEquals(TEST_STRING, spDao.get(TEST_KEY));
    }
}
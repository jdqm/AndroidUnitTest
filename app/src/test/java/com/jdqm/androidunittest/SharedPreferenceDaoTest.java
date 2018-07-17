package com.jdqm.androidunittest;

import android.os.Environment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by yangsheng on 2018-7-17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = RoboApp.class)
public class SharedPreferenceDaoTest {

    public static final String TEST_KEY = "instrumentedTest";
    public static final String TEST_STRING = "玉刚说";

    SharedPreferenceDao spDao;

    @Before
    public void setUp() {
        spDao = new SharedPreferenceDao(RuntimeEnvironment.application);
    }

    @Test
    public void sharedPreferenceDaoWriteRead() {
        spDao.put(TEST_KEY, TEST_STRING);
        Assert.assertEquals(TEST_STRING, spDao.get(TEST_KEY));
    }

}
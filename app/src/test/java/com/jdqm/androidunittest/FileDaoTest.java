package com.jdqm.androidunittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jdqm on 2018/7/15.
 */
public class FileDaoTest {

    public static final String TEST_STRING = "Hello Android Unit Test.";

    FileDao fileDao;

    @Before
    public void setUp() throws Exception {
        fileDao = new FileDao();
    }

    @Test
    public void testWrite() throws Exception {
        String name = "readme.md";
        fileDao.write(name, TEST_STRING);
        String content = fileDao.read(name);
        Assert.assertEquals(TEST_STRING, content);
    }
}
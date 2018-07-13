package com.jdqm.androidunittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Jdqm on 2018-7-11.
 */
public class CalculateTest {

    private Calculate calculate;

    @Before
    public void setUp() throws Exception {
        calculate = new Calculate();
        System.out.println("setUp");
    }

    @Test
    public void add() {
        int result = calculate.add(1, 2);
        Assert.assertEquals(3, result);
    }

    @Test
    public void abs() {
        int result = calculate.abs(-1);
        Assert.assertEquals(1, result);
    }
}
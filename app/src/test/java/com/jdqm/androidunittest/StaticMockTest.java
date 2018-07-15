package com.jdqm.androidunittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Created by Jdqm on 2018/7/15.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticClass1.class, StaticClass2.class})
public class StaticMockTest {

    @Test
    public void testSomething() throws Exception{
        // mock完静态类以后，默认所有的方法都不做任何事情
        mockStatic(StaticClass1.class);
        when(StaticClass1.getStaticMethod()).thenReturn("Jdqm");
        //验证是否StaticClass1.getStaticMethod()这个方法被调用了一次
        StaticClass1.getStaticMethod();
        verifyStatic(StaticClass1.class, times(1));
    }
}

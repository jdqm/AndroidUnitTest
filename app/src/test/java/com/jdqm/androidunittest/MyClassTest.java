package com.jdqm.androidunittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

/**
 * Created by Jdqm on 2018/7/14.
 */

@RunWith(MockitoJUnitRunner.class)
public class MyClassTest {

    @Mock
    MyClass test;

    @Test
    public void mockitoTestExample() throws Exception {

        //可是使用注解@Mock替代
        //MyClass test = mock(MyClass.class);

        // 当调用test.getUniqueId()的时候返回43
        when(test.getUniqueId()).thenReturn(18);
        // 当调用test.compareTo()传入任意的Int值都返回43
        when(test.compareTo(anyInt())).thenReturn(18);

        // 当调用test.close()的时候，抛NullPointerException异常
        doThrow(new NullPointerException()).when(test).close();
        // 当调用test.execute()的时候，什么都不做
        doNothing().when(test).execute();

        assertThat(test.getUniqueId(), is(18));
        // 验证是否调用了1次test.getUniqueId()
        verify(test, times(1)).getUniqueId();
        // 验证是否没有调用过test.getUniqueId()
        verify(test, never()).getUniqueId();
        // 验证是否至少调用过2次test.getUniqueId()
        verify(test, atLeast(2)).getUniqueId();
        // 验证是否最多调用过3次test.getUniqueId()
        verify(test, atMost(3)).getUniqueId();
        // 验证是否这样调用过:test.query("test string")
        verify(test).query("test string");
        // 通过Mockito.spy() 封装List对象并返回将其mock的spy对象
        List list = new LinkedList();
        List spy = spy(list);
        //指定spy.get(0)返回"Jdqm"
        doReturn("Jdqm").when(spy).get(0);
        assertEquals("Jdqm", spy.get(0));
    }
}
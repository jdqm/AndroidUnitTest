package com.jdqm.androidunittest;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by yangsheng on 2018-7-13.
 */

@RunWith(MockitoJUnitRunner.class)
public class MockUnitTest {
    private static final String FAKE_STRING = "AndroidUnitTest";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.app_name))
                .thenReturn(FAKE_STRING);
       // ClassUnderTest myObjectUnderTest = new ClassUnderTest(mMockContext);

        // ...when the string is returned from the object under test...
        //String result = myObjectUnderTest.getHelloWorldString();

        // ...then the result should be the expected one.
        assertThat(mMockContext.getString(R.string.app_name), is(FAKE_STRING));
    }
}

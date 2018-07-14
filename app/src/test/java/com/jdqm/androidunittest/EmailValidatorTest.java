package com.jdqm.androidunittest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Jdqm on 2018-7-13.
 */
public class EmailValidatorTest {

    @Test
    public void isValidEmail() {
        assertThat(EmailValidator.isValidEmail("name@email.com"), is(true));
    }
}
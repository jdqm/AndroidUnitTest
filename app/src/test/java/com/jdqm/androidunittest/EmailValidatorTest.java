package com.jdqm.androidunittest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yangsheng on 2018-7-13.
 */
public class EmailValidatorTest {
    EmailValidator emailValidator;

    @Before
    public void setUp() throws Exception {
        emailValidator = new EmailValidator();
    }

    @Test
    public void isValidEmail() {
     //   assertThat(emailValidator.isValidEmail("name@email.com"), is(true));
        assertEquals(true, emailValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void tessss() {
        assertThat(emailValidator.isValidEmail("name@email.com"), is(true));
    }
}
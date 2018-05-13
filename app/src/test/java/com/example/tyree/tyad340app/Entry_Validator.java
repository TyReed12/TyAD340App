package com.example.tyree.tyad340app;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Entry_Validator {
    private MainActivity mActivity = new MainActivity();

    @Test
    public void validInputReturnsTrue(){
        assertThat(mActivity.inputIsValid("blah blah blah"), is(true));
    }

    @Test
    public void emptyInputReturnsTrue(){
        assertThat(mActivity.inputIsValid(""), is(false));
    }
}
package com.example.tyree.tyad340app;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tyree.tyad340app.SharedPrefHelper;

@RunWith(MockitoJUnitRunner.class)
public class Shared_Pref_Test {

    @Mock
    SharedPreferences mMockSharedPref;

    @Mock
    SharedPreferences.Editor mMockEditor ;


    private SharedPrefHelper mMockSharedPrefHelper;

    private String visitorName = "JupiterRising";

    @Before
    public void initMocks() {

        // Create a mocked SharedPreferences.
        mMockSharedPrefHelper = createMockSharedPref();

    }

    @Test
    public void sharedPreferences_SaveAndReadEntry() {

        // Save the personal information to SharedPreferences
        boolean success = mMockSharedPrefHelper.saveEntry(visitorName);

        assertThat("SharedPreferenceEntry.save... returns true",
                success, is(true));

        assertEquals(visitorName, mMockSharedPrefHelper.getEntry());

    }

    /**
     * Creates a mocked SharedPreferences object for successful read/write
     */
    private SharedPrefHelper createMockSharedPref() {

        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPref.getString(eq("visitorName"), anyString()))
                .thenReturn(visitorName);

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(mMockSharedPref.edit()).thenReturn(mMockEditor);

        return new SharedPrefHelper(mMockSharedPref);
    }
}

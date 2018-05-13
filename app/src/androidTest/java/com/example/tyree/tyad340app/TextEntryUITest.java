package com.example.tyree.tyad340app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TextEntryUITest {

        private static final String MESSAGE = "This is a test";
        private static final String PACKAGE_NAME = "com.example.tyree.tyad340app";

        /* Instantiate an IntentsTestRule object. */
        @Rule
        public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);

        @Test
        public void verifyMessageSentToMessageActivity() {

            // Types a message into a EditText element.
            onView(withId(R.id.editText)).perform(typeText(MESSAGE), closeSoftKeyboard());

            // Clicks a button to send the message to another
            // activity through an explicit intent.
            onView(withId(R.id.button)).perform(click());

            // Verifies that the DisplayMessageActivity received an intent
            // with the correct package name and message.
            Intents.intended(allOf(hasComponent(hasShortClassName(".DisplayMessageActivity")),
                    toPackage(PACKAGE_NAME)));

        }
}



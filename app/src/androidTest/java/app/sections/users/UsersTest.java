/*
 * Copyright 2016 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.sections.users;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;
import app.presentation.sections.launch.LaunchActivity;
import org.base_app_android.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static app.common.ViewActions.openDrawer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersTest {
  @Rule public ActivityTestRule<LaunchActivity> mActivityRule =
      new ActivityTestRule<>(LaunchActivity.class);

  private static final String USERNAME = "FuckBoilerplate";

  @Test public void _1_Verify_Get_Users() {
    onView(withId(R.id.rv_users))
        .perform(RecyclerViewActions.scrollToPosition(10));
  }

  @Test public void _2_Verify_Get_User() {
    onView(withId(R.id.rv_users))
        .perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));

    onView(withId(R.id.tv_name)).check(matches(isNotEmpty()));
  }

  @Test public void _3_Verify_Search_User() {
    onView(withId(R.id.drawer_layout)).perform(openDrawer());

    onView(Matchers.allOf(withId(R.id.navigation_view),
        hasDescendant(withText(R.string.find_user)))).perform(click());

    onView(withId(R.id.et_name)).perform(click(), replaceText(USERNAME), closeSoftKeyboard());
    onView(withId(R.id.bt_find_user)).perform(click());

    onView(withId(R.id.tv_name)).check(matches(isNotEmpty()));
  }

  private static Matcher<View> isNotEmpty() {

    return new BoundedMatcher<View, TextView>(TextView.class) {

      @Override public void describeTo(final Description description) {
        description.appendText("not empty text: ");
      }

      @Override public boolean matchesSafely(final TextView textView) {
        return !textView.getText().toString().isEmpty();
      }
    };
  }
}

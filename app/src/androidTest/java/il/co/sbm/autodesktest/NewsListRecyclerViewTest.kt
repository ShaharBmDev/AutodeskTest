package il.co.sbm.autodesktest

import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.linkedin.android.testbutler.TestButler
import il.co.sbm.autodesktest.model.network.NewsApiService
import il.co.sbm.autodesktest.model.network.objects.response.NewsApiResponse
import il.co.sbm.autodesktest.model.repositories.NewsRepository
import il.co.sbm.autodesktest.ui.activities.MainActivity
import il.co.sbm.autodesktest.ui.adapters.NewsAdapter
import il.co.sbm.autodesktest.utils.testUtils.EspressoTestingIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class NewsListRecyclerViewTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        //register to EspressoTestingIdlingResource to keep track of idling state.
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource.getIdlingResource())
    }

    /**
     * An espresso test to scroll and click on an article item after data is received.
     */
    @Test
    fun scrollAndLastItemClickTest() {
        //make sure list is visible.
        onView(withId(R.id.rv_newsList_list)).check(matches(isDisplayed()))

        //get last position of data in the list.
        val lastPosition = NewsRepository.getTopHeadlines().value?.data?.articles?.size?.minus(1) ?: NO_POSITION

        // perform click on last item in the list if in fact the list has data.
        if (lastPosition > NO_POSITION) {
            onView(withId(R.id.rv_newsList_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition<NewsAdapter.ViewHolder>(
                    lastPosition,
                    click()
                )
            )
        }
    }

    @After
    fun unregisterIdlingResource() {
        //unregister to EspressoTestingIdlingResource to keep track of idling state.
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource.getIdlingResource())
    }
}
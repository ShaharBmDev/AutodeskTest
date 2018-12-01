package il.co.sbm.autodesktest.utils.testUtils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import org.jetbrains.annotations.TestOnly

/**
 * Used to determine if all the tasks are done and the state is idling, allowing testing for espresso.
 */
object EspressoTestingIdlingResource {

    private const val TAG = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(TAG)
    private var mCount = 0

    /**
     * Increments by 1 to indicate that there is at least one task working.
     */
    @TestOnly
    fun increment() {
        mCountingIdlingResource.increment()
        mCount++
    }

    /**
     * Decrements by 1 to indicate that one of the tasks is done.
     */
    @TestOnly
    fun decrement() {
        if (mCount > 0) {
            mCountingIdlingResource.decrement()
            mCount--
        }
    }

    /**
     * Returns the IdlingResource object.
     */
    @TestOnly
    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }
}
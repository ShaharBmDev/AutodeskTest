package il.co.sbm.autodesktest.model.global

import android.app.Application

class AutodeskTestApplication : Application() {

    companion object {
        lateinit var mInstance: AutodeskTestApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }

//    todo unit test.
}
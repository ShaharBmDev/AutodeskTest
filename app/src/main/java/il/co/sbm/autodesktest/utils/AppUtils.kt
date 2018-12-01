package il.co.sbm.autodesktest.utils

import android.text.TextUtils

object AppUtils {

    /**
     * @param iStringToGet the string to check
     * @return the String if it is not null, or empty string if it is null.
     */
    fun getStringOrEmpty(iStringToGet: String?): String {
        return if (TextUtils.isEmpty(iStringToGet)) "" else iStringToGet!!
    }
}
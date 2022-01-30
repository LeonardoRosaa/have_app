package com.leonardo.have_app

import android.content.pm.ApplicationInfo

class ApplicationModel(
    packageName: String,
    category: Category,
    enabled: Boolean
) : Application(
    packageName,
    category,
    enabled,
) {

    companion object {

        @JvmStatic
        fun fromApplicationInfo(applicationInfo: ApplicationInfo): ApplicationModel {
            return ApplicationModel(
                applicationInfo.packageName,
                getByValue(applicationInfo.category),
                applicationInfo.enabled
            )
        }
    }


}
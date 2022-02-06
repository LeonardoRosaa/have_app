package com.leonardo.have_app.data.models

import android.content.pm.ApplicationInfo
import com.leonardo.have_app.domain.entities.Application
import com.leonardo.have_app.domain.entities.Category
import com.leonardo.have_app.domain.entities.getByValue

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
        fun fromApplicationInfo(applicationInfo: ApplicationInfo): Application {
            return Application(
                applicationInfo.packageName,
                getByValue(applicationInfo.category),
                applicationInfo.enabled
            )
        }
    }


}
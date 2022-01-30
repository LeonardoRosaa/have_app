package com.leonardo.have_app

import android.content.pm.PackageManager

interface ApplicationGateway {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [ApplicationModel],
     * does not return an [Exception]
     */
    fun getApplication(packageName: String): ApplicationModel
}

class PackageManagerGateway(private val packageManager: PackageManager): ApplicationGateway {
    override fun getApplication(packageName: String): ApplicationModel {
        val applicationFound = packageManager.getApplicationInfo(packageName, 0)

        return ApplicationModel.fromApplicationInfo(applicationFound)
    }
}
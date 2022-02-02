package com.leonardo.have_app

import com.leonardo.have_app.core.types.GetApplication

interface ApplicationService {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [Application],
     * does not return an [Exception]
     */
    fun getApplication(packageName: String): GetApplication
}

class PackageManagerService(private val applicationGateway: ApplicationGateway): ApplicationService {
    override fun getApplication(packageName: String): GetApplication {
        return applicationGateway.getApplication(packageName)
    }
}
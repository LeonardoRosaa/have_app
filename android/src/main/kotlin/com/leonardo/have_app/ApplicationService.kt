package com.leonardo.have_app

interface ApplicationService {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [Application],
     * does not return an [Exception]
     */
    fun getApplication(packageName: String): Application
}

class PackageManagerService(private val applicationGateway: ApplicationGateway): ApplicationService {
    override fun getApplication(packageName: String): Application {
        return applicationGateway.getApplication(packageName)
    }
}
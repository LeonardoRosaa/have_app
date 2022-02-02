package com.leonardo.have_app

import arrow.core.Either
import arrow.core.left
import com.leonardo.have_app.core.exceptions.ApplicationPackageNameDoesNotBeEmptyException
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
        if (packageName.isEmpty()) return Either.Left(ApplicationPackageNameDoesNotBeEmptyException())

        return applicationGateway.getApplication(packageName)
    }
}
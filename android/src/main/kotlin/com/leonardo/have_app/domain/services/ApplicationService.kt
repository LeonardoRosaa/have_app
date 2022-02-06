package com.leonardo.have_app.domain.services

import arrow.core.Either
import com.leonardo.have_app.core.exceptions.ApplicationPackageNameDoesNotBeEmptyException
import com.leonardo.have_app.core.types.GetApplication
import com.leonardo.have_app.data.gateways.ApplicationGateway

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
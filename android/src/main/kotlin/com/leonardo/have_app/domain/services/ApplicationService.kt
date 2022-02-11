package com.leonardo.have_app.domain.services

import arrow.core.Either
import com.leonardo.have_app.core.exceptions.ApplicationPackageNameDoesNotBeEmptyException
import com.leonardo.have_app.core.exceptions.CantFoundApplicationsInstalledException
import com.leonardo.have_app.core.exceptions.GetApplicationsInstalledException
import com.leonardo.have_app.core.types.GetAllApplications
import com.leonardo.have_app.core.types.GetApplication
import com.leonardo.have_app.data.gateways.ApplicationGateway
import com.leonardo.have_app.data.models.ApplicationModel

interface ApplicationService {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [Application],
     * does not return an [Exception]
     */
    fun getApplication(packageName: String): GetApplication

    /**
     * Get all applications installed
     *
     * @return if there's applications returns a [List] of [ApplicationModel],
     * if else can't found applications, returns [CantFoundApplicationsInstalledException]
     * or if else throw another error [GetApplicationsInstalledException]
     */
    fun getInstalledApplications(): GetAllApplications
}

class PackageManagerService(private val applicationGateway: ApplicationGateway): ApplicationService {
    override fun getApplication(packageName: String): GetApplication {
        if (packageName.isEmpty()) return Either.Left(ApplicationPackageNameDoesNotBeEmptyException())

        return applicationGateway.getApplication(packageName)
    }

    override fun getInstalledApplications(): GetAllApplications {
        return applicationGateway.getInstalledApplications()
    }
}
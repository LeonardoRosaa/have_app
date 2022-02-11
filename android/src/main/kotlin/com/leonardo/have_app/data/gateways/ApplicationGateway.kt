package com.leonardo.have_app.data.gateways

import android.content.pm.PackageManager
import arrow.core.Either
import com.leonardo.have_app.core.exceptions.*
import com.leonardo.have_app.core.types.GetAllApplications
import com.leonardo.have_app.core.types.GetApplication
import com.leonardo.have_app.data.models.ApplicationModel
import com.leonardo.have_app.domain.entities.Application
import java.lang.Exception

interface ApplicationGateway {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [ApplicationModel],
     * does not return an [ApplicationException]
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

class PackageManagerGateway(private val packageManager: PackageManager) : ApplicationGateway {
    override fun getApplication(packageName: String): GetApplication {
        return try {
            val applicationFound = packageManager.getApplicationInfo(packageName, 0)

            Either.Right(ApplicationModel.fromApplicationInfo(applicationFound))
        } catch (error: PackageManager.NameNotFoundException) {
            Either.Left(ApplicationNotFoundException(packageName))
        } catch (error: Exception) {
            Either.Left(GetApplicationException(error.toString()))
        }
    }

    override fun getInstalledApplications(): GetAllApplications {
        try {
            val applications = packageManager.getInstalledApplications(0)

            if (applications.isEmpty())
                return Either.Left(CantFoundApplicationsInstalledException())

            return Either.Right(applications.map { ApplicationModel.fromApplicationInfo(it) })
        } catch (error: Exception) {
            return Either.Left(GetApplicationsInstalledException(error.toString()))
        }
    }
}
package com.leonardo.have_app

import android.content.pm.PackageManager
import arrow.core.Either
import com.leonardo.have_app.core.exceptions.*
import com.leonardo.have_app.core.types.GetApplication
import java.lang.Exception

interface ApplicationGateway {
    /**
     * Get application by [packageName]
     * @return if does application exists returns [ApplicationModel],
     * does not return an [ApplicationException]
     */
    fun getApplication(packageName: String): GetApplication
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
}
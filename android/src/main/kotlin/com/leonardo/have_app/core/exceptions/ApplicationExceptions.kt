package com.leonardo.have_app.core.exceptions

import java.lang.Exception

abstract class ApplicationException(val name: String?, error: String?) : Exception(error)

class ApplicationNotFoundException(packageName: String) :
    ApplicationException(
        "ApplicationNotFoundException",
        "Application $packageName does not exists or not found"
    )

class GetApplicationException(error: String?) :
    ApplicationException("GetApplicationException", error)

class ApplicationPackageNameDoesNotBeEmptyException() :
    ApplicationException(
        "ApplicationPackageNameDoesNotBeEmptyException",
        "The package name of application does not be empty"
    )

class CantFoundApplicationsInstalledException() : ApplicationException(
    "CantFoundApplicationsInstalledException",
    "cant found applications installed in device"
)

class GetApplicationsInstalledException(error: String?): ApplicationException(
    "GetApplicationsInstalledException",
    error
)
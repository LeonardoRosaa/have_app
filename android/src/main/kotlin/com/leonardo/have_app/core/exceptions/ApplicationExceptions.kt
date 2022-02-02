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
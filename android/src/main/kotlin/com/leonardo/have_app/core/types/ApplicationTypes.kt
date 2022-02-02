package com.leonardo.have_app.core.types

import arrow.core.Either
import com.leonardo.have_app.Application
import com.leonardo.have_app.ApplicationModel
import com.leonardo.have_app.core.exceptions.ApplicationException

typealias GetApplication = Either<ApplicationException, Application>
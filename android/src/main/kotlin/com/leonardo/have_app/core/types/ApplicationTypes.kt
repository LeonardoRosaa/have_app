package com.leonardo.have_app.core.types

import arrow.core.Either
import com.leonardo.have_app.core.exceptions.ApplicationException
import com.leonardo.have_app.domain.entities.Application

typealias GetApplication = Either<ApplicationException, Application>
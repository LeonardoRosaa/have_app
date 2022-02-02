package com.leonardo.have_app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import arrow.core.Either
import com.leonardo.have_app.core.exceptions.ApplicationNotFoundException
import com.leonardo.have_app.core.exceptions.ApplicationPackageNameDoesNotBeEmptyException
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(MockitoJUnitRunner::class)
class PackageManagerServiceTestTest {

    @Mock
    private lateinit var mockApplicationGateway: ApplicationGateway

    @Test
    fun `get package by name`() {
        val application = ApplicationModel(
            "instagram",
            Category.SOCIAL,
            true
        )

        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)
        `when`(mockApplicationGateway.getApplication(anyString()))
            .thenAnswer { Either.Right(application) }

        val result = applicationService.getApplication("instagram")

        result.shouldBeRight(application)
    }

    @Test
    fun `package name does not be empty`() {
        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)

        val result = applicationService.getApplication("")

        result.shouldBeLeft(ApplicationPackageNameDoesNotBeEmptyException())
    }

    @Test
    fun `package does not found`() {
        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)
        `when`(mockApplicationGateway.getApplication(anyString()))
            .thenAnswer { Either.Left(ApplicationNotFoundException("instagram")) }

        val result = applicationService.getApplication("instagram")
        result.shouldBeLeft(ApplicationNotFoundException("instagram"))
    }
}
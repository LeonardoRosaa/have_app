package com.leonardo.have_app

import arrow.core.Either
import com.leonardo.have_app.core.exceptions.ApplicationNotFoundException
import com.leonardo.have_app.core.exceptions.ApplicationPackageNameDoesNotBeEmptyException
import com.leonardo.have_app.core.exceptions.CantFoundApplicationsInstalledException
import com.leonardo.have_app.core.exceptions.GetApplicationsInstalledException
import com.leonardo.have_app.data.gateways.ApplicationGateway
import com.leonardo.have_app.data.models.ApplicationModel
import com.leonardo.have_app.domain.entities.Category
import com.leonardo.have_app.domain.services.ApplicationService
import com.leonardo.have_app.domain.services.PackageManagerService
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

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

    @Test
    fun `get installed applications`() {
        val application = ApplicationModel(
            "instagram",
            Category.SOCIAL,
            true
        )
        val application1 = ApplicationModel(
            "com.android.gmail",
            Category.PRODUCTIVITY,
            true
        )
        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)
        `when`(mockApplicationGateway.getInstalledApplications())
            .thenAnswer { Either.Right(listOf(application, application1)) }

        val result = applicationService.getInstalledApplications()
        result.fold({ 0 }, { assertEquals(it.size, 2) })
    }

    @Test
    fun `get installed applications is empty`() {

        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)
        `when`(mockApplicationGateway.getInstalledApplications())
            .thenAnswer { Either.Left(CantFoundApplicationsInstalledException()) }

        val result = applicationService.getInstalledApplications()
        result.shouldBeLeft(CantFoundApplicationsInstalledException())
    }

    @Test
    fun `get installed applications throw another exception`() {

        val applicationService: ApplicationService = PackageManagerService(mockApplicationGateway)
        `when`(mockApplicationGateway.getInstalledApplications())
            .thenAnswer {
                Either.Left(
                    GetApplicationsInstalledException("error on get all installed applications")
                )
            }

        val result = applicationService.getInstalledApplications()
        result.shouldBeLeft(
            GetApplicationsInstalledException("error on get all installed applications")
        )
    }
}
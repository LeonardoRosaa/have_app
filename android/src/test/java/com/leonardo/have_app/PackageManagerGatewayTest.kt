package com.leonardo.have_app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.leonardo.have_app.core.exceptions.ApplicationNotFoundException
import com.leonardo.have_app.core.exceptions.CantFoundApplicationsInstalledException
import com.leonardo.have_app.core.exceptions.GetApplicationsInstalledException
import com.leonardo.have_app.data.gateways.ApplicationGateway
import com.leonardo.have_app.data.gateways.PackageManagerGateway
import com.leonardo.have_app.data.models.ApplicationModel
import com.leonardo.have_app.domain.entities.Category
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import kotlin.test.assertEquals

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(MockitoJUnitRunner::class)
class PackageManagerGatewayTest {

    @Mock
    private lateinit var mockPackageManager: PackageManager

    @Test
    fun `get package by name`() {
        val applicationInfo = mock(ApplicationInfo::class.java)
        applicationInfo.packageName = "instagram"
        applicationInfo.category = Category.SOCIAL.value
        applicationInfo.enabled = true

        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getApplicationInfo(anyString(), anyInt()))
            .thenAnswer { applicationInfo }

        val result = applicationGateway.getApplication("instagram")

        result.shouldBeRight(
            ApplicationModel(
                "instagram",
                Category.SOCIAL,
                true
            )
        )
    }

    @Test
    fun `package does not found`() {
        val applicationInfo = mock(ApplicationInfo::class.java)
        applicationInfo.packageName = "instagram"
        applicationInfo.category = Category.SOCIAL.value
        applicationInfo.enabled = true

        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getApplicationInfo(anyString(), anyInt()))
            .thenAnswer { throw PackageManager.NameNotFoundException() }

        val result = applicationGateway.getApplication("instagram")

        result.shouldBeLeft(ApplicationNotFoundException("instagram"))
    }

    @Test
    fun `get installed applications`() {
        val applicationInfo = mock(ApplicationInfo::class.java)
        applicationInfo.packageName = "instagram"
        applicationInfo.category = Category.SOCIAL.value
        applicationInfo.enabled = true

        val applicationInfo1 = mock(ApplicationInfo::class.java)
        applicationInfo1.packageName = "com.android.calculator"
        applicationInfo1.category = Category.UNDEFINED.value
        applicationInfo1.enabled = true

        val applicationInfo2 = mock(ApplicationInfo::class.java)
        applicationInfo1.packageName = "com.android.gmail"
        applicationInfo1.category = Category.PRODUCTIVITY.value
        applicationInfo1.enabled = true

        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getInstalledApplications(anyInt()))
            .thenAnswer {
                listOf<ApplicationInfo>(
                    applicationInfo,
                    applicationInfo1,
                    applicationInfo2
                )
            }

        val result = applicationGateway.getInstalledApplications()

        result.fold({ 0 }, { assertEquals(it.size, 3) })
    }

    @Test
    fun `get installed applications is empty`() {
        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getInstalledApplications(anyInt()))
            .thenAnswer { listOf<ApplicationModel>() }

        val result = applicationGateway.getInstalledApplications()

        result.shouldBeLeft(CantFoundApplicationsInstalledException())
    }

    @Test
    fun `get installed applications throw another exception`() {
        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getInstalledApplications(anyInt()))
            .thenAnswer { throw PackageManager.NameNotFoundException() }

        val result = applicationGateway.getInstalledApplications()

        result.shouldBeLeft(
            GetApplicationsInstalledException(
                PackageManager.NameNotFoundException().toString()
            )
        )
    }
}
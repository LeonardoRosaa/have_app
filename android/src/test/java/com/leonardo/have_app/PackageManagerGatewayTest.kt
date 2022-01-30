package com.leonardo.have_app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PackageManagerGatewayTest {

    @Mock
    private lateinit var mockPackageManager: PackageManager

    @Test
    fun getApplicationByPackageName() {
        val applicationInfo = mock(ApplicationInfo::class.java)
        applicationInfo.packageName = "instagram"
        applicationInfo.category = Category.SOCIAL.value
        applicationInfo.enabled = true

        val applicationGateway: ApplicationGateway = PackageManagerGateway(mockPackageManager)
        `when`(mockPackageManager.getApplicationInfo(anyString(), anyInt()))
            .thenAnswer { applicationInfo }

        val result = applicationGateway.getApplication("instagram")

        assertEquals(result.packageName, "instagram")
        assertEquals(result.category, Category.SOCIAL)
        assertEquals(result.enabled, true)
    }

}
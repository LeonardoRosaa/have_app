package com.leonardo.have_app

import android.content.Context
import androidx.annotation.NonNull
import com.leonardo.have_app.data.gateways.ApplicationGateway
import com.leonardo.have_app.data.gateways.PackageManagerGateway
import com.leonardo.have_app.domain.entities.GetApplication
import com.leonardo.have_app.domain.services.ApplicationService
import com.leonardo.have_app.domain.services.PackageManagerService

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** HaveAppPlugin */
class HaveAppPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private lateinit var context: Context

    private lateinit var applicationService: ApplicationService

    private lateinit var applicationGateway: ApplicationGateway

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "have_app")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
        applicationGateway = PackageManagerGateway(context.packageManager)
        applicationService = PackageManagerService(applicationGateway)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
        fun getApplication() {
            val obj = Json.decodeFromString<GetApplication>(call.arguments as String)
            val application = applicationService.getApplication(obj.packageName)

            application.fold(
                { error ->
                    result.error(error.name, error.message, error)
                },
                { app -> result.success(Json.encodeToString(app)) })
        }

        fun getInstalledApplications() {
            val application = applicationService.getInstalledApplications()

            application.fold(
                { error ->
                    result.error(error.name, error.message, error)
                },
                { app -> result.success(Json.encodeToString(app)) })
        }

        when (call.method) {
            "getApplication" -> getApplication()
            "getInstalledApplications" -> getInstalledApplications()
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}

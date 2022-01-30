package com.leonardo.have_app

import android.content.Context
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** HaveAppPlugin */
class HaveAppPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

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

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    val obj = Json.decodeFromString<GetApplication>(call.arguments as String)
    val a = applicationService.getApplication(obj.packageName)

    result.success(Json.encodeToString(a))
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}

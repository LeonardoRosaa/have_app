import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

class HaveApp {
  static const MethodChannel _channel = MethodChannel('have_app');

  static Future<String?> get platformVersion async {
    try {
      final dynamic version = await _channel.invokeMethod(
          'getPlatformVersion', jsonEncode({"packageName": "instagram"}));
      print(version);
    } catch (error) {
      print(error);
    }

    return '111';
  }
}

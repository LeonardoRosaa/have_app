
import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

class HaveApp {
  static const MethodChannel _channel = MethodChannel('have_app');

  static Future<String?> get platformVersion async {
    final version = await _channel.invokeMethod('getApplication', jsonEncode({ "packageName": "instagram"}));
    print(version);
   
    return '111';
  }
}

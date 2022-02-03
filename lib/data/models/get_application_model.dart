import 'dart:convert';

import 'package:have_app/domain/entities/get_application.dart';

class GetApplicationModel extends GetApplication {

  const GetApplicationModel({
    required String packageName,
  }): super(packageName: packageName);

  Map<String, dynamic> toMap() {
    return {
      'packageName': packageName,
    };
  }

  String toJson() => jsonEncode(toMap());
}
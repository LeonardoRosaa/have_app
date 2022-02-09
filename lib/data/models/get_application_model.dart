import 'dart:convert';

import 'package:have_app/domain/entities/get_application.dart';

/// It class extends [GetApplicationModel] to care about other methods, like as encode and decode.

class GetApplicationModel extends GetApplication {
  GetApplicationModel({
    required String packageName,
  }) : super(packageName: packageName);

  Map<String, dynamic> toMap() {
    return {
      'packageName': packageName,
    };
  }

  factory GetApplicationModel.fromMap(Map<String, dynamic> map) {
    return GetApplicationModel(
      packageName: map['packageName'] ?? '',
    );
  }

  String toJson() => jsonEncode(toMap());
}

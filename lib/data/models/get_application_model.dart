import 'dart:convert';

import 'package:have_app/domain/entities/get_application.dart';

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

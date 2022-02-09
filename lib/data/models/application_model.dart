import 'dart:convert';

import '../../domain/entities/application.dart';

class ApplicationModel extends Application {
  const ApplicationModel({
    required String packageName,
    required bool enabled,
    required Category category,
  }) : super(
          packageName: packageName,
          enabled: enabled,
          category: category,
        );

  factory ApplicationModel.fromMap(Map<String, dynamic> map) {
    return ApplicationModel(
        packageName: map['packageName'] ?? '',
        enabled: map['enabled'] ?? false,
        category:
            Category.values.byName(map['category'].toString().toLowerCase()));
  }

  factory ApplicationModel.fromJson(String source) =>
      ApplicationModel.fromMap(json.decode(source));
}

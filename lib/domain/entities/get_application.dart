import 'package:have_app/core/faults/application_exception.dart';

class GetApplication {
  GetApplication({required this.packageName})
      : assert(
          packageName.isNotEmpty,
          const ApplicationPackageNameDoesNotBeEmptyException(),
        );

  final String packageName;
}

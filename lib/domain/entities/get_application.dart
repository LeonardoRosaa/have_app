import 'package:have_app/core/faults/application_exception.dart';

/// May use this class to get an application.
///
/// If the [packageName] is empty, the class constructor
/// throw [ApplicationPackageNameDoesNotBeEmptyException] exception.
class GetApplication {
  GetApplication({required this.packageName})
      : assert(
          packageName.isNotEmpty,
          const ApplicationPackageNameDoesNotBeEmptyException(),
        );

  final String packageName;
}

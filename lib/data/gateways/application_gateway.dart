import 'package:dartz/dartz.dart';
import 'package:flutter/services.dart';
import 'package:have_app/core/channels.dart';
import 'package:have_app/data/models/application_model.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:have_app/data/models/get_application_model.dart';

abstract class ApplicationGateway {
  const ApplicationGateway();

  /// Get application by [packageName] and if does application exists
  /// returns [ApplicationModel], does not return an [ApplicationException]
  Future<Either<ApplicationException, ApplicationModel>> getPackage(
    GetApplicationModel getApplication,
  );
}

class ApplicationGatewayImpl implements ApplicationGateway {
  const ApplicationGatewayImpl({required this.channel});

  final MethodChannel channel;

  @override
  Future<Either<ApplicationException, ApplicationModel>> getPackage(
    GetApplicationModel getApplication,
  ) async {
    try {
      final application = await channel.invokeMethod(
        Channels.getApplication.value,
        getApplication.toJson(),
      );

      return right(ApplicationModel.fromJson(application));
    } on PlatformException catch (error) {
      switch (error.code) {
        case ApplicationPackageNameDoesNotBeEmptyException.code:
          return left(ApplicationPackageNameDoesNotBeEmptyException(error));
        case ApplicationNotFoundException.code:
          return left(ApplicationNotFoundException(error));
        default:
          return left(GetApplicationException(error));
      }
    }
  }
}

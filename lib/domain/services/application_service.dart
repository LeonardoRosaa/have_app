import 'package:dartz/dartz.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:have_app/data/gateways/application_gateway.dart';
import 'package:have_app/data/models/models.dart';
import 'package:have_app/domain/entities/entities.dart';

abstract class ApplicationService {
  const ApplicationService();

  Future<Either<ApplicationException, Application>> getPackage(
    GetApplication getApplication,
  );
}

class ApplicationServiceImpl implements ApplicationService {
  const ApplicationServiceImpl({required this.applicationGateway});

  final ApplicationGateway applicationGateway;

  @override
  Future<Either<ApplicationException, Application>> getPackage(
      GetApplication getApplication) {
    return applicationGateway.getPackage(
      GetApplicationModel(packageName: getApplication.packageName),
    );
  }
}

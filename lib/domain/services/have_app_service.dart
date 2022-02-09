import 'package:flutter/services.dart';
import 'package:have_app/core/channels.dart';
import 'package:have_app/data/gateways/application_gateway.dart';
import 'package:have_app/domain/entities/get_application.dart';
import 'package:have_app/domain/entities/application.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:dartz/dartz.dart';
import 'package:have_app/domain/services/application_service.dart';

/// The main class of this package, through this class client will access
/// the main methods of this package.
class HaveApp implements ApplicationService {
  HaveApp()
      : _applicationService = ApplicationServiceImpl(
          applicationGateway: ApplicationGatewayImpl(
            channel: MethodChannel(
              Channels.principal.value,
            ),
          ),
        );

  final ApplicationService _applicationService;

  @override
  Future<Either<ApplicationException, Application>> getPackage(
      GetApplication getApplication) {
    return _applicationService.getPackage(getApplication);
  }
}

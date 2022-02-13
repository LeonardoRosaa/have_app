abstract class ApplicationException implements Exception {
  const ApplicationException([this.error]);

  final dynamic error;
}

class ApplicationNotFoundException extends ApplicationException {
  static const code = 'ApplicationNotFoundException';

  const ApplicationNotFoundException([dynamic error]) : super(error);
}

/// It's generic [Exception] for when occurs a failure
/// on try does get an application
class GetApplicationException extends ApplicationException {
  static const code = 'GetApplicationException';

  const GetApplicationException([dynamic error]) : super(error);
}

class ApplicationPackageNameDoesNotBeEmptyException
    extends ApplicationException {
  static const code = 'ApplicationPackageNameDoesNotBeEmptyException';

  const ApplicationPackageNameDoesNotBeEmptyException([dynamic error])
      : super(error);
}

/// Can't not found anything application installed in device
class CantFoundApplicationsInstalledException extends ApplicationException {
  static const code = 'CantFoundApplicationsInstalledException';

  const CantFoundApplicationsInstalledException()
      : super(
          'Can not found applications installed in device',
        );
}

/// There's some error on get installed applications in device
class  GetApplicationsInstalledException extends ApplicationException {
  static const code = 'GetApplicationsInstalledException';

  const GetApplicationsInstalledException([Object? error]): super(error);
}
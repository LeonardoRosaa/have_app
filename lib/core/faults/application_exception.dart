abstract class ApplicationException implements Exception {
  const ApplicationException([this.error]);

  final dynamic error;
}

class ApplicationNotFoundException extends ApplicationException {

  static const code = 'ApplicationNotFoundException';

  const ApplicationNotFoundException([dynamic error]): super(error);
}

class GetApplicationException extends ApplicationException {

  static const code = 'GetApplicationException';

  const GetApplicationException([dynamic error]): super(error);
}

class ApplicationPackageNameDoesNotBeEmptyException extends ApplicationException {

  static const code = 'ApplicationPackageNameDoesNotBeEmptyException';

  const ApplicationPackageNameDoesNotBeEmptyException([dynamic error]): super(error); 
}
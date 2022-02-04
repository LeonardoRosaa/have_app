import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:have_app/data/models/application_model.dart';
import 'package:have_app/data/models/get_application_model.dart';
import 'package:have_app/domain/entities/entities.dart';
import 'package:have_app/domain/services/application_service.dart';
import 'package:mocktail/mocktail.dart';

import '../../mocks.dart';

const application = ApplicationModel(
  packageName: 'com.android.bluetooh',
  enabled: true,
  category: Category.undefined,
);

void main() {
  final applicationGateway = MockApplicationGateway();
  final applicationService =
      ApplicationServiceImpl(applicationGateway: applicationGateway);

  setUpAll(() {
    registerFallbackValue(
        GetApplicationModel(packageName: 'com.android.bluetooh'));
  });

  group('application gateway', () {
    group('get application', () {
      test('does found', () async {
        when(
          () => applicationGateway.getPackage(any<GetApplicationModel>()),
        ).thenAnswer((_) async => right(application));

        final result = await applicationService.getPackage(
          GetApplication(packageName: 'com.android.bluetooth'),
        );

        expect(result.fold(id, id), application);
      });

      test('does package name not be empty', () async {
        expect(() => GetApplicationModel(packageName: ''), throwsAssertionError);
      });

      test('and not found', () async {
        when(
          () => applicationGateway.getPackage(any<GetApplicationModel>()),
        ).thenAnswer((_) async => left(const ApplicationNotFoundException()));

        final result = await applicationService.getPackage(
          GetApplication(packageName: 'instagram'),
        );

        expect(result.fold(id, id), isA<ApplicationNotFoundException>());
      });
    });
  });
}

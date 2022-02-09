import 'package:dartz/dartz.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:have_app/core/channels.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:have_app/data/gateways/application_gateway.dart';
import 'package:have_app/data/models/application_model.dart';
import 'package:have_app/data/models/get_application_model.dart';
import 'package:mocktail/mocktail.dart';

import '../../mocks.dart';

const _getApplicationResult = '''
{
  "packageName": "com.android.bluetooth",
  "enabled": true,
  "category": "UNDEFINED"
}
''';

void main() {
  final channel = MockMethodChannel();
  final applicationGateway = ApplicationGatewayImpl(channel: channel);

  group('application gateway', () {
    group('get application', () {
      test('does found', () async {
        when(
          () => channel.invokeMethod(
            Channels.getApplication.value,
            any<String>(),
          ),
        ).thenAnswer(
          (_) async => _getApplicationResult,
        );

        final result = await applicationGateway.getPackage(
          GetApplicationModel(packageName: 'com.android.bluetooth'),
        );

        final expectedApplication =
            ApplicationModel.fromJson(_getApplicationResult);

        expect(result.fold(id, id), expectedApplication);
      });

      test('does package name not be empty', () async {
        when(
          () => channel.invokeMethod(
            Channels.getApplication.value,
            any<String>(),
          ),
        ).thenThrow(
          PlatformException(
            code: 'ApplicationPackageNameDoesNotBeEmptyException',
          ),
        );

        final result = await applicationGateway.getPackage(
          GetApplicationModel(packageName: 'instagram'),
        );

        expect(result.fold(id, id),
            isA<ApplicationPackageNameDoesNotBeEmptyException>());
      });

      test('and not found', () async {
        when(
          () => channel.invokeMethod(
            Channels.getApplication.value,
            any<String>(),
          ),
        ).thenThrow(
          PlatformException(
            code: 'ApplicationNotFoundException',
          ),
        );

        final result = await applicationGateway.getPackage(
          GetApplicationModel(packageName: 'instagram'),
        );

        expect(result.fold(id, id), isA<ApplicationNotFoundException>());
      });
    });
  });
}

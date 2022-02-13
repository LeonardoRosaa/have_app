import 'package:dartz/dartz.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:have_app/core/channels.dart';
import 'package:have_app/core/faults/application_exception.dart';
import 'package:have_app/data/gateways/application_gateway.dart';
import 'package:have_app/data/models/models.dart';
import 'package:mocktail/mocktail.dart';

import '../../mocks.dart';

const _getApplicationResult = '''
{
  "packageName": "com.android.bluetooth",
  "enabled": true,
  "category": "UNDEFINED"
}
''';

const _getAllApplicationsResult = '''
[
  {
    "packageName": "com.android.bluetooth",
    "enabled": true,
    "category": "UNDEFINED"
  },
  {
    "packageName": "com.android.gmail",
    "enabled": true,
    "category": "PRODUCTIVITY"
  }
]
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

    group('get all installed applications', () {
      test('does found', () async {
        when(() =>
                channel.invokeMethod(Channels.getInstalledApplications.value))
            .thenAnswer((_) async => _getAllApplicationsResult);

        final result = await applicationGateway.getAllInstalled();

        expect(result.fold(id, id), isA<List<ApplicationModel>>());
        result.fold((_) {}, (r) => expect(r.length, 2));
      });

      test('can not found', () async {
        when(
          () => channel.invokeMethod(Channels.getInstalledApplications.value),
        ).thenThrow(
          PlatformException(code: 'CantFoundApplicationsInstalledException'),
        );

        final result = await applicationGateway.getAllInstalled();

        expect(result.fold(id, id), isA<CantFoundApplicationsInstalledException>());
      });

      test('throw exception', () async {
        when(
          () => channel.invokeMethod(Channels.getInstalledApplications.value),
        ).thenThrow(
          PlatformException(code: 'GetApplicationsInstalledException'),
        );

        final result = await applicationGateway.getAllInstalled();

        expect(result.fold(id, id), isA<GetApplicationsInstalledException>());
      });
    });
  });
}

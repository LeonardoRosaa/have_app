import 'package:flutter/services.dart';
import 'package:have_app/data/gateways/application_gateway.dart';
import 'package:mocktail/mocktail.dart';

class MockMethodChannel extends Mock implements MethodChannel {}

class MockApplicationGateway extends Mock implements ApplicationGateway {}

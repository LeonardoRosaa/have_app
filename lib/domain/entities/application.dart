import 'package:equatable/equatable.dart';

/// The [Application] class is a **application**, **app** or **package** installed
/// in client device.
class Application extends Equatable {
  const Application({
    required this.packageName,
    required this.category,
    required this.enabled,
  });

  final String packageName;

  final Category category;

  final bool enabled;

  @override
  List<Object?> get props => [packageName, category, enabled];
}

/// This [enumeration] is possibles categories of an application
enum Category {
  accessibility,
  audio,
  game,
  image,
  maps,
  news,
  productivity,
  social,
  undefined,
}

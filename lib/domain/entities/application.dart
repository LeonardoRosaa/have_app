import 'package:equatable/equatable.dart';

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

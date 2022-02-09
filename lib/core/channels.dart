/// Channels name to does access in the native platforms
class Channels {
  const Channels(String value) : _value = value;

  final String _value;

  String get value => _value;

  /// The [value] of principal channel
  static const principal = Channels('have_app');

  /// The [value] of the method to
  /// get application by package name
  static const getApplication = Channels('getApplication');
}

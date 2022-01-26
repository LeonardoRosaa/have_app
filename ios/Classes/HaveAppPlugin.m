#import "HaveAppPlugin.h"
#if __has_include(<have_app/have_app-Swift.h>)
#import <have_app/have_app-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "have_app-Swift.h"
#endif

@implementation HaveAppPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHaveAppPlugin registerWithRegistrar:registrar];
}
@end

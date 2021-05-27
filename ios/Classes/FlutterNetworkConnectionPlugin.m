#import "FlutterNetworkConnectionPlugin.h"
#if __has_include(<flutter_network_connection/flutter_network_connection-Swift.h>)
#import <flutter_network_connection/flutter_network_connection-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_network_connection-Swift.h"
#endif

@implementation FlutterNetworkConnectionPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterNetworkConnectionPlugin registerWithRegistrar:registrar];
}
@end

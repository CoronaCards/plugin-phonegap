#import <Cordova/CDVPlugin.h>

@interface CDVCoronaViewBridge : CDVPlugin {}

- (void)run:(CDVInvokedUrlCommand*)command;
- (void)suspend:(CDVInvokedUrlCommand*)command;
- (void)resume:(CDVInvokedUrlCommand*)command;
- (void)close:(CDVInvokedUrlCommand*)command;
- (void)sendEvent:(CDVInvokedUrlCommand*)command;

@end

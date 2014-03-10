#import "CDVCoronaViewBridge.h"

#import <Foundation/Foundation.h>
#import "CoronaKit/CoronaKit.h"
#import "ARCMacros.h"

static NSMutableDictionary *sActiveViews = [NSMutableDictionary new];

@interface CDVCoronaViewBridge()
@property (nonatomic, strong) NSMutableDictionary *coronaControllers;
@end

@implementation CDVCoronaViewBridge

- (id)initWithWebView:(UIWebView*)theWebView
{
	self = [super initWithWebView:theWebView];
	if ( nil != self )
	{
		_coronaControllers = [[NSMutableDictionary alloc] init];
	}
	return self;
}

- (void)dealloc
{
	[_coronaControllers RELEASE];

	SUPER_DEALLOC();
}

static CGRect
InitFrame( NSDictionary *options, CGRect defaultFrame )
{
	CGRect frame = defaultFrame;

	NSNumber *x = [options valueForKey:@"x"];
	if ( x )
	{
		frame.origin.x = [x floatValue];
	}
	NSNumber *y = [options valueForKey:@"y"];
	if ( y )
	{
		frame.origin.y = [y floatValue];
	}
	NSNumber *w = [options valueForKey:@"width"];
	if ( w )
	{
		frame.size.width = [w floatValue];
	}
	NSNumber *h = [options valueForKey:@"height"];
	if ( h )
	{
		frame.size.height = [h floatValue];
	}

	return frame;
}

- (void)newView:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [[[CoronaViewController alloc] init] AUTORELEASE];

	// UUID
//	NSString *key = [[NSUUID UUID] UUIDString];
	NSString *key = [self keyForCommand:command];
	[_coronaControllers setObject:coronaController forKey:key];

	UIViewController *parentController = self.viewController;
	[parentController addChildViewController:coronaController];

	CoronaView *coronaView = (CoronaView *)coronaController.view;

	// Options
	NSDictionary *options = [command argumentAtIndex:1];
	CGRect frame = InitFrame( options, parentController.view.frame );
	coronaView.frame = frame;

	[parentController.view addSubview:coronaView];

	CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:key];
	[self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (NSString *)keyForCommand:(CDVInvokedUrlCommand *)command
{
	return [command argumentAtIndex:0];
}

- (CoronaViewController *)controllerForCommand:(CDVInvokedUrlCommand *)command
{
	NSString *key = [self keyForCommand:command];
	return [_coronaControllers valueForKey:key];
}

- (void)run:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [self controllerForCommand:command];

	if ( coronaController )
	{
		NSString *path = [command argumentAtIndex:1];
		path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:path];

		NSDictionary *params = [command argumentAtIndex:2];

		CoronaView *coronaView = (CoronaView *)coronaController.view;
		[coronaView runWithPath:path parameters:params];
	}
}

- (void)suspend:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [self controllerForCommand:command];

	if ( coronaController )
	{
		CoronaView *coronaView = (CoronaView *)coronaController.view;
		[coronaView suspend];
	}
}

- (void)resume:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [self controllerForCommand:command];

	if ( coronaController )
	{
		CoronaView *coronaView = (CoronaView *)coronaController.view;
		[coronaView resume];
	}
}

- (void)close:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [self controllerForCommand:command];

	if ( coronaController )
	{
		coronaController.paused = YES;
		[coronaController.view removeFromSuperview];
		[coronaController removeFromParentViewController];

		NSString *key = [self keyForCommand:command];
		[_coronaControllers removeObjectForKey:key];
	}
}

- (void)sendEvent:(CDVInvokedUrlCommand*)command
{
	CoronaViewController *coronaController = [self controllerForCommand:command];

	if ( coronaController )
	{
		NSDictionary *event = [command argumentAtIndex:0];

		CoronaView *coronaView = (CoronaView *)coronaController.view;
		[coronaView sendEvent:event];
	}
}

@end

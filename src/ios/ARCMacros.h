//
// ARCMacros.h
// 

#if !defined(__clang__) || __clang_major__ < 3
	#ifndef __bridge
		#define __bridge
	#endif

	#ifndef __bridge_retain
		#define __bridge_retain
	#endif

	#ifndef __bridge_retained
		#define __bridge_retained
	#endif

	#ifndef __autoreleasing
		#define __autoreleasing
	#endif

	#ifndef __strong
		#define __strong
	#endif

	#ifndef __unsafe_unretained
		#define __unsafe_unretained
	#endif

	#ifndef __weak
		#define __weak
	#endif
#endif

#if __has_feature(objc_arc)
	#define RETAIN_PROPERTY strong
	#define RETAIN self
	#define AUTORELEASE self
	#define RELEASE self
	#define DEALLOC self
	#define SUPER_DEALLOC()
	#define AUTORELEASE_POOL_START() @autoreleasepool {
	#define AUTORELEASE_POOL_END() }
#else
	#define RETAIN_PROPERTY retain
	#define RETAIN retain
	#define AUTORELEASE autorelease
	#define RELEASE release
	#define DEALLOC dealloc
	#define SUPER_DEALLOC() ([super dealloc])
	#define AUTORELEASE_POOL_START() NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
	#define AUTORELEASE_POOL_END() [pool release];
#endif

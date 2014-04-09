<!---
# The MIT License (MIT)
# 
# Copyright (c) 2014 Corona Labs
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of
# this software and associated documentation files (the "Software"), to deal in
# the Software without restriction, including without limitation the rights to
# use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies # of
# the Software, and to permit persons to whom the Software is furnished to do so,
# subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, # FITNESS
# FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
# COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
# IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
# CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
-->

# com.coronalabs.coronacards.cordova

This plugin provides the ability to create CoronaView instances from JavaScript.

## Installation

    phonegap local plugin add https://github.com/CoronaCards/plugin-phonegap

## Project Configuration

### Android

On Android, you will need to add the CoronaCards libraries to your Android project.

More specifically, you will need to copy the .jar files into the [Android Project Directory]/libs folder and the .so files into the [Android Project Directory]/libs/armeabi-v7a folder.

You will also need to put your Corona project into the [Android Project Directory]/assets folder.

See [CoronaCards Project Integration (Android)](http://docs.coronalabs.com/daily/coronacards/android/project.html) for more information.

### iOS/Xcode

On iOS, you will need to add `CoronaKit.framework` to your Xcode project.

In addition, you will need to add required frameworks. 

See [CoronaCards Setup Guide (iOS)](http://docs.coronalabs.com/daily/coronacards/ios/setup.html) for more information.

### Gotchas

There is a quirk with the command line tool worth noting, relating to how the JavaScript files for the plugin get included.

The easiest path is to let the command line tool add the JavaScript files to your project for you. However, you must add the platform __before__ you add the plugin. Otherwise, you'll have to manually add the JavaScript files.

You can add a platform by calling `phonegap run ios` which attempts to add, build, and run your project. 

For a new project, the command-line sequence would look like:

    phonegap create HelloWorld
    cd HelloWorld
    phonegap run ios
    phonegap local plugin add https://github.com/CoronaCards/plugin-phonegap

You can consult the [Phonegap docs](http://docs.phonegap.com/en/3.4.0/index.html) for more information.


## Supported Platforms

- Android

- iOS

## Media

    var view = new CoronaView( x, y, w, h );

### Parameters

- __x__: 

- __y__: 

- __w__: The width

- __h__: The height


### Constants

### Methods

- `view.run`: Loads the `main.lua` in the specific folder and executes it.

- `view.suspend`: Suspends the CoronaView instance.

- `view.resume`: Resumes the CoronaView instance.

- `view.close`: Shuts down the CoronaView instance.

- `view.sendEvent`: Sends an event from JavaScript to Lua.

## view.run

Loads the `main.lua` in the specific folder and executes it.

    view.run(path, params);

### Parameters

- __path__: A relative path inside the .app bundle to a folder containing the Corona resource files (e.g. `main.lua`).

- __params__: Currently not supported on Android.

### Quick Example

    var view = new CoronaView( 0, 0, 100, 100 );


## view.suspend

    view.suspend();

### Quick Example

    var view = new CoronaView( 0, 0, 100, 100 );


## view.resume

    view.resume();

### Quick Example

    var view = new CoronaView( 0, 0, 100, 100 );


## view.close

    view.close();

### Quick Example

    var view = new CoronaView( 0, 0, 100, 100 );


## view.sendEvent

    view.sendEvent(eventParams);

### Parameters

- __eventParams__: An associative array (key-value pairs).

### Quick Example

    var view = new CoronaView( 0, 0, 100, 100 );



### Quick Example

    // Play audio
    //
    function playAudio(url) {
        // Play the audio file at url
        var my_media = new Media(url,
            // success callback
            function () {
                console.log("playAudio():Audio Success");
            },
            // error callback
            function (err) {
                console.log("playAudio():Audio Error: " + err);
            }
        );
        // Play audio
        my_media.play();
    }


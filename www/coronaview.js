var exec = require('cordova/exec');
var utils = require('cordova/utils');

function CoronaView(x,y,w,h) {
console.log( "> CoronaView ");

	var cb = function( key ) {
console.log( "> cb" );
		view._key = key;
console.log( "< cb: ", this._key );
	};


	var options = {};
	options.x = x;
	options.y = y;
	options.width = w;
	options.height = h;

console.log( ">> CoronaView options: ", options.x, options.y, options.width, options.height );

	exec(cb, null, "CoronaView", "newView", [options]);

console.log( "< CoronaView");
	return view;
};
//() {
//console.log( "> CoronaView 2");
//	// this.channels = {
//	//      'loadstart': channel.create('loadstart'),
//	//      'loadstop' : channel.create('loadstop'),
//	//      'loaderror' : channel.create('loaderror'),
//	//      'exit' : channel.create('exit')
//	// };
//}

CoronaView.prototype = {
	run: function(path, params) {
		exec(null, null, "CoronaView", "run", [this._key, path, params]);
	},
	suspend: function() {
		exec(null, null, "CoronaView", "suspend", [this._key]);
	},
	resume: function() {
		exec(null, null, "CoronaView", "resume", [this._key]);
	},
	close: function() {
		exec(null, null, "CoronaView", "close", [this._key]);
	},
	sendEvent: function(eventParams) {
		exec(null, null, "CoronaView", "sendEvent", [this._key, eventParams]);
	},
};

module.exports = CoronaView

/*
var CoronaView = function( x, y, w, h ) {
console.log( "> CoronaView() ");
	var view = new CoronaView();
	var cb = function( viewKey ) {
		view._viewKey = viewKey;
	};

	var options = new Object();
	options.x = x;
	options.y = y;
	options.width = w;
	options.height = h;

	exec(cb, null, "CoronaView", "newView", [options]);

console.log( "< CoronaView() ");
	return view;
}

CoronaView.prototype = {
	run: function(path, params) {
		exec(null, null, "CoronaView", "run", [this._viewKey, path, params]);
	},
	suspend: function() {
		exec(null, null, "CoronaView", "suspend", [this._viewKey]);
	},
	resume: function() {
		exec(null, null, "CoronaView", "resume", [this._viewKey]);
	},
	close: function() {
		exec(null, null, "CoronaView", "close", [this._viewKey]);
	},
	sendEvent: function(eventParams) {
		exec(null, null, "CoronaView", "sendEvent", [this._viewKey, eventParams]);
	},
};

module.exports = CoronaView;
*/
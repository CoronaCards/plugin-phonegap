
var exec = require('cordova/exec');
var utils = require('cordova/utils');

function CoronaView(x,y,w,h) {
console.log( "> CoronaView ---- ");

	this._key = utils.createUUID();
console.log( " uuid: ", this._key );

	var options = {};
	options.x = x;
	options.y = y;
	options.width = w;
	options.height = h;

console.log( ">> CoronaView options: ", options.x, options.y, options.width, options.height );

	exec(null, null, "CoronaView", "newView", [this._key, options]);

console.log( "< CoronaView");
};

CoronaView.prototype.run = function(path, params) {
	exec(null, null, "CoronaView", "run", [this._key, path, params]);
};

CoronaView.prototype.suspend = function() {
	exec(null, null, "CoronaView", "suspend", [this._key]);
};

CoronaView.prototype.resume = function() {
	exec(null, null, "CoronaView", "resume", [this._key]);
};

CoronaView.prototype.close = function() {
	exec(null, null, "CoronaView", "close", [this._key]);
};

CoronaView.prototype.sendEvent = function(eventParams) {
	exec(null, null, "CoronaView", "sendEvent", [this._key, eventParams]);
};

module.exports = CoronaView


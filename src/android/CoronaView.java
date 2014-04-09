package com.coronalabs.coronacards.cordova;

import org.json.JSONException;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.view.ViewGroup;
import android.view.View;
import android.view.MotionEvent;
import java.util.Hashtable;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class CoronaView extends CordovaPlugin{
	Hashtable<String, com.ansca.corona.CoronaView> mViewsHashMap = new Hashtable<String, com.ansca.corona.CoronaView>();
	boolean mInsertedRelativeLayout = false;
	RelativeLayout mRelativeLayout;

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if (action.equals("newView")) {
			insertRelativeLayout();
			String uid = args.getString(0);
			int x = args.optJSONObject(1).getInt("x");
			int y = args.optJSONObject(1).getInt("y");
			int height = args.optJSONObject(1).getInt("height");
			int width = args.optJSONObject(1).getInt("width");
			newView(uid, x, y, height, width);
		} else if (action.equals("run")) {
			String uid = args.getString(0);
			String path = args.getString(1);
			run(uid, path);
		} else if (action.equals("suspend")) {
			String uid = args.getString(0);
			suspend(uid);
		} else if (action.equals("resume")) {
			String uid = args.getString(0);
			resume(uid);
		} else if (action.equals("close")) {
			String uid = args.getString(0);
			close(uid);
		} else if (action.equals("sendEvent")) {
			String uid = args.getString(0);
			JSONObject value = args.optJSONObject(1);
			sendEvent(uid, value);
		}
		
		return true;
	}

	// The relative layout allows us to position the view in api level <11
	private void insertRelativeLayout() {
		if (mInsertedRelativeLayout) {
			return;
		}

		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				FrameLayout rootView = (FrameLayout)cordova.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
				
				RelativeLayout rl = new RelativeLayout(cordova.getActivity());

				// Pass the touch event to the parent since the Relativelayout matches parent.
				rl.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent e) {
						return false;
					}
				});

				rootView.addView(rl, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				rootView.bringChildToFront(rl);
				
				mRelativeLayout = rl;
				mInsertedRelativeLayout = true;
			}
		});	
	}

	private void newView(final String uid, final int x, final int y, final int height, final int width) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = new com.ansca.corona.CoronaView(cordova.getActivity());
				mViewsHashMap.put(uid, cv);
				
				RelativeLayout rl = mRelativeLayout;

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
				params.leftMargin = x;
				params.topMargin = y;

				rl.addView(cv, params);
				rl.bringChildToFront(cv);
			}
		});
	}

	private void run(final String uid, final String path) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = mViewsHashMap.get(uid);
				if (cv == null) {
					return;
				}
				cv.init(path);
				cv.resume();
			}
		});
	}

	private void suspend(final String uid) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = mViewsHashMap.get(uid);
				if (cv == null) {
					return;
				}
				cv.pause();
			}
		});
	}

	private void resume(final String uid) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = mViewsHashMap.get(uid);
				if (cv == null) {
					return;
				}
				cv.resume();
			}
		});
	}

	private void close(final String uid) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = mViewsHashMap.get(uid);
				if (cv == null) {
					return;
				}
				cv.destroy();
			}
		});
	}

	private void sendEvent(final String uid, final JSONObject object) {
		cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				com.ansca.corona.CoronaView cv = mViewsHashMap.get(uid);
				if (cv == null) {
					return;
				}
				try {
					Hashtable<Object, Object> table = toTable(object);
					cv.sendEvent(table);
				} catch (JSONException e) {

				}
			}
		});
	}

	public static Hashtable<Object, Object> toTable(JSONObject object) throws JSONException {
		Hashtable<Object, Object> map = new Hashtable();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
				String key = (String) keys.next();
				map.put(key, fromJson(object.get(key)));
		}
		return map;
	}
 
	private static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
				return null;
		} else if (json instanceof JSONObject) {
				return toTable((JSONObject) json);
		} else {
				return json;
		}
	}
}

package com.mlemainque.cordova.plugins;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import static java.lang.String.valueOf;
import java.util.HashMap;



public class CameraRoll extends CordovaPlugin {
	public static final String ACTION_GET_PHOTOS	= "getPhotos";
	public static final String ACTION_SAVE_PHOTO	= "saveToCameraRoll";

	public static final String TAG = "CameraRollPlugin";
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		Log.d(TAG, "execute " + action);

		try {

			if (ACTION_GET_PHOTOS.equals(action)) {
				getPhotos(args, callbackContext);
			} else if (ACTION_SAVE_PHOTO.equals(action)) {
				callbackContext.error("saveToCameraRoll() action is not (yet) supported on Android.");
			} else  {
				return false;
			}
			return true;

		} catch(Exception e) {

			Log.w(TAG, "Exception: " + e.getMessage());
			return false;

		}

	}

	private void getPhotos(JSONArray args, final CallbackContext callbackContext) throws JSONException {
		final CordovaWebView webView = this.webView;

		cordova.getThreadPool().execute(new Runnable() {
			@Override
			public void run() {

				// We discover every photo stored on the phone and ask the _data field, which is the full
				// path of the file
				Cursor cursor = MediaStore.Images.Media.query(
					webView.getContext().getContentResolver(),
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					new String[] {
						MediaStore.Images.Media.DATA
					}
				);

				while (cursor.moveToNext()) {
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						if (cursor.getColumnName(i).equals("_data")) {

							Log.d(TAG, "Discovering "+cursor.getString(i));
							
							// The callbackContext.success() method can only be called once
							PluginResult result = new PluginResult(PluginResult.Status.OK, cursor.getString(i));
							result.setKeepCallback(true);
							callbackContext.sendPluginResult(result);

						}
					}
				}

			}
		});
	}

}

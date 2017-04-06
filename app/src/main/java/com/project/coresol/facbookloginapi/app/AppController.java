package com.project.coresol.facbookloginapi.app;

import android.app.Application;

import com.facebook.FacebookSdk;

public class
		AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();


	private static AppController mInstance;
	@Override
	public void onCreate() {
		super.onCreate();

		FacebookSdk.sdkInitialize(getApplicationContext());
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}
}
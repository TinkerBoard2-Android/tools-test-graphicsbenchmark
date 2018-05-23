/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.game.qualification.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.android.game.qualification.ApkInfo;
import com.android.game.qualification.ApkListXmlParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

@RunWith(Parameterized.class)
public class GameQualificationTest {
    public static final String INTENT_ACTION = "com.android.game.qualification.START";

    private static final String TAG = "GameQualificationTest";

    @Parameters(name = "{0}")
    public static Iterable<Object[]> data()
            throws ParserConfigurationException, SAXException, IOException {
        List<Object[]> params = new ArrayList<>();
        ApkListXmlParser parser = new ApkListXmlParser();
        List<ApkInfo> apks = parser.parse(new File(ApkInfo.APK_LIST_LOCATION));
        for (ApkInfo apk : apks) {
            params.add(new Object[] { apk.getName(), apk });
        }

        return params;
    }

    @Parameter(value = 0)
    public String mApkName;

    @Parameter(value = 1)
    public ApkInfo mApk;

    private Handler mHandler;
    private MetricsReporter mReport = new MetricsReporter();
    private boolean mGotIntent = false;

    @Test public void run() throws IntentFilter.MalformedMimeTypeException, IOException {
        startApp(mApk);
    }

    private void startApp(ApkInfo apk) throws IntentFilter.MalformedMimeTypeException, IOException {
        Looper.prepare();
        mHandler = new Handler();

        registerReceiver();
        mReport.begin(apk.getName());
        Log.d(TAG, "Launching " + apk.getPackageName());

        Intent intent =
                InstrumentationRegistry.getContext().getPackageManager()
                    .getLaunchIntentForPackage(apk.getPackageName());

        for (ApkInfo.Argument argument : mApk.getArgs()) {
            switch(argument.getType()) {
                case STRING:
                    intent.putExtra(argument.getKey(), argument.getValue());
                    break;
                case BOOLEAN: {
                    boolean value = Boolean.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
                case BYTE: {
                    byte value = Byte.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
                case INT: {
                    int value = Integer.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
                case LONG: {
                    long value = Long.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
                case FLOAT: {
                    float value = Float.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
                case DOUBLE: {
                    double value = Double.valueOf(argument.getValue());
                    intent.putExtra(argument.getKey(), value);
                    break;
                }
            }
        }

        InstrumentationRegistry.getContext().startActivity(intent);
        mHandler.postDelayed(() -> {
            if (!mGotIntent) {
                mHandler.getLooper().quit();
            }
        }, mApk.getRunTime());
        Looper.loop();
        mReport.end();
    }

    private void registerReceiver() throws IntentFilter.MalformedMimeTypeException {
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long timestamp = intent.getLongExtra("timestamp", 0);
                Log.d(TAG, "Received intent at " + timestamp);
                mReport.startLoop(timestamp);
                if (!mGotIntent) {
                    mHandler.postDelayed(() -> mHandler.getLooper().quit(), mApk.getRunTime());
                    mGotIntent = true;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(INTENT_ACTION, "text/plain");
        InstrumentationRegistry.getContext().registerReceiver(br, intentFilter);
    }
}
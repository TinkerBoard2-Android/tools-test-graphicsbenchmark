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

package com.google.android.gfx.benchmark.test;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.android.gfx.benchmark.ApkInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class GraphicsBenchmarkTest {
    private static final String TAG = "GraphicsBenchmarkTest";

    @Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
        List<Object[]> params = new ArrayList<>();
        for (ApkInfo apk : ApkInfo.values()) {
            params.add(new Object[] { apk.name(), apk });
        }
        return params;
    }

    @Parameter(value = 0)
    public String apkName;

    @Parameter(value = 1)
    public ApkInfo apk;

    @Test public void run() throws IOException, InterruptedException {
        startApp(apk);
    }

    private void startApp(ApkInfo app) throws IOException, InterruptedException {
        Log.d(TAG, "Launching " + app.getPackageName());

        // TODO: Need to support passing arguments to intents.
        Intent intent =
                InstrumentationRegistry.getContext().getPackageManager()
                    .getLaunchIntentForPackage(app.getPackageName());
        InstrumentationRegistry.getContext().startActivity(intent);
        Thread.sleep(10000);
    }
}

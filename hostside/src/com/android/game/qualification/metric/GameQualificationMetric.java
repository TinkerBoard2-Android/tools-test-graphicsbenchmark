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

package com.android.game.qualification.metric;

class GameQualificationMetric {
    private long mActualPresentTime;
    private long mFrameReadyTime;

    public GameQualificationMetric() {
        this.mActualPresentTime = 0;
        this.mFrameReadyTime = 0;
    }

    public GameQualificationMetric(long actualPresentTime, long frameReadyTime) {
        this.mActualPresentTime = actualPresentTime;
        this.mFrameReadyTime = frameReadyTime;
    }

    public long getActualPresentTime() {
        return mActualPresentTime;
    }

    public void setActualPresentTime(long actualPresentTime) {
        this.mActualPresentTime = actualPresentTime;
    }

    public long getFrameReadyTime() {
        return mFrameReadyTime;
    }

    public void setFrameReadyTime(long frameReadyTime) {
        this.mFrameReadyTime = frameReadyTime;
    }
}
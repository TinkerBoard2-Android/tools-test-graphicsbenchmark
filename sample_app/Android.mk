# Copyright 2018, The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SDK_VERSION := 26  # Oreo
LOCAL_MODULE := libsample
LOCAL_MODULE_TAGS := tests
LOCAL_SRC_FILES := src/cpp/sample_activity.cpp
LOCAL_SHARED_LIBRARIES := libagbench

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_SDK_VERSION := 26  # Oreo
LOCAL_PACKAGE_NAME := GraphicsBenchmarkSampleApp
LOCAL_MODULE_TAGS := tests
LOCAL_JNI_SHARED_LIBRARIES := libagbench libsample
LOCAL_COMPATIBILITY_SUITE := device-tests
LOCAL_SRC_FILES := $(call all-java-files-under, src/java)

include $(BUILD_PACKAGE)

include $(call all-makefiles-under,$(LOCAL_PATH))

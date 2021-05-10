
Android WeatherHelper Prototype
===================================

This prototype shows how to implement a humidity and temperature monitor using Bluetooth comunication between two Android device and Arduino, using
all the fundamental Bluetooth API capabilities.

Introduction
------------

This prototype should be run on Android devices and Arduino Uno, to establish working app. Select "Made discoverable" in Arduino, I'm used DHT22 Sensor to get humidity and temperarure measured, and click
on the Bluetooth icon on the devide, to find the device and establish the connection.

A Prototype climate management system(CMS) controlled by mobile device through an app. 
Developed  by integrating Android Studio and Arduino platform, 
o CMS consist to a system that is not just weather monitor(humidity and temperature) 
but specially to direct the user to take preventive measures 
by describing that to the user in case the climatic variable changes have been a nocive level to the user.

Pre-requisites
--------------

- Android SDK 27
- Android Build Tools v27.0.2
- Android Support Repository

Screenshots
-------------

<img src="screenshots/01-circuit.png" height="400" alt="Screenshot"/> <img src="screenshots/02-launch.png" height="400" alt="Screenshot"/> <img src="screenshots/03-infos.png" height="400" alt="Screenshot"/> 


License
-------

Copyright 2017 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

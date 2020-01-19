# Detective Droid 
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-detective--droid-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/898) [![Maintainability](https://api.codeclimate.com/v1/badges/4dae723e17c6fadb28a9/maintainability)](https://codeclimate.com/github/michaelcarrano/detective-droid/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/4dae723e17c6fadb28a9/test_coverage)](https://codeclimate.com/github/michaelcarrano/detective-droid/test_coverage) [![CircleCI](https://circleci.com/gh/michaelcarrano/detective-droid/tree/develop.svg?style=svg)](https://circleci.com/gh/michaelcarrano/detective-droid/tree/develop)

Detective Droid is here to help you investigate what libraries are being used inside applications that are installed on your device.

This makes it really easy to see what other developers/companies are using to develop their applications.

Detective Droid requires no permissions and works on Android API 21 (Android 5.0 Lollipop) and newer.


## Limitations
Detective Droid is unable to detect libraries that are obfuscated with Proguard/R8. Additionally, unable to detect libraries that are dynamically created. For instance, [LeadBolt](http://leadbolt.com) creates their SDK on the fly for each developer which means a unique Classpath.


## Credits
Daniel Bjorge - Detective Droid is inspired by his [AirPush Detector](https://github.com/dbjorge/AirPush-Detector) project.

Josef Pfleger - The logic of detecting libraries in other applications comes from his article, [APK piracy](http://www-jo.se/f.pfleger/apk-piracy).


## License


    Copyright 2013 Michael Carrano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
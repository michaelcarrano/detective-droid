# Detective Droid [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-detective--droid-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/898)

Detective Droid is here to help you investigate what libraries are being used inside applications that are installed on your device.

This makes it really easy to see what other developers/companies are using to develop their applications.

Detective Droid requires no permissions and works on Android API 21 (Android 5.0 Lollipop) and newer.

## Limitations
Detective Droid is unable to detect libraries that are obfuscated with Proguard/R8. Additionally, unable to detect libraries that are dynamically created. For instance, [LeadBolt](http://leadbolt.com) creates their SDK on the fly for each developer which means a unique Classpath.

## Contributing
Contributions are welcome, especially for new libraries you would like to see Detective Droid investigate.

**Library Contribution:**

Please create a pull request per library.

A library is a JSON object that follows this format:

	{
    	"name": "Name of library",
    	"path": "full.classpath.of.library",
    	"source": "a link to where we can reference the library"
	},

See current [libaries](https://github.com/michaelcarrano/detective-droid/tree/master/libraries) Detective Droid investigates.

All the JSON files are combined into a single libaries.json file via a Gradle task when building the Detective Droid application.

## Versions
The versionName follows the format of **major.minor.patch.library**

For example, new functionality would result in increasing the **minor** number and if new libraries are added then we increase the **library** number.

2.0.0
- Complete re-write of detective droid app with modern approach to Android development. See branch [release/1.x](https://github.com/michaelcarrano/detective-droid/tree/release/1.x) for version prior to this release.

1.3.1.1

- Add intent to launch browser to view library source Url.
- Update launcher icon and Play Store assets.

1.2.0.1

- Add ProgressDialog to better inform users what is going on.
- Add SettingsActivity to allow users to choose whether the system apps should be scanned. (By default they are not)
- Add new libraries to investigate.


1.0

- Initial release

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
# Detective Droid

Detective Droid is here to help you investigate what libraries are being used inside applications that are installed on your device.

This makes it really easy to see what other developers/companies are using to develop their applications.

Detective Droid requires no permissions and works on Android version 2.2 and beyond.

## Limitations
Detective Droid is unable to detect libraries that are dynamically created. For instance, [LeadBolt](http://leadbolt.com) creates their SDK on the fly for each developer which means a unique Classpath.

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
gradle-xkcd-plugin
===
[![Build Status](https://travis-ci.org/gregwhitaker/gradle-xkcd-plugin.svg?branch=master)](https://travis-ci.org/gregwhitaker/gradle-xkcd-plugin)

Gradle plugin for downloading web comics from [xkcd.com](http://xkcd.com).

##Usage
Please see the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.github.gregwhitaker.xkcd) for instructions on including this plugin in your project.

The plugin provides the custom task, `DownloadComicTask`, that you can configure as follows:

###Downloading the Latest Comic

```$groovy
task('myComic', type: DownloadComicTask) {
    destination = file('${buildDir}/images/comic.png')
    downloadLatest()
}   
```

###Downloading a Random Comic

```$groovy
task('myComic', type: DownloadComicTask) {
    destination = file('${buildDir}/images/comic.png')
    downloadRandom()
}   
```

###Downloading a Specific Comic

```$groovy
task('myComic', type: DownloadComicTask) {
    destination = file('${buildDir}/images/comic.png')
    comic = 1629
}   
```

##Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/gregwhitaker/gradle-xkcd-plugin/issues).

##License
Copyright 2017 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
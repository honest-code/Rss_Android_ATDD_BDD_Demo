# Rss Android ATDD BDD Demo
Rss Android App BDD / ATDD Demo for HonestCode

## Objetives
This is a simple Android RSS App with some Acceptance Test in Ghekin connected with the service pro.HonestCode.io.

The App is intended to show a functional sample of BDD and ATDD.


## System requisites

* Android Studio
* Android SDK
* Android Emulator properly configured
* Honest Code project configured

## Honest Code
Build a product on Honest Code, with features and scenarios that fit the .features

![](./publish/product.png)


![](./publish/feature.png)


![](./publish/scenario.png)

## Use

Import this gradle project in Android Studio

### Run tests

Clean, and build project
```
sh ./gradlew clean assembleDevDebug assembleDevDebugAndroidTest
```

Install app and test app

```
adb install -r ./app/build/outputs/apk/app-dev-debug.apk
```

```
adb install -r ./app/build/outputs/apk/app-dev-debug-androidTest.apk
```

Run instrumentation

```
adb shell am instrument -w -r -e package com.igz.rssreader -e debug false com.igz.rssreader.test/com.igz.rssreader.support.runner.CucumberTestRunner
```

Verify execution on emulator

<table>
  <tr>
    <td><img src="./publish/emulator-1.png" alt="Drawing" width="300px" /></td>
    <td><img src="./publish/emulator-2.png" alt="Drawing" width="300px" /></td>
  </tr>
</table>


### Upload report
```
adb shell "run-as com.igz.rssreader cat /data/data/com.igz.rssreader/cucumber-reports/cucumber.json" > rssreader-report.json
```

```
curl -XPOST -—data-binary @rssreader-report.json https://pro.honestcode.io/api/hooks/tr/<<HONEST_CODE_KEY>> -v -s
```


![](./publish/after-report.png)
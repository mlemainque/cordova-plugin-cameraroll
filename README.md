# Cordova Plugin Camera Roll #

## What is this plugin for?

This cordova plugin allows to browse the phone's gallery and discovers every photos and videos.

This is a fork from [this](https://github.com/driftyco/cordova-camera-roll) repository which contains the iOS implementation. The Android implementation is based on [this](https://github.com/athieriot/cordova-media-discoverer-plugin) other repository.

## Installation

With cordova:
```
$ cordova plugin add https://github.com/mlemainque/cordova-plugin-cameraroll
```

## CameraRoll.getPhotos

```
window.cordova.plugins.CameraRoll.getPhotos(callback);
```

### Parameters

* __callback__: The callback that is called for each file found

### Exemple

```
window.cordova.plugins.CameraRoll.getPhotos(function(URI) {
  // Do something ...
}, function(err) {
  console.log(err);
});
```

### Supported Platforms

* Android
* iOS

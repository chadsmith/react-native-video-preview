## react-native-video-preview

A `<VideoPreview>` component for react-native on Android.

iOS support is not included because [react-native-video](https://github.com/react-native-community/react-native-video) already shows a preview when loading a video that is paused.

### Add it to your project

Run `npm i -S git://github.com/chadsmith/react-native-video-preview.git`

Then run `react-native link react-native-video-preview`

Inside your code, import the component by adding:

```javascript
import VideoPreview from 'react-native-video-preview';
```

If necessary, make the following additions to the given files manually:

**android/settings.gradle**

```
include ':react-native-video-preview'
project(':react-native-video-preview').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-video-preview/android')
```

**android/app/build.gradle**

```
dependencies {
   ...
   compile project(':react-native-video-preview')
}
```

**MainApplication.java**

On top, import:

```java
import com.github.chadsmith.RCTVideoPreview;
```

Under `.addPackage(new MainReactPackage())`:

```java
.addPackage(new VideoPreviewPackage())
```

## Usage

```javascript
<VideoPreview
  source={{ uri: 'http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4' }} // Can be a URL or a local file.
  style={styles.preview}
  resizeMode="contain" />

// Later on in your styles..
var styles = StyleSheet.create({
  preview: {
    height: 180,
    width: 320,
  },
});
```

---

**MIT Licensed**

# react-native-thub-rn-face-detection

React Native package for face detection

## Installation

```sh
npm install react-native-thub-rn-face-detection
```

## Usage

```js
import ThubRnFaceDetection from 'react-native-thub-rn-face-detection';

ThubRnFaceDetection.faceDetection(
  imagePath,
  (data) => {
    console.log(data);
  },
  (errorMessage) => {
    console.log(errorMessage);
  }
);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

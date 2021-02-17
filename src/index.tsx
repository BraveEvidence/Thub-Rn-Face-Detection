import { NativeModules } from 'react-native';

type ThubRnFaceDetectionType = {
  multiply(a: number, b: number): Promise<number>;
};

const { ThubRnFaceDetection } = NativeModules;

export default ThubRnFaceDetection as ThubRnFaceDetectionType;

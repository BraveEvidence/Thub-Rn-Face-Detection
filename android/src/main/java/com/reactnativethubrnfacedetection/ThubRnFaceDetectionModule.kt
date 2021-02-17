package com.reactnativethubrnfacedetection

import android.net.Uri
import com.facebook.react.bridge.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import java.io.File
import java.io.IOException

class ThubRnFaceDetectionModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var faceDetectionSuccessCallback: Callback? = null
  private var faceDetectionFailureCallback: Callback? = null

  private val highAccuracyOpts = FaceDetectorOptions.Builder()
    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
    .build()

  // Real-time contour detection
  private val realTimeOpts = FaceDetectorOptions.Builder()
    .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
    .build()

  val detector = FaceDetection.getClient(highAccuracyOpts)

  override fun getName(): String {
    return "ThubRnFaceDetection"
  }

  @ReactMethod
  fun faceDetection(uri: String, successCallback: Callback, failureCallback: Callback) {
    faceDetectionSuccessCallback = successCallback
    faceDetectionFailureCallback = failureCallback

    val image: InputImage
    try {
      val file = File(uri)
      image = InputImage.fromFilePath(reactContext, Uri.parse(file.toString()))
      val result = detector.process(image)
        .addOnSuccessListener { faces ->
          if (faces.size > 0) {
            val face = faces[0]

            var leftEarPos = ""
            var rightEarPos = ""
            var leftCheekPos = ""
            var leftEyePos = ""
            var mouthBottomPos = ""
            var mouthLeftPos = ""
            var mouthRightPos = ""
            var rightCheekPos = ""
            var smileProb = ""
            var rightEyeOpenProb = ""
            var leftEyeOpenProb = ""

            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
            leftEar?.let {
              leftEarPos = leftEar.position.toString()
            }
            val rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR)
            rightEar?.let {
              rightEarPos = rightEar.position.toString()
            }
            val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)
            leftCheek.let {
              leftCheekPos = leftCheek?.position.toString()
            }
            val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
            leftEye.let {
              leftEyePos = leftEye?.position.toString()
            }
            val mouthBottom = face.getLandmark(FaceLandmark.MOUTH_BOTTOM)
            mouthBottom.let {
              mouthBottomPos = mouthBottom?.position.toString()
            }
            val mouthLeft = face.getLandmark(FaceLandmark.MOUTH_LEFT)
            mouthLeft.let {
              mouthLeftPos = mouthLeft?.position.toString()
            }
            val mouthRight = face.getLandmark(FaceLandmark.MOUTH_RIGHT)
            mouthRight.let {
              mouthRightPos = mouthRight?.position.toString()
            }
            val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)
            rightCheek.let {
              rightCheekPos = rightCheek?.position.toString()
            }

            // If classification was enabled:
            if (face.smilingProbability != null) {
              smileProb = face?.smilingProbability.toString()
            }
            if (face.rightEyeOpenProbability != null) {
              rightEyeOpenProb = face?.rightEyeOpenProbability.toString()
            }
            if (face.leftEyeOpenProbability != null) {
              leftEyeOpenProb = face?.leftEyeOpenProbability.toString()
            }

            faceDetectionFailureCallback?.invoke(mapOf(
              "leftEarPos" to leftEarPos,
              "rightEarPos" to rightEarPos,
              "leftCheekPos" to leftCheekPos,
              "leftEyePos" to leftEyePos,
              "mouthBottomPos" to mouthBottomPos,
              "mouthLeftPos" to mouthLeftPos,
              "mouthRightPos" to mouthRightPos,
              "rightCheekPos" to rightCheekPos,
              "smileProb" to smileProb,
              "rightEyeOpenProb" to rightEyeOpenProb,
              "leftEyeOpenProb" to leftEyeOpenProb,
            ))


          } else {
            faceDetectionFailureCallback?.invoke("No faces detected")
          }
        }
        .addOnFailureListener { e ->
          faceDetectionFailureCallback?.invoke(e.message.toString())
        }
    } catch (e: IOException) {
      e.printStackTrace()
      faceDetectionFailureCallback?.invoke(e.message.toString())
    }
  }


}

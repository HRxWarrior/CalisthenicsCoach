package com.caliscoach.app.pose

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import com.caliscoach.app.data.model.FormFeedback
import kotlin.math.abs
import kotlin.math.atan2

object PoseAnalyzer {
    private fun angle(a: PoseLandmark?, b: PoseLandmark?, c: PoseLandmark?): Float {
        if (a == null || b == null || c == null) return -1f
        val radians = atan2((c.position.y - b.position.y).toDouble(), (c.position.x - b.position.x).toDouble()) -
                atan2((a.position.y - b.position.y).toDouble(), (a.position.x - b.position.x).toDouble())
        var degrees = Math.toDegrees(radians).toFloat()
        degrees = abs(degrees)
        if (degrees > 180f) degrees = 360f - degrees
        return degrees
    }

    fun analyzePushup(pose: Pose): PushupResult {
        val lShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val lElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val lWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val lHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val lKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)

        if (lShoulder == null || lElbow == null || lWrist == null || lHip == null) {
            return PushupResult(feedback = FormFeedback.STAND_BACK, elbowAngle = -1f, isDown = false)
        }

        val elbowAngle = angle(lShoulder, lElbow, lWrist)
        val bodyAngle = angle(lShoulder, lHip, lKnee ?: lHip)
        val isDown = elbowAngle < 100f
        val isUp = elbowAngle > 155f

        val feedback = when {
            bodyAngle in 0f..150f -> FormFeedback.KEEP_BACK_STRAIGHT
            isDown && elbowAngle > 80f -> FormFeedback.GO_LOWER
            isUp -> FormFeedback.GOOD_REP
            else -> FormFeedback.GOOD_FORM
        }
        return PushupResult(feedback, elbowAngle, isDown)
    }

    fun analyzeSquat(pose: Pose): SquatResult {
        val lHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val lKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val lAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val lShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)

        if (lHip == null || lKnee == null || lAnkle == null) {
            return SquatResult(feedback = FormFeedback.STAND_BACK, kneeAngle = -1f, isDown = false)
        }

        val kneeAngle = angle(lHip, lKnee, lAnkle)
        val isDown = kneeAngle < 110f
        val isUp = kneeAngle > 160f
        val kneeX = lKnee.position.x
        val ankleX = lAnkle.position.x
        val kneePastToes = abs(kneeX - ankleX) > 80f

        val feedback = when {
            kneePastToes -> FormFeedback.KNEES_OVER_TOES
            isDown && kneeAngle > 90f -> FormFeedback.GO_LOWER
            isUp -> FormFeedback.GOOD_REP
            else -> FormFeedback.GOOD_FORM
        }
        return SquatResult(feedback, kneeAngle, isDown)
    }

    fun analyzePlank(pose: Pose): PlankResult {
        val lShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val lHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val lAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)

        if (lShoulder == null || lHip == null || lAnkle == null) {
            return PlankResult(feedback = FormFeedback.STAND_BACK, bodyAngle = -1f)
        }

        val bodyAngle = angle(lShoulder, lHip, lAnkle)
        val feedback = when {
            bodyAngle > 190f -> FormFeedback.HIPS_TOO_LOW
            bodyAngle < 160f -> FormFeedback.HIPS_TOO_HIGH
            else -> FormFeedback.GOOD_FORM
        }
        return PlankResult(feedback, bodyAngle)
    }
}

data class PushupResult(val feedback: FormFeedback, val elbowAngle: Float, val isDown: Boolean)
data class SquatResult(val feedback: FormFeedback, val kneeAngle: Float, val isDown: Boolean)
data class PlankResult(val feedback: FormFeedback, val bodyAngle: Float)

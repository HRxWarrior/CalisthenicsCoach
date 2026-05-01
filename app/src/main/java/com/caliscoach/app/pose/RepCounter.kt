package com.caliscoach.app.pose

class RepCounter {
    private var wasDown = false
    private var repCount = 0
    private var goodReps = 0

    fun countPushup(result: PushupResult): Int {
        if (result.elbowAngle < 0) return repCount
        if (result.isDown) { wasDown = true }
        else if (wasDown && result.elbowAngle > 155f) {
            repCount++
            if (result.feedback.isGood) goodReps++
            wasDown = false
        }
        return repCount
    }

    fun countSquat(result: SquatResult): Int {
        if (result.kneeAngle < 0) return repCount
        if (result.isDown) { wasDown = true }
        else if (wasDown && result.kneeAngle > 160f) {
            repCount++
            if (result.feedback.isGood) goodReps++
            wasDown = false
        }
        return repCount
    }

    fun getReps() = repCount
    fun getFormScore(): Float = if (repCount > 0) goodReps.toFloat() / repCount else 0f
    fun reset() { wasDown = false; repCount = 0; goodReps = 0 }
}

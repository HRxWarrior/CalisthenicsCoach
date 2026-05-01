package com.caliscoach.app.data.model

enum class ExerciseType(val label: String, val icon: String) {
    PUSHUP("Push-ups", "fitness"),
    SQUAT("Squats", "squat"),
    PLANK("Plank", "plank"),
    JUMPING_JACK("Jumping Jacks", "jump")
}

data class Routine(
    val name: String,
    val difficulty: String,
    val exercises: List<RoutineExercise>,
    val estimatedMinutes: Int
)

data class RoutineExercise(
    val type: ExerciseType,
    val targetReps: Int = 0,
    val targetSeconds: Int = 0
)

enum class FormFeedback(val message: String, val isGood: Boolean) {
    GOOD_REP("Good rep!", true),
    GO_LOWER("Go lower", false),
    KEEP_BACK_STRAIGHT("Keep back straight", false),
    KNEES_OVER_TOES("Watch your knees", false),
    HIPS_TOO_HIGH("Lower your hips", false),
    HIPS_TOO_LOW("Raise your hips", false),
    GOOD_FORM("Good form!", true),
    STAND_BACK("Stand back so camera can see you", false),
    DETECTING("Detecting pose...", true)
}

object RoutineData {
    val beginner = Routine("Beginner Basics", "Easy", listOf(
        RoutineExercise(ExerciseType.PUSHUP, targetReps = 10),
        RoutineExercise(ExerciseType.SQUAT, targetReps = 15),
        RoutineExercise(ExerciseType.PLANK, targetSeconds = 20),
        RoutineExercise(ExerciseType.JUMPING_JACK, targetReps = 20),
    ), estimatedMinutes = 10)

    val intermediate = Routine("Intermediate Push", "Medium", listOf(
        RoutineExercise(ExerciseType.PUSHUP, targetReps = 20),
        RoutineExercise(ExerciseType.SQUAT, targetReps = 25),
        RoutineExercise(ExerciseType.PLANK, targetSeconds = 45),
        RoutineExercise(ExerciseType.PUSHUP, targetReps = 15),
        RoutineExercise(ExerciseType.JUMPING_JACK, targetReps = 30),
    ), estimatedMinutes = 18)

    val advanced = Routine("Advanced Grind", "Hard", listOf(
        RoutineExercise(ExerciseType.PUSHUP, targetReps = 30),
        RoutineExercise(ExerciseType.SQUAT, targetReps = 40),
        RoutineExercise(ExerciseType.PLANK, targetSeconds = 60),
        RoutineExercise(ExerciseType.PUSHUP, targetReps = 25),
        RoutineExercise(ExerciseType.SQUAT, targetReps = 30),
        RoutineExercise(ExerciseType.JUMPING_JACK, targetReps = 50),
    ), estimatedMinutes = 25)

    val all = listOf(beginner, intermediate, advanced)
}

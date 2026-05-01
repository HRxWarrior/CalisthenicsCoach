package com.caliscoach.app.ui.screens

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.caliscoach.app.data.model.ExerciseType
import com.caliscoach.app.pose.CameraAnalyzer
import com.caliscoach.app.ui.components.FeedbackBanner
import com.caliscoach.app.ui.theme.AccentBlue
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.AccentOrange
import com.caliscoach.app.ui.theme.AccentRed
import com.caliscoach.app.ui.theme.DarkBg
import com.caliscoach.app.ui.theme.DarkCard
import com.caliscoach.app.ui.theme.TextPrimary
import com.caliscoach.app.ui.theme.TextSecondary
import com.caliscoach.app.viewmodel.ExerciseViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExerciseScreen(navController: NavController, exerciseType: String, vm: ExerciseViewModel = viewModel()) {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    LaunchedEffect(Unit) { vm.setExerciseType(exerciseType); if (!cameraPermission.status.isGranted) cameraPermission.launchPermissionRequest() }

    val reps by vm.reps.collectAsState()
    val feedback by vm.feedback.collectAsState()
    val timer by vm.timer.collectAsState()
    val isActive by vm.isActive.collectAsState()
    val isSaved by vm.isSaved.collectAsState()

    val type = ExerciseType.entries.firstOrNull { it.name == exerciseType } ?: ExerciseType.PUSHUP
    val color = when (type) { ExerciseType.PUSHUP -> AccentGreen; ExerciseType.SQUAT -> AccentBlue; ExerciseType.PLANK -> AccentOrange; else -> AccentGreen }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Column(Modifier.fillMaxSize().background(DarkBg)) {
        // Header
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = color) }
            Column(Modifier.weight(1f)) {
                Text(type.label, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(if (isActive) "Tracking..." else "Ready", color = TextSecondary, fontSize = 12.sp)
            }
            Text(formatTime(timer), color = color, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        // Camera preview
        if (cameraPermission.status.isGranted) {
            Box(Modifier.weight(1f).fillMaxWidth().padding(horizontal = 12.dp).clip(RoundedCornerShape(16.dp))) {
                AndroidView(factory = { ctx ->
                    val pv = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cp = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also { it.setSurfaceProvider(pv.surfaceProvider) }
                        val analyzer = ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
                            .also { it.setAnalyzer(ContextCompat.getMainExecutor(ctx), CameraAnalyzer { pose -> vm.onPoseDetected(pose) }) }
                        try {
                            cp.unbindAll()
                            cp.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_FRONT_CAMERA, preview, analyzer)
                        } catch (_: Exception) {}
                    }, ContextCompat.getMainExecutor(ctx))
                    pv
                }, modifier = Modifier.fillMaxSize())
            }
        } else {
            Box(Modifier.weight(1f).fillMaxWidth().padding(12.dp).background(DarkCard, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                Text("Camera permission required", color = TextSecondary)
            }
        }

        Spacer(Modifier.height(8.dp))

        // Feedback banner
        Box(Modifier.padding(horizontal = 12.dp)) {
            FeedbackBanner(feedback.message, feedback.isGood)
        }

        Spacer(Modifier.height(8.dp))

        // Stats + controls
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                if (type != ExerciseType.PLANK) {
                    Text("$reps", color = color, fontWeight = FontWeight.Bold, fontSize = 48.sp)
                    Text("reps", color = TextSecondary, fontSize = 14.sp)
                } else {
                    Text(formatTime(timer), color = color, fontWeight = FontWeight.Bold, fontSize = 48.sp)
                    Text("hold time", color = TextSecondary, fontSize = 14.sp)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!isActive) {
                    FloatingActionButton(onClick = { vm.start() }, containerColor = color, contentColor = DarkBg, modifier = Modifier.size(64.dp)) {
                        Icon(Icons.Default.PlayArrow, "Start", modifier = Modifier.size(32.dp))
                    }
                } else {
                    FloatingActionButton(onClick = { vm.stop() }, containerColor = AccentRed, contentColor = TextPrimary, modifier = Modifier.size(64.dp)) {
                        Icon(Icons.Default.Stop, "Stop", modifier = Modifier.size(32.dp))
                    }
                }
                if (!isActive && (reps > 0 || timer > 0L) && !isSaved) {
                    Spacer(Modifier.height(8.dp))
                    FloatingActionButton(onClick = { vm.saveWorkout() }, containerColor = AccentGreen, contentColor = DarkBg, shape = CircleShape, modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Default.Check, "Save")
                    }
                }
                if (isSaved) { Spacer(Modifier.height(4.dp)); Text("Saved!", color = AccentGreen, fontSize = 12.sp) }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

private fun formatTime(seconds: Long): String {
    val m = seconds / 60; val s = seconds % 60
    return String.format("%02d:%02d", m, s)
}

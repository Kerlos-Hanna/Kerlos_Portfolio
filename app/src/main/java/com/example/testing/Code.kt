package com.example.testing

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize

val allColors = listOf(
    Color.Black,
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Magenta,
)

data class MyPath(
    val list: List<Offset>,
    val color: Color,
    val thickness: Float
)

@Composable
fun DrawingCanvas(
    thicknessOfColor: MutableState<Float>,
    color: MutableState<Color>,
    clearCanvas: MutableState<Boolean>,
    path: MutableState<List<Offset>>,
    allPaths: MutableState<List<MyPath>>,
    bitMap: MutableState<Bitmap?>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            path.value += offset
                        },

                        onDrag = { change, _ ->
                            path.value += change.position
                        },

                        onDragEnd = {
                            allPaths.value += MyPath(
                                list = path.value,
                                color = color.value,
                                thickness = thicknessOfColor.value
                            )

                            path.value = emptyList()
                        },

                        onDragCancel = {
                            allPaths.value += MyPath(
                                list = path.value,
                                color = color.value,
                                thickness = thicknessOfColor.value
                            )

                            path.value = emptyList()
                        }
                    )
                }
        ) {
            bitMap.value?.let { btm ->
                drawImage(
                    image = btm.asImageBitmap(),
                    dstSize = IntSize(size.width.toInt(), size.height.toInt())
                )
            }

            if (clearCanvas.value) {
                allPaths.value = emptyList()
                path.value = emptyList()

                clearCanvas.value = false
            }

            if (allPaths.value.isNotEmpty()) {
                allPaths.value.forEach { pathItem ->
                    drawPath(
                        path = Path().apply {
                            moveTo(pathItem.list[0].x, pathItem.list[0].y)

                            for (i in 1..(pathItem.list).lastIndex) {
                                val from = pathItem.list[i - 1]
                                val to = pathItem.list[i]

                                quadraticTo(
                                    x1 = (from.x + to.x) / 2f,
                                    y1 = (from.y + to.y) / 2f,
                                    x2 = to.x,
                                    y2 = to.y
                                )
                            }
                        },
                        color = pathItem.color,
                        style = Stroke(
                            width = pathItem.thickness,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            if (path.value.isNotEmpty()) {
                drawPath(
                    path = Path().apply {
                        moveTo(path.value[0].x, path.value[0].y)

                        for (i in 1..(path.value).lastIndex) {
                            val from = path.value[i - 1]
                            val to = path.value[i]

                            quadraticTo(
                                x1 = (from.x + to.x) / 2f,
                                y1 = (from.y + to.y) / 2f,
                                x2 = to.x,
                                y2 = to.y
                            )
                        }
                    },
                    color = color.value,
                    style = Stroke(
                        width = thicknessOfColor.value,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

@Composable
fun ImagePicker(bitMap: MutableState<Bitmap?> , imagePicked: MutableState<Boolean>) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    imageUri?.let {
        val source = ImageDecoder.createSource(context.contentResolver , it)
        bitMap.value = ImageDecoder.decodeBitmap(source)

        imagePicked.value = false
    }

    LaunchedEffect(true) {
        launcher.launch("image/*")
    }
}
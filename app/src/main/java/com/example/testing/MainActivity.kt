package com.example.testing

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var selectedIndex by remember { mutableIntStateOf(0) }
            val thicknessOfColor = remember { mutableFloatStateOf(0f) }
            val selectedColor = remember { mutableStateOf(Color.Black) }
            var pickedColor by remember { mutableStateOf(Color.Black) }
            val clearCanvas = remember { mutableStateOf(false) }
            val path = remember { mutableStateOf(listOf<Offset>()) }
            val allPaths = remember { mutableStateOf(listOf<MyPath>()) }
            var showAlert by remember { mutableStateOf(false) }
            val imagePicked = remember { mutableStateOf(false) }
            val bitMap = remember { mutableStateOf<Bitmap?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DrawingCanvas(thicknessOfColor , selectedColor , clearCanvas , path , allPaths , bitMap)

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    allColors.forEachIndexed { index , color ->
                        Box(
                            modifier = Modifier.size(30.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = 3.dp,
                                    color = if(selectedIndex == index) Color.White else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedIndex = index

                                    selectedColor.value = allColors[selectedIndex]
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Slider(
                    value = thicknessOfColor.floatValue,
                    onValueChange = { newValue ->
                        thicknessOfColor.floatValue = newValue
                    },
                    valueRange = 0f..30f,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            clearCanvas.value = true
                        }
                    ) {
                        Text(text = "Clear Canvas")
                    }

                    val colorPickerController = rememberColorPickerController()

                    if(showAlert){
                        AlertDialog(
                            modifier = Modifier.fillMaxSize(),
                            title = {
                                Text(
                                    text = "Pick Your Color",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            onDismissRequest = {
                                showAlert = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    selectedColor.value = pickedColor
                                    showAlert = false
                                }) {
                                    Text(text = "Yes")
                                }
                            },

                            dismissButton = {
                                TextButton(onClick = {
                                    showAlert = false
                                }) {
                                    Text(text = "No")
                                }
                            },
                            text = {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    AlphaTile(
                                        modifier = Modifier.weight(1f),
                                        controller = colorPickerController
                                    )

                                    HsvColorPicker(
                                        modifier = Modifier.weight(1f).padding(10.dp),
                                        controller = colorPickerController,
                                        onColorChanged = {
                                            pickedColor = it.color
                                        }
                                    )
                                }
                            }
                        )
                    }

                    Button(
                        onClick = {
                            showAlert = true
                        },
                        modifier = Modifier.scale(0.7f, 0.7f)
                    ) {
                        Text(text = "Color Wheel")
                    }

                    Button(
                        onClick = {
                            allPaths.value = allPaths.value.dropLast(1)
                        },
                        modifier = Modifier.scale(0.7f, 0.7f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = ""
                        )
                    }
                }

                if(imagePicked.value){
                    ImagePicker(bitMap , imagePicked)
                }

                Row(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = {
                            imagePicked.value = true
                        },

                        modifier = Modifier.scale(0.9f , 0.9f)
                    ) {
                        Text(text = "Pick an Image")
                    }

                    Button(
                        onClick = {
                            bitMap.value = null
                        },

                        modifier = Modifier.scale(0.9f , 0.9f)
                    ) {
                        Text(text = "Delete Image")
                    }

                    Button(
                        onClick = {
                            bitMap.value?.let {
                                val fileOutputStream: FileOutputStream
                                val imagePathFile: File?
                                val root = getExternalFilesDir(null)?.absolutePath

                                val myDir = File("$root/Drawing")

                                if(!myDir.exists()){
                                    myDir.mkdirs()
                                }

                                val fileName = "ImageFromDrawingApp-" + System.currentTimeMillis() + ".jpeg"
                                imagePathFile = File(myDir , fileName)

                                fileOutputStream = FileOutputStream(imagePathFile)

                                bitMap.value?.compress(Bitmap.CompressFormat.JPEG , 100 , fileOutputStream)

                                Toast.makeText(
                                    this@MainActivity,
                                    "Image is saved successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                fileOutputStream.flush()
                                fileOutputStream.close()
                            }
                        },

                        modifier = Modifier.scale(0.9f , 0.9f)
                    ) {
                        Text(text = "Save Image")
                    }
                }
            }
        }
    }
}
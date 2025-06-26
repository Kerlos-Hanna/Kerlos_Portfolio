package com.example.countryquiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Quiz(navController: NavController , name: String?) {

    var showDialogWhenBackButtonIsPressed by remember { mutableStateOf(false) }

    BackHandler {
        showDialogWhenBackButtonIsPressed = true
    }

    if(showDialogWhenBackButtonIsPressed){
        AlertDialog(
            title = {
                Text(text = "Warning")
            },

            text = {
                Text(text = "Are you sure that you want to exit ?")
            },

            onDismissRequest = {
                showDialogWhenBackButtonIsPressed = false
            },

            confirmButton = {
                TextButton(onClick = {
                    showDialogWhenBackButtonIsPressed = false
                    navController.navigate(route = "HomePage"){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Yes")
                }
            },

            dismissButton = {
                TextButton(onClick = {
                    showDialogWhenBackButtonIsPressed = false
                }) {
                    Text(text = "No")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Quiz App" , fontSize = 30.sp)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB567E5)
                )
            )
        }
    ) { padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Which country does this flag belong to?",
                color = Color.Black,
                fontSize = 20.sp
            )

            var indexOfLists by remember { mutableIntStateOf(0) }

            val countriesList = listOf(
                Country(R.drawable.brazil , "Brazil"),
                Country(R.drawable.france , "France"),
                Country(R.drawable.egypt , "Egypt"),
                Country(R.drawable.america , "America"),
                Country(R.drawable.argentina , "Argentina"),
                Country(R.drawable.belgium , "Belgium"),
                Country(R.drawable.burkina_faso , "Burkina Faso"),
                Country(R.drawable.morocco , "Morocco"),
                Country(R.drawable.purtogal , "Portugal"),
                Country(R.drawable.tunisia , "Tunisia"),
            )

            val choicesList = listOf(
                Choices("Brazil" , "France" , "Egypt" , "Morocco"),
                Choices("America" , "France" , "Argentina" , "Tunisia"),
                Choices("Egypt" , "Argentina" , "Belgium" , "Burkina Faso"),
                Choices("Egypt" , "Argentina" , "Portugal" , "America"),
                Choices("Morocco" , "Portugal" , "Argentina" , "Burkina Faso"),
                Choices("Morocco" , "Egypt" , "Belgium" , "France"),
                Choices("Tunisia" , "Belgium" , "Burkina Faso" , "America"),
                Choices("Morocco" , "Tunisia" , "Portugal" , "Argentina"),
                Choices("Belgium" , "Portugal" , "Tunisia" , "Egypt"),
                Choices("Tunisia" , "Egypt" , "Morocco" , "Burkina Faso"),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(countriesList[indexOfLists].image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            var offsetOfSlider by remember { mutableFloatStateOf(0f) }
            var widthOfSlider by remember { mutableFloatStateOf(0f) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.padding(top = 8.dp)) {
                    Canvas(modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(3.dp)) {
                        drawLine(
                            color = Color.LightGray,
                            start = Offset.Zero,
                            end = Offset(size.width, 0f),
                            strokeWidth = 10f
                        )
                    }

                    Canvas(modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(3.dp)) {

                        widthOfSlider = (size.width)/9

                        drawLine(
                            color = Color.Green,
                            start = Offset.Zero,
                            end = Offset(offsetOfSlider, 0f),
                            strokeWidth = 10f
                        )
                    }
                }

                Text(
                    text = "${indexOfLists + 1}/10",
                    modifier = Modifier.align(Alignment.Top),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            var borderColor1 by remember { mutableStateOf(Color.LightGray) }
            var borderColor2 by remember { mutableStateOf(Color.LightGray) }
            var borderColor3 by remember { mutableStateOf(Color.LightGray) }
            var borderColor4 by remember { mutableStateOf(Color.LightGray) }

            var backGround1 by remember { mutableStateOf(Color.Transparent) }
            var backGround2 by remember { mutableStateOf(Color.Transparent) }
            var backGround3 by remember { mutableStateOf(Color.Transparent) }
            var backGround4 by remember { mutableStateOf(Color.Transparent) }

            var pointsOfTheGame by remember { mutableIntStateOf(0) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(width = 1.dp, color = borderColor1, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(backGround1)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        borderColor1 = Color.Magenta
                        borderColor2 = Color.LightGray
                        borderColor3 = Color.LightGray
                        borderColor4 = Color.LightGray
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = choicesList[indexOfLists].choice1)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = borderColor2, shape = RoundedCornerShape(10.dp))
                    .background(backGround2)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        borderColor2 = Color.Magenta
                        borderColor1 = Color.LightGray
                        borderColor3 = Color.LightGray
                        borderColor4 = Color.LightGray
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = choicesList[indexOfLists].choice2)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = borderColor3, shape = RoundedCornerShape(10.dp))
                    .background(backGround3)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        borderColor3 = Color.Magenta
                        borderColor2 = Color.LightGray
                        borderColor1 = Color.LightGray
                        borderColor4 = Color.LightGray
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = choicesList[indexOfLists].choice3)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = borderColor4, shape = RoundedCornerShape(10.dp))
                    .background(backGround4)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        borderColor4 = Color.Magenta
                        borderColor2 = Color.LightGray
                        borderColor3 = Color.LightGray
                        borderColor1 = Color.LightGray
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = choicesList[indexOfLists].choice4)
            }

            Spacer(modifier = Modifier.height(10.dp))

            var changeTextButton by remember { mutableStateOf("CHECK") }

            var checkForSelectionAnOption by remember { mutableStateOf(false) }

            Button(
                onClick = {
                    if(changeTextButton == "CHECK"){
                        if(borderColor1 == Color.Magenta){
                            if(choicesList[indexOfLists].choice1 == countriesList[indexOfLists].name){
                                backGround1 = Color.Green

                                pointsOfTheGame++
                            }

                            else{
                                backGround1 = Color.Red

                                if(choicesList[indexOfLists].choice1 == countriesList[indexOfLists].name){
                                    backGround1 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice2 == countriesList[indexOfLists].name){
                                    backGround2 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice3 == countriesList[indexOfLists].name){
                                    backGround3 = Color.Green
                                }

                                else{
                                    backGround4 = Color.Green
                                }
                            }

                            changeTextButton = "NEXT"
                        }

                        else if(borderColor2 == Color.Magenta){
                            if(choicesList[indexOfLists].choice2 == countriesList[indexOfLists].name){
                                backGround2 = Color.Green

                                pointsOfTheGame++
                            }

                            else{
                                backGround2 = Color.Red

                                if(choicesList[indexOfLists].choice1 == countriesList[indexOfLists].name){
                                    backGround1 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice2 == countriesList[indexOfLists].name){
                                    backGround2 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice3 == countriesList[indexOfLists].name){
                                    backGround3 = Color.Green
                                }

                                else{
                                    backGround4 = Color.Green
                                }
                            }

                            changeTextButton = "NEXT"
                        }

                        else if(borderColor3 == Color.Magenta){
                            if(choicesList[indexOfLists].choice3 == countriesList[indexOfLists].name){
                                backGround3 = Color.Green

                                pointsOfTheGame++
                            }

                            else{
                                backGround3 = Color.Red

                                if(choicesList[indexOfLists].choice1 == countriesList[indexOfLists].name){
                                    backGround1 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice2 == countriesList[indexOfLists].name){
                                    backGround2 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice3 == countriesList[indexOfLists].name){
                                    backGround3 = Color.Green
                                }

                                else{
                                    backGround4 = Color.Green
                                }
                            }

                            changeTextButton = "NEXT"
                        }

                        else if(borderColor4 == Color.Magenta){
                            if(choicesList[indexOfLists].choice4 == countriesList[indexOfLists].name){
                                backGround4 = Color.Green

                                pointsOfTheGame++
                            }

                            else{
                                backGround4 = Color.Red

                                if(choicesList[indexOfLists].choice1 == countriesList[indexOfLists].name){
                                    backGround1 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice2 == countriesList[indexOfLists].name){
                                    backGround2 = Color.Green
                                }

                                else if(choicesList[indexOfLists].choice3 == countriesList[indexOfLists].name){
                                    backGround3 = Color.Green
                                }

                                else{
                                    backGround4 = Color.Green
                                }
                            }

                            changeTextButton = "NEXT"
                        }

                        else{
                            checkForSelectionAnOption = true
                        }
                    }

                    else{
                        borderColor1 = Color.LightGray
                        borderColor2 = Color.LightGray
                        borderColor3 = Color.LightGray
                        borderColor4 = Color.LightGray

                        backGround1 = Color.Transparent
                        backGround2 = Color.Transparent
                        backGround3 = Color.Transparent
                        backGround4 = Color.Transparent

                        changeTextButton = "CHECK"

                        if(indexOfLists==9){
                            navController.navigate("ResultPage/$name/$pointsOfTheGame"){
                                popUpTo(0){
                                    inclusive = true
                                }
                            }
                        }

                        else{
                            indexOfLists++

                            offsetOfSlider+=widthOfSlider
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = changeTextButton)
            }

            if(checkForSelectionAnOption){
                AlertDialog(
                    title = {
                        Text(text = "Warning")
                    },

                    text = {
                        Text(text = "You must choose an option")
                    },

                    onDismissRequest = {
                        checkForSelectionAnOption = false
                    },

                    confirmButton = {},

                    dismissButton = {}
                )
            }
        }
    }
}

data class Choices(
    val choice1: String,
    val choice2: String,
    val choice3: String,
    val choice4: String,
)

data class Country(
    val image: Int,
    val name: String
)
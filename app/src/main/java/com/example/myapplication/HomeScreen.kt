package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Beige1
import com.example.myapplication.ui.theme.BlueViolet1
import com.example.myapplication.ui.theme.BlueViolet3
import com.example.myapplication.ui.theme.ButtonBlue
import com.example.myapplication.ui.theme.DarkerButtonBlue
import com.example.myapplication.ui.theme.DeepBlue
import com.example.myapplication.ui.theme.LightGreen1
import com.example.myapplication.ui.theme.LightRed
import com.example.myapplication.ui.theme.OrangeYellow1
import com.example.myapplication.ui.theme.TextWhite

@Composable
fun Welcoming(){
    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.11f)) {
        Column(modifier = Modifier.fillMaxWidth(0.7f).padding(20.dp)) {

            val gothicaFamily = FontFamily(
                Font(R.font.gothica1_bold , weight = FontWeight.Bold),
                Font(R.font.gothica1_regular , weight = FontWeight.Normal)
            )

            Text(
                text = "Good Morning, Kerlos" ,
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp ,
                    fontFamily = gothicaFamily ,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "We wish you have a good day!" ,
                color = Color(0xFF777777),
                style = TextStyle(
                    fontSize = 13.sp ,
                    fontFamily = gothicaFamily ,
                    fontWeight = FontWeight.Normal
                )
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Search Icon",
            modifier = Modifier.fillMaxWidth().padding(15.dp).clickable {  },
            alignment = Alignment.TopEnd
        )
    }
}

@Composable
fun AppBarButtons(){

    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.08f).padding(start = 20.dp , end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

        var color1 by remember {mutableStateOf(ButtonBlue)}
        var color2 by remember {mutableStateOf(DarkerButtonBlue)}
        var color3 by remember {mutableStateOf(DarkerButtonBlue)}

        CustomButton("Sweet Sleep" , color1){
            color1 = if(it==1) {color2 = DarkerButtonBlue; color3 = DarkerButtonBlue; ButtonBlue} else DarkerButtonBlue
        }
        Spacer(modifier = Modifier.size(5.dp))

        CustomButton("Insomnia" , color2){
            color2 = if(it==2) {color1 = DarkerButtonBlue; color3 = DarkerButtonBlue; ButtonBlue} else DarkerButtonBlue
        }
        Spacer(modifier = Modifier.size(5.dp))

        CustomButton("Depression" , color3){
            color3 = if(it==3) {color2 = DarkerButtonBlue; color1 = DarkerButtonBlue; ButtonBlue} else DarkerButtonBlue
        }
    }
}

@Composable
fun CustomButton(text: String , color: Color , changingColors: (Int) -> Unit){
    Button(onClick = {

        val choosing = if(text == "Sweet Sleep") 1 else if(text == "Insomnia") 2 else 3

        changingColors(choosing)
    },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonColors(
            containerColor = color,
            contentColor = Color.White,
            disabledContainerColor = color,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun LargeBox(){
    Box(modifier = Modifier.fillMaxHeight(0.2f)
        .fillMaxWidth()
        .padding(20.dp)
        .clip(shape = RoundedCornerShape(20.dp))
    ){
        Row(modifier = Modifier
                .fillMaxSize()
                .background(LightRed)
        ) {
            Column(modifier = Modifier.padding(20.dp).fillMaxHeight().fillMaxWidth(0.5f)) {
                val gothicaFamily = FontFamily(
                    Font(R.font.gothica1_bold , weight = FontWeight.Bold),
                    Font(R.font.gothica1_regular , weight = FontWeight.Normal)
                )
                Text(
                    text = "Daily Thought",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = gothicaFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.size(10.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Meditation",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = gothicaFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    )

                    Box(modifier = Modifier.fillMaxHeight().offset(0.dp , (-5).dp)) {
                        Text(
                            modifier = Modifier,
                            text = " . ",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = gothicaFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        )
                    }

                    Text(
                        text = "3-10 min",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = gothicaFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize().offset(30.dp , 0.dp)) {

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = BlueViolet3 , radius = size.minDimension/4)
                }

                Image(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Play Sound",
                    modifier = Modifier.fillMaxSize().padding(35.dp))

            }
        }
    }
}

@Composable
fun CardView(name: String , painter: Painter , description:String , color: Color){

    Box(
        modifier = Modifier.size(180.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color),
    ) {
        Column(modifier = Modifier.padding(start = 8.dp , top = 7.dp)) {

            val gothicaFamily = FontFamily(
                Font(R.font.gothica1_bold , weight = FontWeight.Bold),
                Font(R.font.gothica1_regular , weight = FontWeight.Normal)
            )

            Text(
                text = name,
                style = TextStyle(
                    fontFamily = gothicaFamily,
                    fontSize = 20.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                modifier = Modifier.fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(modifier = Modifier.padding(bottom = 5.dp) , painter = painter , contentDescription = description)

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
                    Button(onClick = {

                    }) {
                        Text(text = "Start")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(){

    val navigationList = listOf(
        NavigationItem("Home" , painterResource(R.drawable.ic_home) , "Home Icon"),
        NavigationItem("Meditate" , painterResource(R.drawable.ic_bubble) , "Meditate Icon"),
        NavigationItem("Sleep" , painterResource(R.drawable.ic_moon) , "Sleep Icon"),
        NavigationItem("Music" , painterResource(R.drawable.ic_music) , "Music Icon"),
        NavigationItem("Profile" , painterResource(R.drawable.ic_profile) , "Profile Icon")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = DeepBlue) {
                var selector by remember { mutableIntStateOf(0) }

                navigationList.forEachIndexed {index , item ->
                    NavigationBarItem(
                        selected = selector==index,
                        onClick = {
                            selector = index
                        },
                        icon = {
                            Image(painter = item.icon , contentDescription = item.description)
                        },
                        label = {
                            Text(text = item.name)
                        },

                        colors = NavigationBarItemColors(
                            disabledIconColor = TextWhite,
                            disabledTextColor = TextWhite,
                            selectedIconColor = BlueViolet3,
                            selectedTextColor = TextWhite,
                            unselectedIconColor = TextWhite,
                            unselectedTextColor = TextWhite,
                            selectedIndicatorColor = BlueViolet3
                        )
                    )
                }
            }
        }
    ) {innerPadding->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(DeepBlue)){
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Column(modifier = Modifier.padding(start = 20.dp)){
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {

                            CardView("Sleep Meditation" , painterResource(R.drawable.ic_headphone) , "Sleep Meditation" , BlueViolet1)

                            Spacer(modifier = Modifier.size(10.dp))

                            CardView("Tips for Sleeping" , painterResource(R.drawable.ic_videocam) , "Tips for Sleeping" , LightGreen1)
                        }

                        Spacer(modifier = Modifier.size(10.dp))

                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {

                            CardView("Night Island" , painterResource(R.drawable.ic_headphone) , "Night Island" , OrangeYellow1)

                            Spacer(modifier = Modifier.size(10.dp))

                            CardView("Calming Sounds" , painterResource(R.drawable.ic_headphone) , "Calming Sounds" , Beige1)
                        }
                    }
                }
            }
        }
    }
}

data class NavigationItem(val name: String , val icon: Painter , val description: String)
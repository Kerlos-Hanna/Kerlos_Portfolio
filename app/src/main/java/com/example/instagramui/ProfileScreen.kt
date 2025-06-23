package com.example.instagramui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Arrow Back",
            tint = Color.Black,
            modifier = Modifier.clickable {

            }
        )

        Text(
            text = "KerlosMelad_official",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Icon(
            painter = painterResource(R.drawable.ic_bell),
            contentDescription = "Arrow Back",
            tint = Color.Black,
            modifier = Modifier
                .size(25.dp)
                .clickable {

                }
        )

        Icon(
            painter = painterResource(R.drawable.ic_dotmenu),
            contentDescription = "Arrow Back",
            tint = Color.Black,
            modifier = Modifier.clickable {

            }
        )
    }
}

@Composable
fun ProfileStatus() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.kerlos),
            contentDescription = "Image Profile",
            modifier = Modifier
                .fillMaxSize(0.15f)
                .aspectRatio(1f)
                .scale(1.5f, 1.5f)
                .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Texts("601" , "Posts")
        Texts("99.8K" , "Followers")
        Texts("72" , "Following")

    }
}

@Composable
fun Texts(numberText: String, text: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = numberText , fontSize = 20.sp , fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = text , fontSize = 20.sp , fontWeight = FontWeight.Normal)
    }

}

@Composable
fun ProfileDescription() {
    Column {
        Text(text = "Programming Mentor" , fontSize = 20.sp , fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "10 years of coding experience \uD83D\uDCBB" , fontSize = 15.sp , fontWeight = FontWeight.Normal)

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "Want me to make your app? Send me an email! \uD83D\uDCE7" , fontSize = 15.sp , fontWeight = FontWeight.Normal)

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "⬇\uFE0FAndroid tutorials? Subscribe to my channel!⬇\uFE0F" , fontSize = 15.sp , fontWeight = FontWeight.Normal)

        Spacer(modifier = Modifier.height(2.dp))

        Text(text = "https://youtube.com/c/kerlosMelad" , fontSize = 15.sp , fontWeight = FontWeight.Normal , color = Color(0xFF3D3D91))

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                    )
                ){
                    append("Followed by ")
                }

                append("codingInflow, Tommy ")

                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                    )
                ){
                    append("and ")
                }

                append("17 others")
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ActionButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround , verticalAlignment = Alignment.CenterVertically){
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Following",
                color = Color.Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 7.dp)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Following drop down list",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 7.dp)
            )
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Message",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Email",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }

        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Following drop down list"
            )
        }
    }
}

@Composable
fun StatuesInstagram() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ){

        val size = 50.dp

        Column {
            Image(
                painter = painterResource(R.drawable.youtube),
                contentDescription = "Youtube statue",
                modifier = Modifier
                    .size(size)
                    .aspectRatio(1f)
                    .scale(1.5f, 1.5f)
                    .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Youtube" , color = Color.Black)
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.qa),
                contentDescription = "Q&A statue",
                modifier = Modifier
                    .size(size)
                    .aspectRatio(1f)
                    .scale(1.5f, 1.5f)
                    .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Q&A" , color = Color.Black)
        }

        Column {
            Image(
                painter = painterResource(R.drawable.discord),
                contentDescription = "Discord statue",
                modifier = Modifier
                    .size(size)
                    .aspectRatio(1f)
                    .scale(1.5f, 1.5f)
                    .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Discord" , color = Color.Black)
        }

        Column {
            Image(
                painter = painterResource(R.drawable.telegram),
                contentDescription = "Telegram statue",
                modifier = Modifier
                    .size(size)
                    .aspectRatio(1f)
                    .scale(1.5f, 1.5f)
                    .border(width = 3.dp, color = Color.LightGray, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Telegram" , color = Color.Black)
        }
    }
}

@Composable
fun PostsTabs() {
    val tabList = listOf(
        R.drawable.ic_grid,
        R.drawable.ic_reels,
        R.drawable.ic_igtv,
        R.drawable.profile
    )

    var selected by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        TabRow(
            selectedTabIndex = selected,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selected]),
                    color = Color.Black
                )
            }
        ) {
            tabList.forEachIndexed {index , item ->
                Tab(
                    selected = index==selected,
                    onClick = {
                        selected = index
                    }
                ) {
                    Icon(
                        painter = painterResource(item),
                        contentDescription = "",
                        modifier = Modifier.size(35.dp).padding(bottom = 5.dp),
                        tint = if(selected==index) Color.Black else Color.LightGray
                    )
                }
            }
        }

        val posts = listOf(
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
            painterResource(R.drawable.multiple_languages),
            painterResource(R.drawable.bad_habits),
            painterResource(R.drawable.intermediate_dev),
            painterResource(R.drawable.kmm),
            painterResource(R.drawable.learn_coding_fast),
            painterResource(R.drawable.master_logical_thinking),
        )

        if(selected==0){
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 45.dp)
            ) {
                items(
                    count = posts.size
                ){
                    Image(
                        painter = posts[it],
                        contentDescription = "",
                        modifier = Modifier.aspectRatio(1f).border(1.dp , color = Color.White),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}
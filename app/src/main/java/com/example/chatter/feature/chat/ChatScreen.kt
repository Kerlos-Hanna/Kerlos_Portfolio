package com.example.chatter.feature.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.chatter.FirebaseMessageService
import com.example.chatter.MainActivity
import com.example.chatter.R
import com.example.chatter.feature.supabase.SupabaseStorage
import com.example.chatter.feature.video_call.CallButton
import com.example.chatter.feature.video_call.ZEGOConstants
import com.google.firebase.auth.FirebaseAuth
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity", "MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController , channelName: String , channelID: String) {

    val viewModel:ChatViewModel = hiltViewModel()
    val messageUI = viewModel.messageUI.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val messageSender by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser?.displayName) }
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    var isAttachedButtonClicked by remember { mutableStateOf(false) }
    var loadingImageState by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    var imageUrlMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val activity = context as Activity
    val coroutineScope = rememberCoroutineScope()
    val supabaseStorage = SupabaseStorage()
    val applicationContext = LocalContext.current as MainActivity
    val list by remember { mutableStateOf(mutableListOf<ZegoUIKitUser>()) }

    viewModel.getAllUsers { users ->
        users.forEach { user ->
            list.add(ZegoUIKitUser(user.userID , user.userName))
        }
    }

    val cameraImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { success ->
        if(success){
            coroutineScope.launch {
                loadingImageState = true

                imageUrlMessage = supabaseStorage.uploadImage(cameraImageUri.value!! , context)

                viewModel.sendMessage(channelID , messageText , imageUrlMessage)

                loadingImageState = false
            }
        }
    }

    LaunchedEffect(true) {
        if(FirebaseAuth.getInstance().currentUser?.uid!= null && FirebaseAuth.getInstance().currentUser?.displayName!= null){
            applicationContext.initZEGOCLOUDInviteService(
                ZEGOConstants.appID,
                ZEGOConstants.appSign,
                FirebaseAuth.getInstance().currentUser?.uid!!,
                FirebaseAuth.getInstance().currentUser?.displayName!!
            )
        }
    }

    val galleryImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        if(uri != null){
            coroutineScope.launch {
                loadingImageState = true

                imageUrlMessage = supabaseStorage.uploadImage(uri , context)

                viewModel.sendMessage(channelID , messageText , imageUrlMessage)

                loadingImageState = false
            }
        }
    }

    val permissionCamera = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted){
            cameraImageLauncher.launch(viewModel.createImageUri(context , cameraImageUri))
        }
    }

    val permissionGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted){
            galleryImageLauncher.launch("image/*")
        }
    }

    viewModel.listenForMessages(channelID)

    BackHandler(enabled = isFocused.value) {
        focusManager.clearFocus()
    }

    LaunchedEffect(messageUI.value.size) {
        if(messageUI.value.isNotEmpty()) {
            lazyListState.scrollToItem(index = messageUI.value.lastIndex)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = channelName)

                        Row {
                            CallButton(false){ button ->
                                button.setInvitees(list)
                            }

                            CallButton(true){ button ->
                                button.setInvitees(list)
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF626262),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.Black)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }) {

            viewModel.subscribeForNotification(channelID)

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.91f)
                    .fillMaxWidth()
                    .padding(10.dp),
                state = lazyListState
            ){
                items(messageUI.value){ message ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if(viewModel.isCurrentUserSendMessage(message.senderId)){
                            Row {
                                Image(
                                    painter = painterResource(R.drawable.kerlosmelad_profile),
                                    contentDescription = "Logo profile",
                                    modifier = Modifier.size(50.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                if(message.imageUrlMessage != null){
                                    Image(
                                        painter = rememberAsyncImagePainter(model = message.imageUrlMessage),
                                        contentDescription = "Image was sent",
                                        modifier = Modifier.size(200.dp)
                                            .clickable {
                                                navController.navigate("ImageFullScreen/${Uri.encode(message.imageUrlMessage)}")
                                            }
                                    )
                                }

                                else{
                                    Box(
                                        modifier = Modifier.defaultMinSize(minHeight = 50.dp , minWidth = 100.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFF626262)),
                                        contentAlignment = Alignment.CenterStart
                                    ){
                                        Text(
                                            text = message.messageText,
                                            modifier = Modifier.padding(5.dp),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        else{
                            if(message.imageUrlMessage != null){
                                Image(
                                    painter = rememberAsyncImagePainter(model = message.imageUrlMessage),
                                    contentDescription = "Image was sent",
                                    modifier = Modifier.size(200.dp)
                                        .align(Alignment.CenterEnd)
                                        .clickable {
                                            navController.navigate("ImageFullScreen/${Uri.encode(message.imageUrlMessage)}")
                                        }
                                )
                            }

                            else{
                                Box(
                                    modifier = Modifier.defaultMinSize(minHeight = 50.dp , minWidth = 100.dp)
                                        .fillMaxWidth(0.8f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.Cyan.copy(alpha = 0.5f))
                                        .align(Alignment.CenterEnd),
                                    contentAlignment = Alignment.CenterStart
                                ){
                                    Text(text = message.messageText , modifier = Modifier.padding(5.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            if(isAttachedButtonClicked){
                AlertDialog(
                    title = {
                        Text(text = "Select your source!")
                    },
                    text = {
                        Text(text = "Gallery or Camera")
                    },

                    onDismissRequest = {
                        isAttachedButtonClicked = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                isAttachedButtonClicked = false

                                if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                    galleryImageLauncher.launch("image/*")
                                }

                                else if(!ActivityCompat.shouldShowRequestPermissionRationale(activity , Manifest.permission.READ_EXTERNAL_STORAGE)){
                                    Toast.makeText(context , "Go to settings to give permission" , Toast.LENGTH_LONG).show()
                                }

                                else{
                                    permissionGallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        ) {
                            Text(text = "Gallery")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                isAttachedButtonClicked = false

                                if(context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                    cameraImageLauncher.launch(viewModel.createImageUri(context , cameraImageUri))
                                }

                                else if(!ActivityCompat.shouldShowRequestPermissionRationale(activity , Manifest.permission.CAMERA)){
                                    Toast.makeText(context , "Go to settings to give permission" , Toast.LENGTH_LONG).show()
                                }

                                else{
                                    permissionCamera.launch(Manifest.permission.CAMERA)
                                }
                            }
                        ) {
                            Text(text = "Camera")
                        }
                    }
                )
            }

            if (loadingImageState){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else{
                Row(
                    modifier = Modifier.fillMaxSize().weight(1f),
                ) {
                    IconButton (
                        onClick = {
                            isAttachedButtonClicked = true

                            FirebaseMessageService().postNotificationToUsers(channelID , channelName , messageSender!!, "Image is sent" , context)
                        },
                        modifier = Modifier.fillMaxWidth(0.17f)
                            .background(Color(0xFF626262))
                            .fillMaxHeight()
                    ){
                        Image(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Send Message"
                        )
                    }

                    TextField(
                        value = messageText,
                        onValueChange = {
                            messageText = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(Color(0xFF626262))
                            .fillMaxHeight()
                            .onFocusChanged { focusState ->
                                isFocused.value = focusState.isFocused
                            },
                        placeholder = {
                            Text(text = "Message")
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.White,
                            unfocusedContainerColor = Color(0xFF626262),
                            focusedContainerColor = Color(0xFF626262),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedPlaceholderColor = Color.White,
                            focusedPlaceholderColor = Color.White
                        )
                    )

                    IconButton (
                        onClick = {
                            viewModel.sendMessage(channelID =  channelID , messageText = messageText , imageUrlMessage)

                            FirebaseMessageService().postNotificationToUsers(channelID , channelName , messageSender!!, messageText , context)

                            messageText = ""
                        },

                        enabled = messageText.isNotEmpty(),

                        modifier = Modifier.background(Color(0xFF626262))
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ){
                        Image(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Message",
                        )
                    }
                }
            }
        }
    }
}
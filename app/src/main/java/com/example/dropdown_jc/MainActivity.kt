package com.example.dropdown_jc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = Color(0xFF101010),
                modifier = Modifier.fillMaxSize()
            ) {
                DropDown(text = "Hello World" , modifier = Modifier.padding(15.dp)) {
                    Text(text = "This is now Revealed", modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Green) )
                }
            }
        }
    }
}

@Composable
fun DropDown(
    text:String,
    modifier:Modifier=Modifier,
    initiallyOpened: Boolean = false,
    content : @Composable () -> Unit,
) {
    var isOpened by remember{
       mutableStateOf(initiallyOpened)
    }

    val alpha = animateFloatAsState(
        targetValue = if(isOpened) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    val rotateX = animateFloatAsState(
        targetValue = if(isOpened) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =text,
                color= Color.White,
                fontSize = 16.sp
            )
            
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Open or Close menu",
            tint = Color.White,
                modifier = Modifier
                    .clickable {
                        isOpened = !isOpened
                    }
                    .scale(1f, if (isOpened) -1f else 1f) // -1f will create mirror image
                )
            
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(
                        0.5f,
                        0f
                    ) // this will move the pivot to the 50% of the width of box in x direction and 0% of height of box in y direction
                    rotationX = rotateX.value
                }//that allows us to do 3d transformation
                .alpha(alpha.value)
        ){
            content()
        }

    }


}


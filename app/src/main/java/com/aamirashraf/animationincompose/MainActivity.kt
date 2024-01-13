package com.aamirashraf.animationincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aamirashraf.animationincompose.ui.theme.AnimationInComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Color(0xff101010),
                    modifier = Modifier.fillMaxSize()

                        .height(100.dp)
                ) {
                    DropDown(text = "hello world",
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(text = "This is now reveled",
                        modifier = Modifier.fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Yellow)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropDown(
    text:String,
    modifier: Modifier=Modifier,
    initiallyOpened:Boolean=false,
    content:@Composable ()->Unit
){
    var isOpened by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha= animateFloatAsState(
        targetValue =if(isOpened)1f else 0f ,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX= animateFloatAsState(
        targetValue =if(isOpened)0f else 1f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Row(

        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment=Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
        Icon(
            imageVector =Icons.Default.ArrowDropDown,
            contentDescription = "open or close the drop down menu",
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    isOpened != isOpened
                }
                .scale(1f, if (isOpened) -1f else 1f)
        )

    }
    Spacer(modifier = Modifier.heightIn(10.dp))
    Box(
        modifier= Modifier
            .fillMaxSize()
            .graphicsLayer {
                transformOrigin = TransformOrigin(0.5f, 0f)
                rotationX = rotateX.value
            }
            .alpha(alpha.value)

    ){
        content()
    }

}
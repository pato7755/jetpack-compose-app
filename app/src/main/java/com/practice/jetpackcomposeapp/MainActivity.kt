package com.practice.jetpackcomposeapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = 20) {
            Icon(
                imageVector = Icons.Default.AddCircle, contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Composable
fun clickCounter() {
    var count by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            fontSize = 30.sp
        )
        Button(
            onClick = {
                count++
            },
        ) {
            Text(
                text = "Click me: $count"
            )
        }
    }
}

@Composable
fun MessageCard(msg: Message) {

    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Color palette",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )

        Column(
            modifier = Modifier.clickable { isExpanded = !isExpanded
                                          println("Clicked")},
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

data class Message(val author: String, val body: String)

@Preview(name = "Light Mode")
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() { // this Preview function cannot have parameters
    JetpackComposeAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Conversation(messages = SampleData.conversationSample)
        }
    }
}
package com.rmaprojects.richtexteditorsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.richtexteditorsample.ui.theme.RichTextEditorSampleTheme
import com.rmaprojects.wysiwyg.data.RichEditorState
import com.rmaprojects.wysiwyg.parser.JsonEditorParser
import com.rmaprojects.wysiwyg.ui.RichEditor
import com.rmaprojects.wysiwyg.ui.StyleContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDarkMode by remember {
                mutableStateOf(false)
            }

            RichTextEditorSampleTheme(
                darkTheme = isDarkMode
            ) {
                Sample(
                    isDarkMode,
                    onDarkModeChange = {
                        isDarkMode = !isDarkMode
                    }
                )
            }
        }
    }

}

@Composable
private fun Sample(
    isDarkMode: Boolean,
    onDarkModeChange: () -> Unit
) {

    val richEditorState = remember {
        RichEditorState.Builder()
            .setInput("")
            .adapter(JsonEditorParser())
            .build()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            StyleContainer(
                state = richEditorState,
                styleButtons = {
                    /**
                     * You can fill your own StyleButton here
                     */
                },
            )
            RichEditor(
                state = richEditorState,
                modifier = Modifier.fillMaxWidth().height(128.dp),
                onTextChanged = {
//                    Log.d("OUTPUT", richEditorState.output())
                },

                )
            Button(onClick = { onDarkModeChange() }) {
                Text(text = "Set DarkMode")
            }
        }
    }
}
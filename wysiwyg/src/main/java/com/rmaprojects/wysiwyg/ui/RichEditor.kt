package com.rmaprojects.wysiwyg.ui

import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.EditText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.doAfterTextChanged
import com.rmaprojects.wysiwyg.data.RichEditorState

@Composable
fun RichEditor(
    state: RichEditorState,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    isDarkMode: Boolean = isSystemInDarkTheme()
) {

    Box(modifier = modifier) {
        val context = LocalContext.current
        val editText = remember {
            EditText(context)
        }
        key(isDarkMode) {
            val textColor = MaterialTheme.colorScheme.onBackground
            AndroidView(
                modifier = Modifier.clickable {
                    editText.requestFocus()
                },
                factory = {
                    editText.apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        background = null

                        accessibilityDelegate = object : View.AccessibilityDelegate() {
                            override fun sendAccessibilityEvent(host: View, eventType: Int) {
                                super.sendAccessibilityEvent(host, eventType)
                                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                                    state.manager.adjustSelection(
                                        TextRange(selectionStart, selectionEnd)
                                    )
                                }
                            }
                        }
                        doAfterTextChanged { editable ->
                            editable?.let {
                                state.manager.onTextFieldValueChange(
                                    it, TextRange(selectionStart, selectionEnd)
                                )
                                onTextChanged(state.output())
                            }
                        }
                        state.manager.setEditable(text)
                        state.manager.adjustSelection(
                            TextRange(
                                selectionStart,
                                selectionEnd
                            )
                        )
                    }
                },
                update = {
                    it.setTextColor(textColor.toArgb())
                }
            )
        }
    }
}

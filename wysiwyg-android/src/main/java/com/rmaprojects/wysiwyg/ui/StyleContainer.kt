package com.rmaprojects.wysiwyg.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.rmaprojects.wysiwyg.R
import com.rmaprojects.wysiwyg.data.RichEditorState
import com.rmaprojects.wysiwyg.utils.TextSpanStyle

/**
 * [state] is Required, because [StyleContainer] modifies texts inside [state]
 *
 * [styleButtons] is Optional, you can add your own button with [StyleButton] inside this container
 *
 * [onCheckButtonClick] is Optional too, fill it with your own function and the [IconButton] with Check Icon will appear
 */

@Composable
fun StyleContainer(
    state: RichEditorState,
    styleButtons: RowScope.(RichEditorState) -> Unit = {},
    onCheckButtonClick: ((String) -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Start,
    ) {

        TitleStyleButton(state)
        StyleButton(
            icon = R.drawable.ic_bold,
            style = TextSpanStyle.BoldStyle,
            value = state
        )

        StyleButton(
            icon = R.drawable.ic_italic,
            style = TextSpanStyle.ItalicStyle,
            value = state,
        )

        StyleButton(
            icon = R.drawable.ic_underlined,
            style = TextSpanStyle.UnderlineStyle,
            value = state,
        )

        styleButtons(state)

        if (onCheckButtonClick != null) {
            IconButton(
                modifier = Modifier
                    .padding(2.dp)
                    .size(48.dp),
                onClick = {
                    Log.d("XXX", "json ${state.output()}")
                    onCheckButtonClick(state.output())
                },
            ) {
                Icon(
                    Icons.Default.Check, contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }


    }
}

@Composable
fun StyleButton(
    @DrawableRes icon: Int,
    style: TextSpanStyle,
    value: RichEditorState,
) {
    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .size(48.dp)
            .background(
                color = if (value.hasStyle(style)) {
                    Color.Gray.copy(alpha = 0.2f)
                } else {
                    Color.Transparent
                }, shape = RoundedCornerShape(6.dp)
            ),
        onClick = {
            value.toggleStyle(style)
        },
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun TitleStyleButton(
    value: RichEditorState
) {
    var expanded by remember { mutableStateOf(false) }

    val onItemSelected = { style: TextSpanStyle ->
        value.updateStyle(style)
        expanded = false
    }

    Row {
        IconButton(
            modifier = Modifier
                .padding(2.dp)
                .size(48.dp),
            onClick = { expanded = true },
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_title), contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(),
            properties = PopupProperties(false)
        ) {

            DropDownItem(text = "Text",
                isSelected = value.hasStyle(TextSpanStyle.Default),
                onItemSelected = { onItemSelected(TextSpanStyle.Default) })
            DropDownItem(text = "Header 1", isSelected = value.hasStyle(TextSpanStyle.H1Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H1Style) })
            DropDownItem(text = "Header 2", isSelected = value.hasStyle(TextSpanStyle.H2Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H2Style) })
            DropDownItem(text = "Header 3", isSelected = value.hasStyle(TextSpanStyle.H3Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H3Style) })
            DropDownItem(text = "Header 4", isSelected = value.hasStyle(TextSpanStyle.H4Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H4Style) })
            DropDownItem(text = "Header 5", isSelected = value.hasStyle(TextSpanStyle.H5Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H5Style) })
            DropDownItem(text = "Header 6", isSelected = value.hasStyle(TextSpanStyle.H6Style),
                onItemSelected = { onItemSelected(TextSpanStyle.H6Style) })
        }
    }
}

@Composable
private fun DropDownItem(
    text: String,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {

    DropdownMenuItem(
        text = {
            Text(text = text)
        }, onClick = onItemSelected,
        modifier = Modifier.background(
            color = if (isSelected) {
                Color.Gray.copy(alpha = 0.2f)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(6.dp)
        )
    )
}
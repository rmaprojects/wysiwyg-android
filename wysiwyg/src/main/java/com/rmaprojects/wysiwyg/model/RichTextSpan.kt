package com.rmaprojects.wysiwyg.model

import com.rmaprojects.wysiwyg.utils.TextSpanStyle

data class RichTextSpan(
    val from: Int,
    val to: Int,
    val style: TextSpanStyle,
)
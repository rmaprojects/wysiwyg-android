package com.rmaprojects.wysiwyg.model

data class RichText(
    val text: String = "",
    val spans: MutableList<RichTextSpan> = mutableListOf()
)
package com.rmaprojects.wysiwyg.parser

import com.rmaprojects.wysiwyg.model.RichText

/**
 * For those who wants to make their own EditorAdapter
 */

interface EditorAdapter {
    fun encode(input: String): RichText
    fun decode(editorValue: RichText): String
}

class DefaultAdapter : EditorAdapter {
    override fun encode(input: String): RichText {
        return RichText("")
    }

    override fun decode(editorValue: RichText): String {
        return editorValue.text
    }
}
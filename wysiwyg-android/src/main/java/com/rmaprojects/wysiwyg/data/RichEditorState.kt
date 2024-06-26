package com.rmaprojects.wysiwyg.data

import com.rmaprojects.wysiwyg.model.RichText
import com.rmaprojects.wysiwyg.parser.DefaultAdapter
import com.rmaprojects.wysiwyg.parser.EditorAdapter
import com.rmaprojects.wysiwyg.utils.TextSpanStyle

class RichEditorState internal constructor(
    private val input: String,
    private val adapter: EditorAdapter = DefaultAdapter(),
) {

    internal var manager: RichTextManager

    init {
        manager = RichTextManager(getRichText())
    }

    private fun getRichText(): RichText {
        return if (input.isNotEmpty()) adapter.encode(input) else RichText()
    }

    fun output(): String {
        return adapter.decode(manager.richText)
    }

    fun reset() {
        manager.reset()
    }

    fun hasStyle(style: TextSpanStyle) = manager.hasStyle(style)

    fun toggleStyle(style: TextSpanStyle) {
        manager.toggleStyle(style)
    }

    fun updateStyle(style: TextSpanStyle) {
        manager.setStyle(style)
    }

    class Builder {
        private var adapter: EditorAdapter = DefaultAdapter()
        private var input: String = ""

        fun setInput(input: String) = apply {
            this.input = input
        }

        fun adapter(adapter: EditorAdapter) = apply {
            this.adapter = adapter
        }

        fun build(): RichEditorState {
            return RichEditorState(input, adapter)
        }
    }
}



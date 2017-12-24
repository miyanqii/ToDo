package jp.miyanqii.todo.util

import android.view.View
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by shuheimiyaki on 2017/12/23.
 */
fun LocalDateTime.toStringForDisplay(): String {
    return this.format(DateTimeFormatter.ofLocalizedDateTime(org.threeten.bp.format.FormatStyle.SHORT))
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}
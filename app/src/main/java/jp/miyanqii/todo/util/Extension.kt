package jp.miyanqii.todo.util

import android.text.format.DateUtils
import android.view.View
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

/**
 * Created by shuheimiyaki on 2017/12/23.
 */
fun LocalDateTime.toStringForDisplay(): String {
    return DateUtils.getRelativeTimeSpanString(this.toInstant(ZoneId.systemDefault().rules.getOffset(Instant.EPOCH)).toEpochMilli()).toString()
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
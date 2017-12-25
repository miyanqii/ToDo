package jp.miyanqii.todo.model.entity

import android.support.annotation.Nullable
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import jp.miyanqii.todo.util.toStringForDisplay
import org.threeten.bp.LocalDateTime

/**
 * Created by miyaki on 2017/12/21.
 */
@Table
data class Task(

        @Setter("id")
        @PrimaryKey(autoincrement = true)
        val id: Int,

        @Setter("title")
        @Column
        var title: String = "",

        @Setter("memo")
        @Column
        var memo: String = "",

        @Setter("createdDateTime")
        @Column
        @Nullable
        var createdDateTime: LocalDateTime? = null,

        @Setter("deadlineDateTime")
        @Column
        @Nullable
        var deadlineDateTime: LocalDateTime? = null,

        @Setter("finishedDateTime")
        @Column
        @Nullable
        var finishedDateTime: LocalDateTime? = null) {

    fun setFinished(finished: Boolean) {
        finishedDateTime = if (finished) LocalDateTime.now() else null
    }

    fun isFinished(): Boolean {
        return finishedDateTime != null
    }
    fun hasMemo(): Boolean = memo.isNullOrEmpty().not()

    fun hasDeadline(): Boolean {
        return deadlineDateTime != null
    }

    fun getCreatedDateTimeForDisplay(): String? {
        return createdDateTime?.toStringForDisplay()
    }

    fun getDeadlineDateTimeForDisplay(): String? {
        return if (hasDeadline()) deadlineDateTime?.toStringForDisplay() + "まで" else null
    }

    fun getFinishedDateTimeForDisplay(): String? {
        return finishedDateTime?.toStringForDisplay()
    }
}

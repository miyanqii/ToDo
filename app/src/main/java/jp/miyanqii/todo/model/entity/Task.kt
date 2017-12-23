package jp.miyanqii.todo.model.entity

import android.support.annotation.Nullable
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
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

    fun isFinished(): Boolean = finishedDateTime == null
    fun hasMemo(): Boolean = memo.isNullOrEmpty().not()
}

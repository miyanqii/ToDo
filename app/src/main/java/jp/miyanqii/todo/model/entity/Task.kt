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
class Task(
        @Setter("id")
        @PrimaryKey(autoincrement = true)
        val id: Int,

        @Setter("title")
        @Column
        val title: String = "",

        @Setter("memo")
        @Column
        val memo: String = "",

        @Setter("createdDateTime")
        @Column
        @Nullable
        val createdDateTime: LocalDateTime?,

        @Setter("deadlineDateTime")
        @Column
        @Nullable
        val deadlineDateTime: LocalDateTime?,

        @Setter("finishedDateTime")
        @Column
        @Nullable
        val finishedDateTime: LocalDateTime?) {

    fun isFinished(): Boolean = finishedDateTime == null
}

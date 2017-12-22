package jp.miyanqii.todo.model.repository

import com.github.gfx.android.orma.Inserter
import io.reactivex.Observable
import io.reactivex.Single
import jp.miyanqii.todo.model.entity.Task

/**
 * Created by miyaki on 2017/12/21.
 */
interface TaskRepository {
    fun add(): Single<Inserter<Task>>
    fun fetchAll(): Observable<Task>
    fun edit(task: Task): Single<Int>
    fun toBeDone(task: Task): Single<Int>
    fun toBeUnDone(task: Task): Single<Int>
    fun remove(task: Task): Single<Int>
    fun removeAll(): Single<Int>
}
package jp.miyanqii.todo.model.repository

import io.reactivex.Completable
import io.reactivex.Observable
import jp.miyanqii.todo.model.entity.Task

/**
 * Created by miyaki on 2017/12/21.
 */
interface TaskRepository {

    fun add(task: Task): Completable
    fun fetchAll() : Observable<List<Task>>
    fun edit(task: Task): Completable
    fun toBeDone(task: Task): Completable
    fun toBeUnDone(task: Task): Completable
    fun remove(task: Task): Completable
    fun removeAll(): Completable
}
package jp.miyanqii.todo.model.repository

import io.reactivex.Completable
import io.reactivex.Observable
import jp.miyanqii.todo.model.entity.Task

/**
 * Created by miyaki on 2017/12/21.
 */
class TaskRepositoryImpl :TaskRepository {
    override fun add(task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchAll(): Observable<List<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun edit(task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toBeDone(task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toBeUnDone(task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
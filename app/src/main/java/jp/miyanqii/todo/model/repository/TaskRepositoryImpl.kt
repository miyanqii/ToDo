package jp.miyanqii.todo.model.repository

import com.github.gfx.android.orma.Inserter
import io.reactivex.Observable
import io.reactivex.Single
import jp.miyanqii.todo.model.database.OrmaHolder
import jp.miyanqii.todo.model.entity.Task
import org.threeten.bp.LocalDateTime

object TaskRepositoryImpl : TaskRepository {

    override fun add(): Single<Inserter<Task>> = OrmaHolder.ORMA
            .prepareInsertIntoTaskAsSingle()

    override fun fetchAll(): Observable<Task> = OrmaHolder.ORMA
            .selectFromTask()
            .executeAsObservable()

    override fun edit(task: Task): Single<Int> = OrmaHolder.ORMA
            .updateTask()
            .idEq(task.id)
            .title(task.title)
            .memo(task.memo)
            .createdDateTime(task.createdDateTime)
            .deadlineDateTime(task.deadlineDateTime)
            .finishedDateTime(task.finishedDateTime)
            .executeAsSingle()


    override fun toBeDone(task: Task): Single<Int> = OrmaHolder.ORMA
            .updateTask()
            .idEq(task.id)
            .finishedDateTime(LocalDateTime.now())
            .executeAsSingle()

    override fun toBeUnDone(task: Task): Single<Int> = OrmaHolder.ORMA
            .updateTask()
            .idEq(task.id)
            .finishedDateTime(null)
            .executeAsSingle()

    override fun remove(task: Task): Single<Int> = OrmaHolder.ORMA
            .deleteFromTask()
            .idEq(task.id)
            .executeAsSingle()

    override fun removeAll(): Single<Int> = OrmaHolder.ORMA
            .deleteFromTask()
            .executeAsSingle()

}
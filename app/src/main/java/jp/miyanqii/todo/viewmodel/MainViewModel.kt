package jp.miyanqii.todo.viewmodel

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.usecase.*
import org.threeten.bp.LocalDateTime

/**
 * Created by miyaki on 2017/12/21.
 */
class MainViewModel(val callback: Callback) {

    lateinit var fetchAllTaskUseCase: FetchAllTaskUseCase
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var editTaskUseCase: EditTaskUseCase
    lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    fun onResume() {
        onRefreshAll()
    }

    fun onPause() {
        //TODO Dispose
    }

    fun onFabClick(view: View) {
        callback.onStartCreateTask()
    }

    fun onFilterClick(view: View) {
        callback.onInputCancel()
    }

    fun onAddTask(task: Task) {
        createTaskUseCase = CreateTaskUseCase()
        createTaskUseCase.createTask()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { inserter ->
                    task.createdDateTime = LocalDateTime.now()
                    inserter.execute(task)
                    onRefreshAll()
                }
    }

    fun onToBeDone(task: Task) {
        editTaskUseCase = EditTaskUseCase()
        editTaskUseCase.toBeDone(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { callback.onActionCompleted("Done!") }
                .subscribe { _ -> onRefreshAll() }
    }

    fun onToBeUnDone(task: Task) {
        editTaskUseCase = EditTaskUseCase()
        editTaskUseCase.toBeUnDone(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { }
                .subscribe { _ -> onRefreshAll() }

    }

    fun onItemEdit(task: Task) {
        editTaskUseCase = EditTaskUseCase()
        editTaskUseCase.edit(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> onRefreshAll() }
    }

    fun onItemDelete(task: Task) {
        deleteTaskUseCase = DeleteTaskUseCase()
        deleteTaskUseCase.delete(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { callback.onActionCompleted("削除しました") }
                .subscribe { _ -> onRefreshAll() }
    }

    fun onDeleteAll() { //TODO rename?
        deleteAllTaskUseCase = DeleteAllTaskUseCase()
        deleteAllTaskUseCase.deleteAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { callback.onActionCompleted("削除しました") }
                .subscribe { _ -> onRefreshAll() }
    }

    private fun onRefreshAll() {
        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fetched -> callback.onListUpdated(fetched) }//TODO Disposable
    }

    interface Callback {
        fun onListUpdated(tasks: List<Task>)
        fun onStartCreateTask()
        fun onActionCompleted(message: String, undoActionName: String = "")
        fun onInputCancel()
    }
}
package jp.miyanqii.todo.viewmodel

import android.databinding.ObservableField
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.usecase.*

/**
 * Created by miyaki on 2017/12/21.
 */
class MainViewModel(val callback: Callback) {

    lateinit var fetchAllTaskUseCase: FetchAllTaskUseCase
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var editTaskUseCase: EditTaskUseCase
    lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    var tasks: ObservableField<String> = ObservableField("default")

    fun onFabClick(view: View) { //TODO Singleで返したいかも
        callback.onStartCreateTask()
    }

    fun addTask(task: Task) {
        createTaskUseCase = CreateTaskUseCase()
        createTaskUseCase.createTask()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { inserter ->
                    inserter.execute(task)
                    refreshAll()
                }
    }

    fun onResume() {
        refreshAll()
    }

    fun onPause() {
        //TODO Dispose
    }

    private fun refreshAll() {
        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fetched ->
                    tasks.set(fetched.toString())
                    callback.onListUpdated(fetched)
                }//TODO Disposable
    }

    fun deleteAll() {
        deleteAllTaskUseCase = DeleteAllTaskUseCase()
        deleteAllTaskUseCase.deleteAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    callback.displayCompleted("削除しました")
                    refreshAll()
                }
    }

    interface Callback {
        fun onListUpdated(tasks: List<Task>)
        fun onStartCreateTask()
        fun displayCompleted(message: String)
    }

    fun onItemDelete(task: Task) {
        deleteTaskUseCase = DeleteTaskUseCase()
        deleteTaskUseCase.delete(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> refreshAll() }
    }

    fun onItemEdit(task: Task) {
        editTaskUseCase = EditTaskUseCase()
        editTaskUseCase.edit(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> refreshAll() }
    }
}
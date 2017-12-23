package jp.miyanqii.todo.viewmodel

import android.databinding.ObservableField
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.usecase.CreateTaskUseCase
import jp.miyanqii.todo.model.usecase.DeleteAllTaskUseCase
import jp.miyanqii.todo.model.usecase.DeleteTaskUseCase
import jp.miyanqii.todo.model.usecase.FetchAllTaskUseCase

/**
 * Created by miyaki on 2017/12/21.
 */
class MainViewModel(val callback: Callback) {

    lateinit var fetchAllTaskUseCase: FetchAllTaskUseCase
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    var tasks: ObservableField<String> = ObservableField("default")

    fun onFabClick(view: View) { //TODO Singleで返したいかも
        createTaskUseCase = CreateTaskUseCase()
        createTaskUseCase.createTask(Task(id = 0, title = "Test", memo = "TestMemo"))

        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fetched ->
                    tasks.set(fetched.toString())
                    callback.onListUpdated(fetched)

                }//TODO Disposable
    }

    fun onResume() {
        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { fetched ->
                    tasks.set(fetched.toString())
                    callback.onListUpdated(fetched)
                }//TODO Disposable
    }

    fun onPause() {
        //TODO Dispose
    }

    fun onDeleteAllClick() {
        deleteAllTaskUseCase = DeleteAllTaskUseCase()
        deleteAllTaskUseCase.deleteAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->

                    fetchAllTaskUseCase = FetchAllTaskUseCase()
                    fetchAllTaskUseCase.fetchAll()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { fetched ->
                                tasks.set(fetched.toString())
                                callback.onListUpdated(fetched)
                            }//TODO Disposable
                }


    }

    interface Callback {
        fun onListUpdated(tasks: List<Task>)
    }

    fun onItemSelected(task: Task) {
        deleteTaskUseCase = DeleteTaskUseCase()
        deleteTaskUseCase.delete(task)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->

                    fetchAllTaskUseCase = FetchAllTaskUseCase()
                    fetchAllTaskUseCase.fetchAll()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { fetched ->
                                tasks.set(fetched.toString())
                                callback.onListUpdated(fetched)
                            }//TODO Disposable
                }
    }
}
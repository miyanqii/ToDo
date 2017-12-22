package jp.miyanqii.todo.viewmodel

import android.databinding.ObservableField
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.usecase.CreateTaskUseCase
import jp.miyanqii.todo.model.usecase.DeleteAllTaskUseCase
import jp.miyanqii.todo.model.usecase.FetchAllTaskUseCase

/**
 * Created by miyaki on 2017/12/21.
 */
class MainViewModel {

    lateinit var fetchAllTaskUseCase: FetchAllTaskUseCase
    lateinit var createTaskUseCase: CreateTaskUseCase
    lateinit var deleteAllTaskUseCase: DeleteAllTaskUseCase

    var tasks: ObservableField<String> = ObservableField("default")

    fun onFabClick(view: View) {
        createTaskUseCase = CreateTaskUseCase()
        createTaskUseCase.createTask(Task(id = 0, title = "Test", memo = "TestMemo"))

        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskList ->
                    tasks.set(taskList.toString())
                }//TODO Disposable
    }

    fun onResume() {
        fetchAllTaskUseCase = FetchAllTaskUseCase()
        fetchAllTaskUseCase.fetchAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskList ->
                    tasks.set(taskList.toString())
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
                            .subscribe { taskList ->
                                tasks.set(taskList.toString())
                            }//TODO Disposable
                }


    }
}
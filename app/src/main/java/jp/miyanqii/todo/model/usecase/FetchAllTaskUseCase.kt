package jp.miyanqii.todo.model.usecase

import android.util.Log
import io.reactivex.Single
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.repository.TaskRepository
import jp.miyanqii.todo.model.repository.TaskRepositoryImpl

/**
 * Created by shuheimiyaki on 2017/12/21.
 */
class FetchAllTaskUseCase {
    fun fetchAll(): Single<List<Task>> {
        val taskRepository: TaskRepository = TaskRepositoryImpl() //TODO DI
        return taskRepository
                .fetchAll()
                .doOnNext { task -> Log.d("FetchAllTaskUseCase", task.toString()) }
                .toList()
    }
}
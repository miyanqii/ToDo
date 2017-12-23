package jp.miyanqii.todo.model.usecase

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.repository.TaskRepository
import jp.miyanqii.todo.model.repository.TaskRepositoryImpl

/**
 * Created by shuheimiyaki on 2017/12/21.
 */
class CreateTaskUseCase {
    fun createTask(task: Task) {
        val taskRepository: TaskRepository = TaskRepositoryImpl //TODO DI
        taskRepository.add()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { inserter -> inserter.execute(task) }
    }
}
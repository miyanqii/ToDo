package jp.miyanqii.todo.model.usecase

import io.reactivex.Single
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.repository.TaskRepository
import jp.miyanqii.todo.model.repository.TaskRepositoryImpl

/**
 * Created by shuheimiyaki on 2017/12/23.
 */
class DeleteTaskUseCase {
    fun delete(task: Task): Single<Int> {
        val taskRepository: TaskRepository = TaskRepositoryImpl //TODO DI
        return taskRepository.remove(task)
    }
}
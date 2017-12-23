package jp.miyanqii.todo.model.usecase

import io.reactivex.Single
import jp.miyanqii.todo.model.repository.TaskRepository
import jp.miyanqii.todo.model.repository.TaskRepositoryImpl

/**
 * Created by shuheimiyaki on 2017/12/21.
 */
class DeleteAllTaskUseCase {
    fun deleteAll(): Single<Int> {
        val taskRepository: TaskRepository = TaskRepositoryImpl //TODO DI
        return taskRepository.removeAll()
    }
}
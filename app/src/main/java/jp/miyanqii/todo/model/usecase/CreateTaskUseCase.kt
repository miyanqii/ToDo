package jp.miyanqii.todo.model.usecase

import com.github.gfx.android.orma.Inserter
import io.reactivex.Single
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.model.repository.TaskRepository
import jp.miyanqii.todo.model.repository.TaskRepositoryImpl

/**
 * Created by shuheimiyaki on 2017/12/21.
 */
class CreateTaskUseCase {
    fun createTask(): Single<Inserter<Task>> {
        val taskRepository: TaskRepository = TaskRepositoryImpl //TODO DI
        return taskRepository.add()
    }
}
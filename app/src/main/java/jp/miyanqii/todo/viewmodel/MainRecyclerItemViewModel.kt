package jp.miyanqii.todo.viewmodel

import android.view.View
import jp.miyanqii.todo.model.entity.Task

/**
 * Created by shuheimiyaki on 2017/12/23.
 */
class MainRecyclerItemViewModel(val task: Task, val callback: Callback) {
    fun onItemClick(view: View) {
        callback.onItemSelected(task)
    }

    interface Callback {
        fun onItemSelected(task: Task)
    }
}
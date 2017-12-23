package jp.miyanqii.todo.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.miyanqii.todo.BR
import jp.miyanqii.todo.R
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.viewmodel.MainRecyclerItemViewModel


/**
 * Created by shuheimiyaki on 2017/12/22.
 */
class MainRecyclerViewAdapter(var tasks: List<Task>, val callback: MainRecyclerItemViewModel.Callback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val b = (holder as ViewHolder).b
        b.setVariable(BR.itemViewModel, MainRecyclerItemViewModel(tasks.get(position), callback))
        b.executePendingBindings()
    }

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.row_task, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val b: ViewDataBinding = DataBindingUtil.bind(v)
    }
}
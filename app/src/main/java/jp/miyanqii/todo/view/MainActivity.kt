package jp.miyanqii.todo.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import jp.miyanqii.todo.BR
import jp.miyanqii.todo.BuildConfig
import jp.miyanqii.todo.R
import jp.miyanqii.todo.databinding.ActivityMainBinding
import jp.miyanqii.todo.databinding.FormAddItemBinding
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.viewmodel.MainRecyclerItemViewModel
import jp.miyanqii.todo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.Callback, MainRecyclerItemViewModel.Callback {
    override fun displayCompleted(message: String) {
        Snackbar.make(b.toolbar, message, Snackbar.LENGTH_SHORT).show()
    }

    lateinit var b: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var mainRecyclerViewAdapter: MainRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        b.setVariable(BR.mainViewModel, mainViewModel)
        setSupportActionBar(b.toolbar)

        b.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainRecyclerViewAdapter = MainRecyclerViewAdapter(emptyList(), this)
        b.recycler.adapter = mainRecyclerViewAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                confirmDeleteAll()
                true
            }
            R.id.action_about -> {
                displayAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.onPause()
    }

    override fun onItemSelected(task: Task) {
        Log.d(localClassName, "onItemSelected")

        val b: FormAddItemBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.form_add_item, null, false)
        b.setVariable(BR.task, task)
        AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_create_brown_500_24dp)
                .setTitle("タスクを編集")
                .setView(b.root)
                .setCancelable(true)
                .setPositiveButton("Update", { d, _ ->
                    mainViewModel.onItemEdit(task)
                    d.dismiss()
                })
                .setNegativeButton("Delete", { d, _ ->
                    mainViewModel.onItemDelete(task)
                    d.dismiss()
                })
                .setNeutralButton("閉じる", { d, _ ->
                    d.dismiss()
                })
                .create()
                .show()
    }

    override fun onListUpdated(tasks: List<Task>) {
        Log.d(localClassName, "onListUpdated")
        mainRecyclerViewAdapter.tasks = tasks
        mainRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onStartCreateTask() {
        val b: FormAddItemBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.form_add_item, null, false)

        val task = Task(
                id = 0,
                title = b.inputTitle.text.toString(),
                memo = b.inputMemo.text.toString())

        b.setVariable(BR.task, task)

        AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_create_brown_500_24dp)
                .setTitle("タスクを登録")
                .setView(b.root)
                .setCancelable(true)
                .setPositiveButton("Add", { d, _ ->
                    b.task?.let { mainViewModel.addTask(it) }
                    d.dismiss()
                })
                .create().show()
    }

    fun displayAbout() {
        AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(BuildConfig.VERSION_NAME)
                .setCancelable(true)
                .setNeutralButton("閉じる", { d, _ -> d.dismiss() })
                .create()
                .show()
    }

    fun confirmDeleteAll() {
        AlertDialog.Builder(this)
                .setMessage("全てのタスクを削除しますか？")
                .setCancelable(true)
                .setPositiveButton("全て削除", { d, _ ->
                    mainViewModel.deleteAll()
                    d.dismiss()
                })
                .setNegativeButton("やめる", { d, _ ->
                    d.dismiss()
                })
                .create()
                .show()
    }
}

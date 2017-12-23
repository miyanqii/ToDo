package jp.miyanqii.todo.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import jp.miyanqii.todo.BR
import jp.miyanqii.todo.R
import jp.miyanqii.todo.databinding.ActivityMainBinding
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.viewmodel.MainRecyclerItemViewModel
import jp.miyanqii.todo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.Callback, MainRecyclerItemViewModel.Callback {

    lateinit var b: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel

    lateinit var mainRecyclerViewAdapter: MainRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        b.setVariable(BR.mainViewModel, mainViewModel)
        setSupportActionBar(b.toolbar)

        b.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        mainRecyclerViewAdapter = MainRecyclerViewAdapter(emptyList(), this)
        b.recycler.adapter = mainRecyclerViewAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                mainViewModel.onDeleteAllClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel?.onPause()
    }

    override fun onItemSelected(task: Task) {
        Log.d(localClassName, "onItemSelected")
        mainViewModel.onItemSelected(task)

    }

    override fun onListUpdated(tasks: List<Task>) {
        Log.d(localClassName, "onListUpdated")
        mainRecyclerViewAdapter.tasks = tasks
        mainRecyclerViewAdapter.notifyDataSetChanged()
        Snackbar.make(b.toolbar, "onListUpdated", Snackbar.LENGTH_SHORT).show()
    }

}

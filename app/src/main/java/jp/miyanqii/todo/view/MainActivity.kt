package jp.miyanqii.todo.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import jp.miyanqii.todo.BR
import jp.miyanqii.todo.BuildConfig
import jp.miyanqii.todo.R
import jp.miyanqii.todo.databinding.ActivityMainBinding
import jp.miyanqii.todo.databinding.DialogEditTaskBinding
import jp.miyanqii.todo.model.entity.Task
import jp.miyanqii.todo.util.toGone
import jp.miyanqii.todo.util.toVisible
import jp.miyanqii.todo.viewmodel.MainRecyclerItemViewModel
import jp.miyanqii.todo.viewmodel.MainViewModel
import org.threeten.bp.LocalDateTime


class MainActivity : AppCompatActivity(),
        MainViewModel.Callback,
        MainRecyclerItemViewModel.Callback {

    companion object {
        val STATE_INPUT_MODE: String = "STATE_INPUT_MODE"
        val STATE_TEMP_INPUT_TEXT: String = "STATE_TEMP_INPUT_TEXT"
    }

    lateinit var b: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var mainRecyclerViewAdapter: MainRecyclerViewAdapter
    private var inputMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this)
        b.setVariable(BR.mainViewModel, mainViewModel)
        setSupportActionBar(b.toolbar)

        b.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainRecyclerViewAdapter = MainRecyclerViewAdapter(emptyList(), this)
        b.recycler.adapter = mainRecyclerViewAdapter

        b.input.setOnEditorActionListener({ textView, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val text = textView.text.toString()
                    if (text.isNotBlank()) {
                        mainViewModel.onAddTask(Task(id = 0, title = text, createdDateTime = LocalDateTime.now()))
                    }
                    onInputCancel()
                    true
                }
                else -> false
            }
        })

        if (savedInstanceState != null) {
            inputMode = savedInstanceState?.getBoolean(STATE_INPUT_MODE)
            b.input.setText(savedInstanceState?.getString(STATE_TEMP_INPUT_TEXT))
        }
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(STATE_INPUT_MODE, inputMode)
        outState?.putString(STATE_TEMP_INPUT_TEXT, b.input.text.toString())
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onResume()

        if (inputMode) {
            onStartCreateTask()
        }
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.onPause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (inputMode) {
                    onInputCancel()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onItemSelected(task: Task) {
        Log.d(localClassName, "onItemSelected")

        val b: DialogEditTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_edit_task, null, false)
        b.setVariable(BR.task, task)
        AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_create_brown_500_24dp)
                .setTitle(getString(R.string.dialog_edit_task_title))
                .setView(b.root)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.update_this), { d, _ ->
                    mainViewModel.onItemEdit(task)
                    d.dismiss()
                })
                .setNegativeButton(getString(R.string.delete_this_task), { d, _ ->
                    showDeleteConfirmation(task)
                    d.dismiss()
                })
                .setNeutralButton(getString(R.string.close), { d, _ ->
                    d.dismiss()
                })
                .create()
                .show()
    }

    private fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_delete_red_500_24dp)
                .setTitle(getString(R.string.dialog_delete_task_title))
                .setMessage(getString(R.string.confirm_delete))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.delete), { d, _ ->
                    mainViewModel.onItemDelete(task)
                    d.dismiss()
                })
                .setNegativeButton(getString(R.string.abort), { d, _ -> d.dismiss() })
                .create()
                .show()
    }

    override fun onToBeDoneClick(task: Task) {
        Snackbar.make(b.toolbar, getString(R.string.done), Snackbar.LENGTH_SHORT).show()
        mainViewModel.onToBeDone(task)
    }

    override fun onToBeUndoneClick(task: Task) {
        mainViewModel.onToBeUnDone(task)
    }

    override fun onListUpdated(tasks: List<Task>) {
        Log.d(localClassName, "onListUpdated")
        mainRecyclerViewAdapter.tasks = tasks
        mainRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onStartCreateTask() {

        inputMode = true

        b.inputBlock.toVisible()
        b.darkFilter.toVisible()
        b.fab.toGone()

        b.inputLayout.apply {
            requestFocus()
            performClick()
        }

        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(b.input, InputMethodManager.SHOW_IMPLICIT)

    }


    override fun onInputCancel() {

        b.inputBlock.toGone()
        b.darkFilter.toGone()
        b.fab.toVisible()

        b.input.setText("")

        if (currentFocus != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        inputMode = false
    }

    override fun onActionCompleted(message: String) {
        Snackbar.make(b.toolbar, message, Snackbar.LENGTH_SHORT).show()
    }

    fun displayAbout() {
        AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(BuildConfig.VERSION_NAME)
                .setCancelable(true)
                .setNeutralButton(getString(R.string.close), { d, _ -> d.dismiss() })
                .create()
                .show()
    }

    fun confirmDeleteAll() {
        if (mainRecyclerViewAdapter.tasks.isEmpty().not()) {
            AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_delete_red_500_24dp)
                    .setTitle(getString(R.string.dialog_delete_all_item_confirm_title))
                    .setMessage(getString(R.string.delete_all_confirm))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.delete_all), { d, _ ->
                        mainViewModel.onDeleteAll()
                        d.dismiss()
                    })
                    .setNegativeButton(getString(R.string.abort), { d, _ -> d.dismiss() })
                    .create()
                    .show()
        }
    }
}

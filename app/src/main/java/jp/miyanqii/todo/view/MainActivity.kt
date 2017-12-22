package jp.miyanqii.todo.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import jp.miyanqii.todo.BR
import jp.miyanqii.todo.R
import jp.miyanqii.todo.databinding.ActivityMainBinding
import jp.miyanqii.todo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var b: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel()
        b.setVariable(BR.mainViewModel, mainViewModel)
        setSupportActionBar(b.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
}

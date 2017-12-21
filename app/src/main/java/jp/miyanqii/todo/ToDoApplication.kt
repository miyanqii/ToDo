package jp.miyanqii.todo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import jp.miyanqii.todo.model.database.OrmaHolder

/**
 * Created by miyaki on 2017/12/21.
 */
class ToDoApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        OrmaHolder.initialize(this)
        AndroidThreeTen.init(this)
    }
}
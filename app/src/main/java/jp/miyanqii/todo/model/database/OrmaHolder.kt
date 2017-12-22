package jp.miyanqii.todo.model.database

import android.content.Context
import jp.miyanqii.todo.model.entity.OrmaDatabase

/**
 * Created by miyaki on 2017/12/21.
 */
object OrmaHolder {
    lateinit var ORMA: OrmaDatabase;

    fun initialize(context: Context) {
        ORMA = OrmaDatabase.builder(context).build();
    }
}
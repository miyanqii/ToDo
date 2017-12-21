package jp.miyanqii.todo.model.database

import android.content.Context

/**
 * Created by miyaki on 2017/12/21.
 */
object OrmaHolder {
    lateinit var ORMA: OrmaDataBase;

    fun initialize(context: Context) {
        ORMA = OrmaDatabase.builder(context).build();
    }
}
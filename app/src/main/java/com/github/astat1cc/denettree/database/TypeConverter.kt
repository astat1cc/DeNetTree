package com.github.astat1cc.denettree.database

import androidx.room.TypeConverter
import com.github.astat1cc.denettree.models.Node
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {

    @TypeConverter
    fun fromJson(nodeListJson: String): List<Node> {
        val type = object : TypeToken<List<Node>>() {}.type
        return Gson().fromJson(nodeListJson, type)
    }

    @TypeConverter
    fun toJson(nodeList: List<Node>): String {
        val type = object : TypeToken<List<Node>>() {}.type
        return Gson().toJson(nodeList, type)
    }
}
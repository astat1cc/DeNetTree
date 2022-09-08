package com.github.astat1cc.denettree.data.local

import androidx.room.TypeConverter
import com.github.astat1cc.denettree.data.local.model.NodeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {

    @TypeConverter
    fun fromJson(nodeListJson: String): List<NodeDto> {
        val type = object : TypeToken<List<NodeDto>>() {}.type
        return Gson().fromJson(nodeListJson, type)
    }

    @TypeConverter
    fun toJson(nodeList: List<NodeDto>): String {
        val type = object : TypeToken<List<NodeDto>>() {}.type
        return Gson().toJson(nodeList, type)
    }
}
package com.github.astat1cc.denettree.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.astat1cc.denettree.models.Node.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Node(
    @PrimaryKey val name: String,
    val parent: String?,
) {

    companion object {

        const val TABLE_NAME = "nodes_table"
    }
}
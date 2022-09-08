package com.github.astat1cc.denettree.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.astat1cc.denettree.data.local.model.NodeDto.Companion.TABLE_NAME
import com.github.astat1cc.denettree.domain.model.Node

@Entity(tableName = TABLE_NAME)
data class NodeDto(
    @PrimaryKey val name: String,
    val parent: String?,
) {

    fun toDomain() = Node(name, parent)

    companion object {

        const val TABLE_NAME = "nodes_table"
    }
}
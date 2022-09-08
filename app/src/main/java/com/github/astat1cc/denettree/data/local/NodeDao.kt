package com.github.astat1cc.denettree.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.astat1cc.denettree.data.local.model.NodeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {

    @Query("SELECT * FROM ${NodeDto.TABLE_NAME} WHERE parent=:parent")
    fun getChildrenOf(parent: String): Flow<List<NodeDto>>

    @Insert(entity = NodeDto::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveNode(node: NodeDto)

    @Query("SELECT * FROM ${NodeDto.TABLE_NAME} WHERE name=:name")
    suspend fun getLastOpenedNode(name: String): NodeDto
}
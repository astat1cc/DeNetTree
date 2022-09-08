package com.github.astat1cc.denettree.data.local

import androidx.room.*
import com.github.astat1cc.denettree.data.local.model.NodeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {

    @Query("SELECT * FROM ${NodeDto.TABLE_NAME} WHERE parent=:parent")
    fun getChildrenOf(parent: String): Flow<List<NodeDto>>

    @Insert(entity = NodeDto::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNode(node: NodeDto)

    @Delete(entity = NodeDto::class)
    suspend fun deleteNode(node: NodeDto)

    @Query("SELECT * FROM ${NodeDto.TABLE_NAME} WHERE name=:name")
    suspend fun getLastOpenedNode(name: String): NodeDto
}
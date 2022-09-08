package com.github.astat1cc.denettree.database

import androidx.room.*
import com.github.astat1cc.denettree.models.Node
import com.github.astat1cc.denettree.utils.Consts
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {

    @Query("SELECT * FROM ${Node.TABLE_NAME} WHERE parent=:parent")
    fun getChildrenOf(parent: String): Flow<List<Node>>

    @Insert(entity = Node::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveNode(node: Node)

    @Query("SELECT * FROM ${Node.TABLE_NAME} WHERE name=:name")
    suspend fun getLastOpenedNode(name: String): Node
}
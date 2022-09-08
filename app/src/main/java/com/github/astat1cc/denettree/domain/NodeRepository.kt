package com.github.astat1cc.denettree.domain

import com.github.astat1cc.denettree.domain.model.Node
import kotlinx.coroutines.flow.Flow

interface NodeRepository {

    fun getChildrenOf(parentName: String): Flow<List<Node>>

    suspend fun addNodeWith(parentName: String)

    suspend fun getLastOpenedNode(): Node

    fun saveLastOpenedNode(nodeName: String)

    suspend fun deleteNode(node: Node)
}
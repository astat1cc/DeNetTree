package com.github.astat1cc.denettree.domain

import com.github.astat1cc.denettree.domain.model.Node
import kotlinx.coroutines.flow.Flow

interface NodeInteractor {

    fun getChildrenOf(parentName: String): Flow<List<Node>>

    suspend fun addNodeWith(parentName: String)

    suspend fun getLastOpenedNode(): Node

    fun saveLastOpenedNode(nodeName: String)

    suspend fun deleteNode(node: Node)

    class Impl(private val repository: NodeRepository) : NodeInteractor {

        override suspend fun getLastOpenedNode(): Node =
            repository.getLastOpenedNode()

        override fun getChildrenOf(parentName: String): Flow<List<Node>> =
            repository.getChildrenOf(parentName)

        override suspend fun addNodeWith(parentName: String) =
            repository.addNodeWith(parentName)

        override fun saveLastOpenedNode(nodeName: String) =
            repository.saveLastOpenedNode(nodeName)

        override suspend fun deleteNode(node: Node) =
            repository.deleteNode(node)
    }
}
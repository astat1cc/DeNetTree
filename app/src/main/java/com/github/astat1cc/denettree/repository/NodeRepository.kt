package com.github.astat1cc.denettree.repository

import android.content.SharedPreferences
import com.github.astat1cc.denettree.database.NodeDao
import com.github.astat1cc.denettree.models.Node
import com.github.astat1cc.denettree.utils.Consts
import kotlinx.coroutines.flow.Flow
import org.web3j.crypto.Hash

class NodeRepository(
    private val sharedPref: SharedPreferences,
    private val dao: NodeDao
) {

    fun saveLastOpenedNode(name: String) {
        sharedPref.edit().putString(Consts.LAST_OPENED_NODE_KEY, name).commit()
    }

    private fun getLastOpenedNodeName(): String =
        sharedPref.getString(Consts.LAST_OPENED_NODE_KEY, null) ?: Consts.ROOT_NODE_NAME

    suspend fun getLastOpenedNode(): Node {
        val lastOpenedNodeName = getLastOpenedNodeName()
        return if (lastOpenedNodeName == Consts.ROOT_NODE_NAME) {
            Node(name = lastOpenedNodeName, null)
        } else {
            dao.getLastOpenedNode(name = lastOpenedNodeName)
        }
    }

    fun getChildrenOf(parent: String): Flow<List<Node>> = dao.getChildrenOf(parent)

    fun addNodeOf(parentName: String) {
        val newId = getLastNodeId() + 1
        saveNewNodeId(newId)
        val generatedName = "0x" + Hash.sha3String(newId.toString()).takeLast(40)
        dao.saveNode(Node(generatedName, parentName))
    }

    private fun saveNewNodeId(newId: Int) {
        sharedPref.edit().putInt(Consts.LAST_NODE_ID_KEY, newId).apply()
    }

    private fun getLastNodeId() =
        sharedPref.getInt(Consts.LAST_NODE_ID_KEY, Consts.ROOT_NODE_ID)
}
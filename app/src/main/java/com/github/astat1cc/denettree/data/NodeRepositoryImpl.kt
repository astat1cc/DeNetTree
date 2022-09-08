package com.github.astat1cc.denettree.data

import android.content.SharedPreferences
import com.github.astat1cc.denettree.data.local.NodeDao
import com.github.astat1cc.denettree.data.local.model.NodeDto
import com.github.astat1cc.denettree.domain.NodeRepository
import com.github.astat1cc.denettree.domain.model.Node
import com.github.astat1cc.denettree.utils.Consts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.web3j.crypto.Hash

class NodeRepositoryImpl(
    private val sharedPref: SharedPreferences,
    private val dao: NodeDao
) : NodeRepository {

    override fun saveLastOpenedNode(nodeName: String) {
        sharedPref.edit().putString(Consts.LAST_OPENED_NODE_KEY, nodeName).commit()
    }

    override suspend fun getLastOpenedNode(): Node {
        val lastOpenedNodeName = getLastOpenedNodeName()
        return if (lastOpenedNodeName == Consts.ROOT_NODE_NAME) {
            Node(name = lastOpenedNodeName, null)
        } else {
            dao.getLastOpenedNode(name = lastOpenedNodeName).toDomain()
        }
    }

    override fun getChildrenOf(parentName: String): Flow<List<Node>> =
        dao.getChildrenOf(parentName).map { emittedList ->
            emittedList.map { nodeDto -> nodeDto.toDomain() }
        }

    override fun addNodeWith(parentName: String) {
        val newId = getLastNodeId() + 1
        saveNewNodeId(newId)
        val generatedName = "0x" + Hash.sha3String(newId.toString()).takeLast(40)
        dao.saveNode(NodeDto(generatedName, parentName))
    }

    private fun getLastOpenedNodeName(): String =
        sharedPref.getString(Consts.LAST_OPENED_NODE_KEY, null) ?: Consts.ROOT_NODE_NAME

    private fun saveNewNodeId(newId: Int) {
        sharedPref.edit().putInt(Consts.LAST_NODE_ID_KEY, newId).apply()
    }

    private fun getLastNodeId() =
        sharedPref.getInt(Consts.LAST_NODE_ID_KEY, Consts.ROOT_NODE_ID)
}
package com.github.astat1cc.denettree.ui.viewmodel

import androidx.lifecycle.*
import com.github.astat1cc.denettree.models.Node
import com.github.astat1cc.denettree.repository.NodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NodeViewModel(
    private val repository: NodeRepository
) : ViewModel() {

    val currentNode: SharedFlow<Node> = flow {
        val node = repository.getLastOpenedNode()
        setChildren(node.name)
        emit(node)
    }.shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 1)

    private val _children =
        MutableSharedFlow<List<Node>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val children: SharedFlow<List<Node>> = _children.asSharedFlow()

    private fun setChildren(parentName: String) {
        viewModelScope.launch {
            _children.emitAll(repository.getChildrenOf(parentName))
        }
    }

    fun addNode() = viewModelScope.launch(Dispatchers.IO) {
        repository.addNodeOf(parentName = currentNode.replayCache.last().name)
    }

    fun changeOpenedNode(selectedNodeName: String) {
        repository.saveLastOpenedNode(selectedNodeName)
    }
}
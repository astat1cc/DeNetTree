package com.github.astat1cc.denettree.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.astat1cc.denettree.domain.NodeInteractor
import com.github.astat1cc.denettree.domain.model.Node
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NodeViewModel(
    private val interactor: NodeInteractor
) : ViewModel() {

    val currentNode: SharedFlow<Node> = flow {
        val node = interactor.getLastOpenedNode()
        setChildren(node.name)
        emit(node)
    }.shareIn(viewModelScope, started = SharingStarted.Lazily, replay = 1)

    private val _children =
        MutableSharedFlow<List<Node>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val children: SharedFlow<List<Node>> = _children.asSharedFlow()

    private fun setChildren(parentName: String) {
        viewModelScope.launch {
            _children.emitAll(interactor.getChildrenOf(parentName))
        }
    }

    fun addNode() = viewModelScope.launch(Dispatchers.IO) {
        interactor.addNodeWith(parentName = currentNode.replayCache.last().name)
    }

    fun changeOpenedNode(node: String) {
        interactor.saveLastOpenedNode(node)
    }

    fun deleteNode(node: Node) = viewModelScope.launch(Dispatchers.IO) {
        interactor.deleteNode(node)
    }
}
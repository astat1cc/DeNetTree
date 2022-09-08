package com.github.astat1cc.denettree.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.astat1cc.denettree.R
import com.github.astat1cc.denettree.databinding.ItemNodeBinding
import com.github.astat1cc.denettree.domain.model.Node
import com.github.astat1cc.denettree.utils.AppResourceProvider

class NodeAdapter(
    private val resourceProvider: AppResourceProvider,
    private val onItemClickListener: (String) -> Unit,
    private val onItemDeleteListener: (Node) -> Unit
) : RecyclerView.Adapter<NodeAdapter.NodeViewHolder>() {

    var currentList: List<Node> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }

    private val diffCallback = object : DiffUtil.ItemCallback<Node>() {
        override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean =
            oldItem == newItem
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNodeBinding.inflate(inflater, parent, false)
        return NodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    inner class NodeViewHolder(binding: ItemNodeBinding) : RecyclerView.ViewHolder(binding.root) {

        private val parent = binding.parentTV
        private val name = binding.nameTV
        private val delete = binding.delete

        fun bind(node: Node) {
            parent.text = resourceProvider.getString(R.string.parent_template, node.parent ?: "")
            name.text = resourceProvider.getString(R.string.name_template, node.name)

            itemView.setOnClickListener { onItemClickListener(node.name) }
            delete.setOnClickListener { onItemDeleteListener(node) }
        }
    }
}
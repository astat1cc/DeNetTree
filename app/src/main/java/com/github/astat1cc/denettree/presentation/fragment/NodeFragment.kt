package com.github.astat1cc.denettree.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.astat1cc.denettree.R
import com.github.astat1cc.denettree.databinding.FragmentNodeBinding
import com.github.astat1cc.denettree.presentation.recyclerview.NodeAdapter
import com.github.astat1cc.denettree.presentation.viewmodel.NodeViewModel
import com.github.astat1cc.denettree.utils.AppResourceProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NodeFragment : Fragment() {

    private lateinit var binding: FragmentNodeBinding

    private val viewModel by viewModel<NodeViewModel>()

    private val resourceProvider by inject<AppResourceProvider>()

    private val adapter by lazy {
        NodeAdapter(
            resourceProvider,
            onItemClickListener = { selectedNodeName ->
                navigateTo(selectedNodeName, enterAnimation = true)
            },
            onItemDeleteListener = { node ->
                viewModel.deleteNode(node)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        setupRecyclerView()
        setOnClickListeners()
        overrideOnBackPressed()
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            customNavigateUp()
        }
    }

    private var nameOfParentOfCurrentNode: String? = null
    private fun customNavigateUp() {
        nameOfParentOfCurrentNode?.let {
            if (nameOfParentOfCurrentNode!!.isNotEmpty()) {
                navigateTo(nameOfParentOfCurrentNode!!, enterAnimation = false)
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.addChildButton.setOnClickListener {
            viewModel.addNode()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = this.adapter
    }

    private fun observe() {
        with(lifecycleScope) {
            launchWhenStarted {
                viewModel.children.collect {
                    adapter.currentList = it
                }
            }
            launchWhenStarted {
                viewModel.currentNode.collect {
                    binding.toolbar.title = it.name
                    if (it.parent != null) {
                        showNavigateBackButton()
                        nameOfParentOfCurrentNode = it.parent
                    } else {
                        nameOfParentOfCurrentNode = ""
                    }
                }
            }
        }
    }

    private fun showNavigateBackButton() {
        with(binding.toolbar) {
            setNavigationIcon(R.drawable.ic_arrow_back_ios)
            setNavigationOnClickListener { customNavigateUp() }
            navigationIcon?.setTint(Color.BLACK)
        }
    }

    private fun navigateTo(nodeName: String, enterAnimation: Boolean) {
        viewModel.changeOpenedNode(nodeName)
        val transaction = parentFragmentManager.beginTransaction()
        if (enterAnimation) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        transaction.replace(R.id.fragmentContainer, NodeFragment()).commit()
    }
}
package com.rss.arglink.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rss.arglink.R
import com.rss.arglink.databinding.FragmentLinkItemListBinding
import com.rss.arglink.ui.adapter.LinkItemListAdapter
import com.rss.arglink.utils.isConnectedToNetwork
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LinkItemListFragment : Fragment() {

    private lateinit var binding: FragmentLinkItemListBinding
    private lateinit var linkItemAdapter: LinkItemListAdapter
    private val viewModel: LinkItemListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLinkItemListBinding.inflate(inflater, container, false)
        linkItemAdapter = LinkItemListAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collectLatest { viewState ->
                    when (viewState) {
                        is LinkItemListViewState.NoData -> {
                            binding.list.visibility = View.GONE
                            binding.loadingIndicator.visibility = View.GONE
                            binding.infoMessage.text = getString(R.string.no_data_message)
                            binding.infoMessage.visibility = View.VISIBLE
                        }
                        is LinkItemListViewState.Success -> {
                            binding.loadingIndicator.visibility = View.GONE
                            binding.infoMessage.visibility = View.GONE
                            linkItemAdapter.submitList(viewState.linkItemList)
                            binding.list.visibility = View.VISIBLE
                        }
                        is LinkItemListViewState.Error -> {
                            binding.list.visibility = View.GONE
                            binding.loadingIndicator.visibility = View.GONE
                            binding.infoMessage.text = getString(R.string.error_message)
                            binding.infoMessage.visibility = View.VISIBLE
                        }
                        is LinkItemListViewState.Loading -> {
                            binding.infoMessage.visibility = View.GONE
                            binding.loadingIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = linkItemAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val drawableLeft = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (binding.search.right - binding.search.compoundDrawables[drawableLeft].bounds.width())) {
                        binding.search.text.clear()
                        return true
                    }
                }
                return false
            }
        })

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.trim().length >= 2) {
                    if (isConnectedToNetwork(context)) {
                        viewModel.searchData(text.toString())
                    } else {
                        Toast.makeText(context, getString(R.string.no_network_message), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun afterTextChanged(text: Editable?) {}
        })
    }
}
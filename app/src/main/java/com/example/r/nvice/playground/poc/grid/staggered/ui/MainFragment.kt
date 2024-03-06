package com.example.r.nvice.playground.poc.grid.staggered.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.r.nvice.playground.poc.grid.staggered.databinding.FragmentMainBinding
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.adapter.MainListAdapter
import com.example.r.nvice.playground.poc.grid.staggered.ui.widget.decoration.GridItemDecoration
import com.example.r.nvice.playground.poc.grid.staggered.util.getItemVisibility


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var listAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        listAdapter = MainListAdapter().apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        initObserver()
        initList()
    }

    private fun initView() {
        with(binding.rvList) {
            adapter = listAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            itemAnimator = object : DefaultItemAnimator() {
                override fun canReuseUpdatedViewHolder(
                    viewHolder: RecyclerView.ViewHolder,
                    payloads: MutableList<Any>
                ): Boolean {
                    return true
                }
            }
            addItemDecoration(
                GridItemDecoration(16)
            )
        }
    }

    private fun initEvent() {
        with(binding) {
            vRefresh.setOnRefreshListener {
                viewModel.refresh()
            }
            rvList.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                    val firstVisibleItemPositions =
                        layoutManager.findFirstVisibleItemPositions(null)
                    val firstVisibleCompletelyItemPositions =
                        layoutManager.findFirstCompletelyVisibleItemPositions(null)
                    val lastVisibleItemPosition = layoutManager
                        .findLastVisibleItemPositions(null)
                    val lastCompletelyVisibleItemPosition = layoutManager
                        .findLastCompletelyVisibleItemPositions(null)

                    with(viewModel) {
                        updateItemPositions(
                            firstVisibleItemPositions = firstVisibleItemPositions,
                            firstCompletelyVisibleItemPositions = firstVisibleCompletelyItemPositions,
                            lastVisibleItemPositions = lastVisibleItemPosition,
                            lastCompletelyVisibleItemPositions = lastCompletelyVisibleItemPosition
                        )
                        with(rvList) {
                            findOptimalItemPosition()?.let(::setPlayOnItem)
                            getItemVisibility(
                                position = playingItemPosition
                            )?.let(::updatePlayableVisibility)
                        }
                    }
                }
            })
        }
    }

    private fun initObserver() {
        with(viewModel) {
            items.observe(viewLifecycleOwner) {
                binding.vRefresh.isRefreshing = false
                listAdapter.submitList(it)
            }
        }
    }

    private fun initList() {
        viewModel.randomItems()
    }
}
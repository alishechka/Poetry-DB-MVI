package com.boss.poetrydb2.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils.join
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.boss.poetrydb2.R
import com.boss.poetrydb2.model.RandomPoem
import com.boss.poetrydb2.ui.state.StateEvent
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), RandomPoemAdapter.Interaction, testAdapter.Interaction {
    lateinit var viewModel: MainViewModel
    lateinit var testAdapter: testAdapter
    lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("invalid Activity")

        subscribeObservers()
//        initRandomPoemAdapter()
        initAuthorListAdapter()

    }

    private fun initRandomPoem(poem: RandomPoem) {
        random_author.text = poem.author
        random_title.text = poem.title
        poem.lines?.let {
            val i = join("\n", it)
            random_poem.text = i
        }
    }

    private fun initAuthorListAdapter() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            testAdapter = testAdapter(this@MainFragment)
            adapter = testAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            println("DEBUG: DataState: $it")

            dataStateHandler.onDataStateChanged(it)

            it.data?.let {
                it.getContentIfNotHandled()?.let {
                    it.randomPoem?.let {
                        viewModel.setRandomPoemData(it)
                    }
                    it.authorList?.let {
                        viewModel.setAuthorListData(it)
                    }
                }
            }
        }) //end of vm.dataState

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.randomPoem?.let {
                initRandomPoem(it)
            }
            it.authorList?.let {
                val list = it.authors
                testAdapter.submitList(list)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_random -> triggerGetRandomEvent()
            R.id.action_list_authors -> triggerGetListAuthorsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetListAuthorsEvent() {
        viewModel.setStateEvent(StateEvent.GetAuthorListEvent())
    }

    private fun triggerGetRandomEvent() {
        viewModel.setStateEvent(StateEvent.GetRandomPoemEvent())
    }

    override fun onItemSelected(position: Int, item: RandomPoem) {
        println("DEBUG: CLICKED $position")
        println("DEBUG: CLICKED $item")
    }

    override fun onItemSelected(position: Int, item: String) {
        println("DEBUG: CLICKED $position")
        println("DEBUG: CLICKED $item")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}
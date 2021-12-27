package com.example.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPokemonListBinding
import com.example.model.PokemonDataModel
import com.example.view.adapter.ItemsAdapter
import com.example.viewmodels.RecyclerPokemonViewModel

class PokemonListFragment : Fragment(), ClickListener {
    lateinit var viewModel: RecyclerPokemonViewModel
    lateinit var binding: FragmentPokemonListBinding
    private var mAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            activity?.let {
                ViewModelProvider(it).get(RecyclerPokemonViewModel::class.java)
            }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_list, container, false)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inicializa recycñerview
        mAdapter = ItemsAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = mAdapter

        //observador de la lista
        viewModel.listState.observe(viewLifecycleOwner) {
            mAdapter?.setItems(list = it)
            binding.progress.isInvisible = true
        }

        viewModel.progressState.observe(viewLifecycleOwner) { show ->
            binding.progress.isVisible = true

        }

        viewModel.fetchPokemonData()
    }


    override fun itemSelect(data: PokemonDataModel) {
        viewModel.setItemSelection(data)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(android.R.id.content, PokemonDetailFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }
}

interface ClickListener {
    fun itemSelect(data: PokemonDataModel)
}
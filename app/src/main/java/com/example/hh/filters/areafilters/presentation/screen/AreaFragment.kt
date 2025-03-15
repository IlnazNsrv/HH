package com.example.hh.filters.areafilters.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hh.core.presentation.AbstractBottomDialogFragment
import com.example.hh.databinding.AreaFragmentBinding

class AreaFragment() : AbstractBottomDialogFragment<AreaFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        AreaFragmentBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.areaRecyclerView

    }
}
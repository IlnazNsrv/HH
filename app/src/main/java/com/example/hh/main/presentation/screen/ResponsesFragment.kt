package com.example.hh.main.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentMenu1Binding

class ResponsesFragment : AbstractFragment<FragmentMenu1Binding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) = FragmentMenu1Binding.inflate(inflater, container, false)
}
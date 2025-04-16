package com.example.hh.main.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hh.core.presentation.AbstractFragment
import com.example.hh.databinding.FragmentMenu2Binding

class ProfileFragment: AbstractFragment<FragmentMenu2Binding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?) = FragmentMenu2Binding.inflate(inflater, container, false)
}
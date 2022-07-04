package com.application.pepul.ui.fragment

import android.os.Bundle
import android.view.View
import com.application.pepul.R
import com.application.pepul.databinding.FragmentHomeBinding
import com.application.pepul.util.viewBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
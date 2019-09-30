package com.example.mdm_everis.home.dispositivos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf

import com.example.mdm_everis.R

class DispositivosFragment : Fragment() {

    companion object{
        fun setArguments() = bundleOf()
    }

    private lateinit var viewModel: DispositivosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dispositivos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DispositivosViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

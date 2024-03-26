package com.example.pbfragmentsharedelement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.OneShotPreDrawListener
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val box = view.findViewById<View>(R.id.box)
        val extras = FragmentNavigatorExtras(box to "second_fragment_box")
        box.setOnClickListener {
            findNavController().navigate(
                R.id.action_firstFragment_to_secondFragment,
                null,
                null,
                extras
            )
        }
        postponeEnterTransition()
        OneShotPreDrawListener.add(view.parent as ViewGroup) {
            startPostponedEnterTransition()
        }
    }
}
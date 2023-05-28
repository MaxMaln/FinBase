package com.example.finbase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.finbase.databinding.FragmentTeoryBinding
import java.io.IOException

class TeoryFragment : Fragment() {

    private var _binding: FragmentTeoryBinding? = null

    private val binding get() = _binding!!

    private var teorylist: ArrayList<TeoryClass> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTeoryBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.listTeory.setOnItemClickListener { parent, _, position, _ ->

            val item = parent.getItemAtPosition(position) as TeoryClass
            val intent = Intent(activity, TeoryRead::class.java)
            intent.putExtra("teory", item.Way)
            startActivity(intent)
        }
        listview()
    }

    private fun listview(){

        teorylist = ArrayList()

        val myAssetManager = activity?.applicationContext?.assets

        val sources = listOf(
            R.drawable.teory_image1,
            R.drawable.teory_image2,
            R.drawable.teory_image3,
            R.drawable.teory_image4,
        )

        try {
            val files = myAssetManager?.list("")
            val range = 2 until files!!.count()
            var score = 0
            for (i in range)
            {
                val str = files[i]
                val date = str.split("Глава")
                score++
                try {
                    val data = date[1].split(".pdf")
                    teorylist.add(TeoryClass(sources.random(), "Глава $score.", data[0].drop(3),files[i]))
                }
                finally {
                    continue
                }
            }
            teorylist.add(TeoryClass(R.drawable.sov_icon, "Советы", "","sov"))
            teorylist.add(TeoryClass(R.drawable.url_icon, "Ссылки", "","url"))
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        binding.listTeory.adapter = activity?.let { TeoryAdapter(it, teorylist) }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.finbase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.finbase.databinding.FragmentPracticBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PracticFragment : Fragment() {

    private var _binding: FragmentPracticBinding? = null

    private val binding get() = _binding!!

    private lateinit var numberglav: String
    
    private lateinit var numbertypes: String

    private var practiclist: ArrayList<PracticClassList> = ArrayList()

    private val apiStatus1: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)
    private val apiStatus2: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)
    private val apiStatus3: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)
    private val apiStatus4: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)

    private val apiStatus5: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)
    private val apiStatus6: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.LOADING)

    private var practictypeslist: ArrayList<PracticClassTypesList> = ArrayList()

    private val db : FirebaseFirestore = Firebase.firestore
    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPracticBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.listPractic.visibility != View.VISIBLE)
                {
                    findNavController().navigate(R.id.navigation_practic)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.listPractic.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position) as PracticClassList
            numberglav = item.Way
            listview2()
            binding.listTypes.visibility = View.VISIBLE
            binding.listPractic.visibility = View.INVISIBLE
        }
        binding.listTypes.setOnItemClickListener { parent, _, position, _ ->

            val item = parent.getItemAtPosition(position) as PracticClassTypesList
            numbertypes = item.Way
            var intent: Intent? = null

            when (numbertypes) {
                "test" -> {
                    intent = Intent(activity, PracticTest::class.java)
                }
                "sam" -> {
                    intent = Intent(activity, PracticIndependentwork::class.java)
                }
            }

            when (numberglav) {
                "glava1and2" -> {
                    numberglav = "glava1and2"
                    intent?.putExtra("glav", numberglav)
                    startActivity(intent)
                }
                "glava3and4" -> {
                    numberglav = "glava3and4"
                    intent?.putExtra("glav", numberglav)
                    startActivity(intent)
                }
                "glava5and6" -> {
                    numberglav = "glava5and6"
                    intent?.putExtra("glav", numberglav)
                    startActivity(intent)
                }
                "glava7" -> {
                    numberglav = "glava7"
                    intent?.putExtra("glav", numberglav)
                    startActivity(intent)
                }
            }
        }
        listview1()
    }

    private fun listview1() {

        practiclist = ArrayList()

        val sources = listOf(R.drawable.teory_image1, R.drawable.teory_image2, R.drawable.teory_image3, R.drawable.teory_image4)

        val ref = db.collection("progress").document(firebaseUser!!.uid)
        ref.get().addOnSuccessListener {
            if(it != null)
            {
                if(it.data?.get("glava1and2_test")?.toString() == "true" && it.data?.get("glava1and2_sam")?.toString() == "true")
                {
                    apiStatus1.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus1.value = ApiStatus.FAILED
                }
                if(it.data?.get("glava3and4_test")?.toString() == "true" && it.data?.get("glava3and4_sam")?.toString() == "true")
                {
                    apiStatus2.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus2.value = ApiStatus.FAILED
                }
                if(it.data?.get("glava5and6_test")?.toString() == "true" && it.data?.get("glava5and6_sam")?.toString() == "true")
                {
                    apiStatus3.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus3.value = ApiStatus.FAILED
                }
                if(it.data?.get("glava7_test")?.toString() == "true" && it.data?.get("glava7_sam")?.toString() == "true")
                {
                    apiStatus4.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus4.value = ApiStatus.FAILED
                }
            }
        }
            .addOnFailureListener {}

        apiStatus1.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 1 и 2", "glava1and2", R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 1 и 2", "glava1and2", R.drawable.loading_icon))
            }
        }
        apiStatus2.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 3 и 4", "glava3and4", R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 3 и 4", "glava3and4", R.drawable.loading_icon))
            }
        }
        apiStatus3.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 5 и 6", "glava5and6", R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 5 и 6", "glava5and6", R.drawable.loading_icon))
            }
        }
        apiStatus4.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 7", "glava7", R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practiclist.add(PracticClassList(sources.random(), "Глава 7", "glava7", R.drawable.loading_icon))
            }
            binding.listPractic.adapter = activity?.let { PracticAdapterList(it, practiclist) }
        }
    }
    private fun listview2(){

        practictypeslist = ArrayList()

        val ref = db.collection("progress").document(firebaseUser!!.uid)
        ref.get().addOnSuccessListener {
            if(it != null)
            {
                if(it.data?.get(numberglav+"_test")?.toString() == "true")
                {
                    apiStatus5.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus5.value = ApiStatus.FAILED
                }
                if(it.data?.get(numberglav+"_sam")?.toString() == "true")
                {
                    apiStatus6.value = ApiStatus.COMPLETE
                }
                else
                {
                    apiStatus6.value = ApiStatus.FAILED
                }
            }
        }
            .addOnFailureListener {}

        apiStatus5.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practictypeslist.add(PracticClassTypesList(R.drawable.test_icon,"Тест", "test",R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practictypeslist.add(PracticClassTypesList(R.drawable.test_icon,"Тест", "test",R.drawable.loading_icon))
            }
        }
        apiStatus6.observe(viewLifecycleOwner){
            if(it == ApiStatus.COMPLETE)
            {
                practictypeslist.add(PracticClassTypesList(R.drawable.sam_icon,"Самостоятельная работа", "sam",R.drawable.complete_icon))
            }
            else if(it == ApiStatus.FAILED)
            {
                practictypeslist.add(PracticClassTypesList(R.drawable.sam_icon,"Самостоятельная работа", "sam",R.drawable.loading_icon))
            }
            binding.listTypes.adapter = activity?.let { PracticTypesAdapterList(it, practictypeslist) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
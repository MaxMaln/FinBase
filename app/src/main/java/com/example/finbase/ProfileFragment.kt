package com.example.finbase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.finbase.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private val db : FirebaseFirestore = Firebase.firestore

    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    @SuppressLint("SetTextI18n")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.textViewEmail.text = "  " + firebaseUser?.email.toString()

        binding.plaintextBirthday.setOnClickListener{
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)
                    binding.plaintextBirthday.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.settingbutton.setOnClickListener {
            val intent = Intent(activity, Settings::class.java)
            startActivity(intent)
        }
        binding.buttonSave.setOnClickListener {

            val data = hashMapOf(
                "name" to binding.plaintextName.text.toString(),
                "surname" to binding.plaintextSurname.text.toString(),
                "patronymic" to binding.plaintextPatronymic.text.toString(),
                "datebirthday" to binding.plaintextBirthday.text.toString(),
            )
            db.collection("users").document(firebaseUser?.uid.toString()).update(data as Map<String, Any>)
            Toast.makeText(activity, "Данные обновлены", Toast.LENGTH_SHORT).show()
        }
        start()
    }
    private fun start() {
        val db : FirebaseFirestore = Firebase.firestore
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("users").document(userId)
        ref.get().addOnSuccessListener {
            if(it != null)
            {
                val name = it.data?.get("name")?.toString()
                val surname = it.data?.get("surname")?.toString()
                val patronymic = it.data?.get("patronymic")?.toString()
                val birthday = it.data?.get("datebirthday")?.toString()
                binding.plaintextName.setText(name)
                binding.plaintextSurname.setText(surname)
                binding.plaintextPatronymic.setText(patronymic)
                binding.plaintextBirthday.setText(birthday)
            }
        }
            .addOnFailureListener {}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
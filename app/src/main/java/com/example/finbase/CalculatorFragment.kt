package com.example.finbase

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.finbase.databinding.FragmentCalculatorBinding
import java.math.RoundingMode


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null

    private val binding get() = _binding!!

    private var type: String = ""

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.checkBoxV.typeface= this.activity?.let { ResourcesCompat.getFont(it,R.font.bahnschrift) }
        binding.checkBoxN.typeface= this.activity?.let { ResourcesCompat.getFont(it,R.font.bahnschrift) }

        this.activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.CalcView_taxes,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.arrayTaxes.adapter = adapter
            }
        }

        binding.arrayTaxes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                type = binding.arrayTaxes.selectedItem.toString()
                procents()
                returnanswer()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.arrayProcent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                returnanswer()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.buttonAccount.setOnClickListener {
            returnanswer()
        }
        binding.checkBoxV.setOnClickListener {
            if (binding.checkBoxV.isChecked)
            {
                binding.checkBoxN.isChecked = false
            }
            else if(!binding.checkBoxV.isChecked)
            {
                binding.checkBoxN.isChecked = true
            }
            returnanswer()
        }
        binding.checkBoxV.setOnTouchListener { _, event -> event.actionMasked == MotionEvent.ACTION_MOVE }
        binding.checkBoxN.setOnClickListener {
            if (binding.checkBoxN.isChecked)
            {
                binding.checkBoxV.isChecked = false
            }
            else if(!binding.checkBoxN.isChecked)
            {
                binding.checkBoxV.isChecked = true
            }
            returnanswer()
        }
        binding.checkBoxN.setOnTouchListener { _, event -> event.actionMasked == MotionEvent.ACTION_MOVE }
    }
    private fun returnanswer(){
        if(binding.editSum.text.toString() != "") {
            if (binding.editSum.text.toString().toDouble() > 999999999999) {
                Toast.makeText(
                    activity,
                    "Максимальное значение до 999 999 999 999",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var str = ""
                for (i in binding.arrayProcent.selectedItem.toString().toCharArray()) {
                    if (i != '%') {
                        str += i
                    }
                }
                val procent: Double = str.toDouble()
                when (type) {

                    "НДС" -> {
                        if (binding.checkBoxV.isChecked) {
                            var sum: Double = binding.editSum.text.toString().toDouble() / (1 - (procent / 100)) * (procent / 100)
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString().toDouble() / (1 - (procent / 100))
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text = String.format("НДС = $sum руб.\n\nОбщая сумма = $sun руб.")
                        } else if (binding.checkBoxN.isChecked) {
                            var sum: Double = binding.editSum.text.toString().toDouble() * (procent / 100)
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString().toDouble() - binding.editSum.text.toString().toDouble() * (procent / 100)
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text = String.format("НДС = $sum руб.\n\nСумма за вычетом налога =\n $sun руб.")
                        }
                    }

                    "НДФЛ" -> {
                        if (binding.checkBoxV.isChecked) {
                            var sum: Double = binding.editSum.text.toString()
                                .toDouble() / (1 - (procent / 100)) * (procent / 100)
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString()
                                .toDouble() / (1 - (procent / 100))
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("НДФЛ = $sum руб.\n" +
                                        "\n" +
                                        "Общая сумма = $sun руб.")
                        } else if (binding.checkBoxN.isChecked) {
                            var sum: Double =
                                binding.editSum.text.toString().toDouble() * (procent / 100)
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString()
                                .toDouble() - binding.editSum.text.toString()
                                .toDouble() * (procent / 100)
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("НДФЛ = $sum руб.\n" +
                                        "\n" +
                                        "Сумма за вычетом налога =\n" +
                                        " $sun руб.")
                        }
                    }

                    "ПФР" -> {
                        if (binding.checkBoxV.isChecked) {
                            var sum: Double =
                                (binding.editSum.text.toString().toDouble() / 0.78) * 0.22
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double =
                                binding.editSum.text.toString().toDouble() / 0.78
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ПФР = $sum руб.\n" +
                                        "\n" +
                                        "Общая сумма = $sun руб.")
                        } else if (binding.checkBoxN.isChecked) {
                            var sum: Double =
                                binding.editSum.text.toString().toDouble() * 0.22
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString()
                                .toDouble() - binding.editSum.text.toString()
                                .toDouble() * 0.22
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ПФР = $sum руб.\n" +
                                        "\n" +
                                        "Сумма за вычетом налога =\n" +
                                        " $sun руб.")
                        }
                    }

                    "ФСС" -> {
                        if (binding.checkBoxV.isChecked) {
                            var sum: Double =
                                (binding.editSum.text.toString().toDouble() / 0.971) * 0.029
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double =
                                binding.editSum.text.toString().toDouble() / 0.971
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ФСС = $sum руб.\n" +
                                        "\n" +
                                        "Общая сумма = $sun руб.")
                        } else if (binding.checkBoxN.isChecked) {
                            var sum: Double =
                                binding.editSum.text.toString().toDouble() * 0.029
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString()
                                .toDouble() - binding.editSum.text.toString()
                                .toDouble() * 0.029
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ФСС = $sum руб.\n" +
                                        "\n" +
                                        "Сумма за вычетом налога =\n" +
                                        " $sun руб.")
                        }
                    }

                    "ФФОМС" -> {
                        if (binding.checkBoxV.isChecked) {
                            var sum: Double =
                                (binding.editSum.text.toString().toDouble() / 0.949) * 0.051
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double =
                                binding.editSum.text.toString().toDouble() / 0.949
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ФСС = $sum руб.\n" +
                                        "\n" +
                                        "Общая сумма = $sun руб.")
                        } else if (binding.checkBoxN.isChecked) {
                            var sum: Double =
                                binding.editSum.text.toString().toDouble() * 0.0051
                            sum = sum.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            var sun: Double = binding.editSum.text.toString()
                                .toDouble() - binding.editSum.text.toString()
                                .toDouble() * 0.051
                            sun = sun.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                            binding.textViewEquals.text =
                                String.format("ФФОМС = $sum руб.\n" +
                                        "\n" +
                                        "Сумма за вычетом налога =\n" +
                                        " $sun руб.")
                        }
                    }
                }
            }
        }
    }
    fun procents() {
        when(type){
            "НДС" -> {
                this.activity?.let {
                    ArrayAdapter.createFromResource(
                        it,
                        R.array.CalcView_NDS,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.arrayProcent.adapter = adapter
                    }
                }
            }
            "НДФЛ" -> {
                this.activity?.let {
                    ArrayAdapter.createFromResource(
                        it,
                        R.array.CalcView_NDFL,
                        android.R.layout.simple_spinner_item
                    ).also { adapter ->
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.arrayProcent.adapter = adapter
                    }
                }
            }
        }
        if(type != "НДС" && type != "НДФЛ")
        {
            binding.arrayProcent.visibility = View.INVISIBLE
        }
        else
        {
            binding.arrayProcent.visibility = View.VISIBLE
        }
        if (type == "Страхование от НС")
        {
            binding.arrayProcent.visibility = View.INVISIBLE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package ru.otus.basicarchitecture

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.ParseException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PersonFragment : Fragment() {
    lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var name = view.findViewById<EditText>(R.id.name)
        var surname = view.findViewById<EditText>(R.id.surname)
        val birthDate = view.findViewById<EditText>(R.id.date)
        birthDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val currentDate = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = try {
                    dateFormat.parse(s.toString())
                } catch (e: ParseException) {
                    null
                }
                if (date != null) {
                    val age = getAge(date)
                    if (age >= 18) {
                            birthDate.error = null
                        } else {
                            birthDate.error = "Минимальный возраст - 18 лет"
                        }
                } else {
                    birthDate.error = "Некорректная дата"
                }
            }
        })




//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.conteiner)
//                    .commit()
//            }
//        }


    }

    private fun getAge(date: Date): Int {
        val today = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.time = date
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }
}
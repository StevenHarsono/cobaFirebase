package com.example.projectgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fsatu.newInstance] factory method to
 * create an instance of this fragment.
 */
class fsatu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var numbers: MutableList<Int> = mutableListOf()
    var checkNum: MutableList<Int> = mutableListOf()
    var correctIndex: MutableList<Int> = mutableListOf()
    lateinit var buttons: Array<Button?>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lower = MainActivity.lower
        val upper = MainActivity.upper

        numbers = (lower..upper).toMutableList().apply {
            addAll(this)
            shuffle()
        }

        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.columnCount = 4
        gridLayout.rowCount = ((upper - lower + 1) * 2) / 4

        buttons = arrayOfNulls((upper - lower + 1) * 2)
        for (i in buttons.indices) {
            buttons[i] = Button(context).apply {
                text = "?"
                id = i
                setOnClickListener { onClicked(i) }
            }
            // Tambahkan tombol ke grid layout
            gridLayout.addView(buttons[i])
        }

        var btnGiveUp = view.findViewById<Button>(R.id.button)
        btnGiveUp.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.main, fdua(), fdua::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    fun onClicked(index: Int) {
        buttons[index]?.text = numbers[index].toString()

        if (checkNum.size <= 1) {
            checkNum.add(numbers[index])
            correctIndex.add(index)

            if (checkNum.size >= 2) {
                onClicked(0)
            }

        } else {
            if (checkNum[0] == checkNum[1]) {
                MainActivity.score += 10
            } else {
                MainActivity.score -= 5
                correctIndex.removeLast()
                correctIndex.removeLast()
            }


            for (button in buttons) {
                if (!(button?.id in correctIndex)) {
                    button?.text = "?"

                }
            }

            checkNum.clear()
        }

        if (correctIndex.size == numbers.size) {
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.main, fdua(), fdua::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fsatu, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fsatu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fsatu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
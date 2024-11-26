package uts.c14220010.tebakangka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fSatu.newInstance] factory method to
 * create an instance of this fragment.
 */
class fSatu : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var numbers: MutableList<Int> = mutableListOf()
    lateinit var buttons: Array<Button?>
    var openedButtonIndex: Int? = null
    var pairsFound: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lowerBound = MainActivity.lowerBound
        val upperBound = MainActivity.upperBound

        //Inisialisasi Acak angka
        numbers = (lowerBound..upperBound).toMutableList().apply {
            addAll(this)
            shuffle()
        }

        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.columnCount = 4
        gridLayout.rowCount = ((upperBound - lowerBound + 1) * 2)/4
        gridLayout.setPadding(8, 8, 8, 8)

        //Inisialisasi button
        buttons = arrayOfNulls((upperBound - lowerBound + 1) * 2)
        for (i in buttons.indices) {
            buttons[i] = Button(context).apply {
                text = "?"
                setOnClickListener { onButtonClicked(i) }
            }
            // Tambahkan tombol ke grid layout
            gridLayout.addView(buttons[i])
        }

        var _btnGiveUp = view.findViewById<Button>(R.id.btnGiveUp)
        _btnGiveUp.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, fDua(), fDua::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    fun onButtonClicked(index: Int) {
        // Tampilkan angka pada tombol yang ditekan
        buttons[index]?.text = numbers[index].toString()

        if (openedButtonIndex == null) {
            // Jika belum ada angka terbuka, simpan indeks button yang ditekan
            openedButtonIndex = index
        } else {
            // Jika sudah ada angka terbuka, cek apakah angka cocok
            if (numbers[openedButtonIndex!!] == numbers[index]) {
                //Angka cocok
                MainActivity.score += 10
                pairsFound++
                openedButtonIndex = null

                //Cek apakah semua pasangan ditemukan
                if (pairsFound == numbers.size / 2) {
                    val mFragmentManager = parentFragmentManager
                    mFragmentManager.beginTransaction().apply {
                        replace(R.id.frameContainer, fDua(), fDua::class.java.simpleName)
                        addToBackStack(null)
                        commit()
                    }
                }
            } else {
                // Angka tidak cocok
                MainActivity.score -= 5
                // Kembalikan tombol ke ?
                buttons[openedButtonIndex!!]?.postDelayed({
                    buttons[openedButtonIndex!!]?.text = "?"
                    buttons[index]?.text = "?"
                    openedButtonIndex = null
                }, 500)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_satu, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fSatu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
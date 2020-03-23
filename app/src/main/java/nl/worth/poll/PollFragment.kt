package nl.worth.poll

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_poll.*
import nl.worth.MainActivity
import nl.worth.R

/**
 * A simple [Fragment] subclass.
 */
class PollFragment : Fragment() {

    private val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_question.text = "What is your favorite car color?"

        val topicsAdapter = QuestionsAdapter(mutableListOf(
            QuestionItem(false, "Red"),
            QuestionItem(false, "Grey"),
            QuestionItem(false, "Black"),
            QuestionItem(false, "Blue")
        ))

        rv_recycler_view.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        rv_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        rv_recycler_view.adapter = topicsAdapter

        topicsAdapter.onItemClick = { question ->
            mainActivity.clang.logEvent("pollSubmit", mapOf("title" to "FavoriteCarColor", "value" to question.text), {
                showAlertDialog()
            }, {
                showAlertDialog(it)
            })
        }
    }

    private fun showAlertDialog(throwable: Throwable? = null) {
        val builder = AlertDialog.Builder(requireContext())

        if (throwable != null) {
            builder.setTitle("Error!Error!Panic!")
            builder.setMessage(throwable.message)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.cancel()
            }
        } else {
            builder.setTitle("Success")
            builder.setMessage("Favorite car color submitted")
            builder.setPositiveButton(android.R.string.ok) {_, _ ->
                findNavController().popBackStack()
            }
        }
        builder.show()
    }
}

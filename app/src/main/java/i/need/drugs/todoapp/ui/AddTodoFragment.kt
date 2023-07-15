package i.need.drugs.todoapp.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.databinding.FragmentTodoBinding
import i.need.drugs.todoapp.domain.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class AddTodoFragment  : Fragment() {

    private var _binding: FragmentTodoBinding? = null

    private val binding: FragmentTodoBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: TodoViewModel

    private lateinit var todo : Todo

    private var priorityMenu: PopupMenu? = null
    private val c = Calendar.getInstance()

    private val component by lazy {
        (requireActivity().application as TodoApp)
            .component
            .mainActivityComponent()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        init()
        setupListeners()

        return binding.root
    }

    private fun init(){
        with(binding){
            todo = Todo(
                id = UUID.randomUUID(),
                msg = "",
                priority = Todo.Priority.NORMAL,
                deadline = null,
                isCompleted = false,
                createDate = Calendar.getInstance().time,
                changedDate = null
            )
            setupMenu()
            setupDate(c.time)
            ivDelete
                .setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.label_disable),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )

            tvDelete.setTextColor(requireActivity().getColor(R.color.label_disable))
        }
    }

    private fun setupListeners(){
        with(binding){
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvPriority.setOnClickListener {
                priorityMenu?.show()
            }

            switchDeadline.setOnClickListener {
                if (switchDeadline.isChecked){
                    tvDeadline.visibility = View.VISIBLE
                    todo.deadline = Calendar.getInstance().time
                }else{
                    tvDeadline.visibility = View.INVISIBLE
                    todo.deadline = null
                }
            }

            tvDeadline.setOnClickListener {
                openDatePicker()
            }

            btnSave.setOnClickListener {
                todo.msg = tvMsg.text.toString()

                if (todo.msg.isNotBlank()) {
                    lifecycle.coroutineScope.launch(Dispatchers.IO) {
                        viewModel.addTodo(todo).collect {
                            Log.d("addTodo", it.toString())
                        }
                    }
                    findNavController().popBackStack()
                } else {
                    val decorView = requireActivity().window.decorView
                    val view = decorView.findViewById(android.R.id.content) ?: decorView.rootView
                    Snackbar
                        .make(view, "Заполните все поля", Snackbar.LENGTH_LONG)
                        .setTextColor(requireActivity().getColor(R.color.label_primary))
                        .setBackgroundTint(requireActivity().getColor(R.color.back_secondary))
                        .show()

                }
            }
        }
    }

    private fun openDatePicker() {
        if (todo.deadline != null) {
            val c = Calendar.getInstance()
            c.time = todo.deadline!!
        }

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, y, m, d ->
                val calendar = Calendar.getInstance()
                calendar.set(y, m, d)
                setupDate(calendar.time)
                todo.deadline = calendar.time
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun setupMenu(){
        priorityMenu = PopupMenu(requireContext(), binding.tvPriority)

        priorityMenu!!.menuInflater.inflate(R.menu.menu_priority, priorityMenu!!.menu)

        // Хардкодить плохо, но я разбойник
        priorityMenu!!.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.low -> {
                    binding.tvPriority.setTextColor(requireActivity().getColor(R.color.label_tertiary))
                    binding.tvPriority.text = "Низкий"
                    todo.priority = Todo.Priority.LOW
                }
                R.id.normal -> {
                    binding.tvPriority.setTextColor(requireActivity().getColor(R.color.label_tertiary))
                    binding.tvPriority.text = "Нет"
                    todo.priority = Todo.Priority.NORMAL
                }
                else -> {
                    binding.tvPriority.setTextColor(requireActivity().getColor(R.color.red))
                    binding.tvPriority.text = "!! Высокий"
                    todo.priority = Todo.Priority.URGENT
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setupDate(date: Date?) {
        val c = Calendar.getInstance()
        if (date != null)
            c.time = date

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.tvDeadline.text = getString(R.string.date, day, month, year)
    }

}
package i.need.drugs.todoapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.databinding.FragmentMainBinding
import i.need.drugs.todoapp.domain.model.ResponseState.State.STATE_OK
import i.need.drugs.todoapp.domain.model.Todo
import i.need.drugs.todoapp.ui.recyclerview.TodoListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private val todoListAdapter = TodoListAdapter()

    companion object{
        var countDone = MutableLiveData(0)
    }
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
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
//        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory) [MainViewModel::class.java]

        viewModel.todoList.observeForever {
            when(it.state){
                STATE_OK -> if (it.data != null) todoListAdapter.submitList(it.data.sortedBy { it.createDate })
                else -> {}
            }
        }

        setupRecyclerView()
        setupRefreshLayout()
        setupListeners()

        return binding.root
    }

    private fun setupRecyclerView(){
        with(binding.rvMain){
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(requireContext())

            setListener(object :  SwipeLeftRightCallback.Listener {
                override fun onSwipedRight(position: Int) {
                    val todo = todoListAdapter.currentList[position]
                    lifecycle.coroutineScope.launch(Dispatchers.IO) {
                        viewModel.changeDoneState(todo).collect {
                            Log.d("changeDoneState", it.toString())
                        }
                    }
                }

                override fun onSwipedLeft(position: Int) {
                    val todo = todoListAdapter.currentList[position]
                    lifecycle.coroutineScope.launch(Dispatchers.IO) {
                        viewModel.deleteTodo(todo).collect {
                            Log.d("deleteTodo", it.toString())
                        }
                    }
                    makeUndoSnackbar(binding.rvMain as View, todo)
                }
            })

        }

        todoListAdapter.onTodoClickListener = {
            findNavController().navigate(MainFragmentDirections.actionEditTodo(it.id.toString()))
        }

        todoListAdapter.onTodoEditedListener = {
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                viewModel.changeDoneState(it).collect {
                    Log.d("changeDoneState", it.toString())
                }
            }
        }
    }

    private fun setupRefreshLayout(){
        binding.refresh.setOnRefreshListener {
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                val list = viewModel.todoList.value?.data ?: emptyList()
                viewModel.updateTodoList(list)
            }
            binding.refresh.isRefreshing = false
        }
    }

    private fun setupListeners() {
        binding.fabAddTodo.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionAddTodo()
            )
        }

        binding.cardAddNew.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionAddTodo()
            )
        }
        binding.btnSettings.setOnClickListener { 
            findNavController().navigate(
                MainFragmentDirections.actionSettings()

            )
        }
    }
    private fun makeUndoSnackbar(view: View, todo: Todo){
        Snackbar.make(view, todo.msg, 5000).setAction("Отменить") {
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                viewModel.addTodo(todo).collect {}
            }
        }
            .setBackgroundTint(view.context.getColor(R.color.red))
            .setText("Удалить ${todo.msg}?")
            .setTextColor(view.context.getColor(R.color.label_primary))
            .setActionTextColor(view.context.getColor(R.color.label_primary))
            .show()
    }
}
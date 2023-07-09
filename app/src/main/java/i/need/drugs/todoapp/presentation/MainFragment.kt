package i.need.drugs.todoapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.databinding.FragmentMainBinding
import i.need.drugs.todoapp.presentation.recyclerview.TodoListAdapter

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    private val todoListAdapter = TodoListAdapter()

    companion object{
        var countDone = MutableLiveData(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this) [MainViewModel::class.java]

        viewModel.todoList.observe(viewLifecycleOwner) { todoList ->
            var count = 0

            todoList.forEach { todoItem ->
                 if (todoItem.isCompleted)
                     count += 1
            }

            countDone.value = count
            todoListAdapter.submitList(todoList)
        }


        if (requireContext().getNeedUpdate()){
            viewModel.todoList.value?.let {
                viewModel.updateTodoList(requireActivity(), requireContext().getRevision(), it)
                requireContext().setNeedUpdate(false)
            }
        }else{
            viewModel.downloadTodoList(requireActivity())
        }

        countDone.observe(viewLifecycleOwner) {
            binding.tvDone.text = getString(R.string.todo_done, it ?: 0)
        }

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

        binding.refresh.setOnRefreshListener {
            if (requireActivity().getNeedUpdate()){
                viewModel.todoList.value?.let {
                    viewModel.updateTodoList(requireActivity(), requireActivity().getRevision(), it)
                    requireActivity().setNeedUpdate(false)
                }

            }else{
                viewModel.downloadTodoList(requireActivity())
            }
            binding.refresh.isRefreshing = false
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView(){

        with(binding.rvMain){
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(requireContext())

            setListener(object :  SwipeLeftRightCallback.Listener {
                override fun onSwipedRight(position: Int) {
                    viewModel.changeDoneState(requireActivity(), requireContext().getRevision(), todoListAdapter.currentList[position])
                }

                override fun onSwipedLeft(position: Int) {
                    viewModel.deleteTodoItem(requireActivity(), requireContext().getRevision(), todoListAdapter.currentList[position].id)
                }
            })

        }

        todoListAdapter.onTodoItemClickListener = {
            findNavController().navigate(MainFragmentDirections.actionEditTodo(it.id))
        }

        todoListAdapter.onTodoItemEditedListener = {
            viewModel.changeDoneState(requireActivity(), requireContext().getRevision(), it)
        }
    }
}
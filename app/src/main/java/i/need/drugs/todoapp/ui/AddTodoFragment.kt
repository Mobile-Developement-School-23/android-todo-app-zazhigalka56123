package i.need.drugs.todoapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.databinding.FragmentAddTodoBinding
import i.need.drugs.todoapp.ui.compose.AddTodoScreen
import i.need.drugs.todoapp.ui.compose.theme.theme
import javax.inject.Inject

class AddTodoFragment: Fragment(){

    private var _binding: FragmentAddTodoBinding? = null

    private val binding: FragmentAddTodoBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: TodoViewModel

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        viewModel.onNavigateBack = {findNavController().popBackStack()}

        binding.composeAddTodoItem.setContent {
            theme {
                AddTodoScreen(
                    viewModel.todo.collectAsState().value,
                    viewModel::observeState
                )
            }
        }

        return binding.root
    }
}
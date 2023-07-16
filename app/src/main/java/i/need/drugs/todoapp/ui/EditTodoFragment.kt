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
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.databinding.FragmentTodoBinding
import i.need.drugs.todoapp.ui.compose.AddTodoScreen
import i.need.drugs.todoapp.ui.compose.theme.theme
import java.util.UUID
import javax.inject.Inject

class EditTodoFragment: Fragment(){

    private var _binding: FragmentTodoBinding? = null

    private val binding: FragmentTodoBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: TodoViewModel

    private val todoId by lazy {
        UUID.fromString(navArgs<EditTodoFragmentArgs>().value.todoId)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]

        viewModel.onNavigateBack = {findNavController().popBackStack()}
        viewModel.isNewItem = false
        viewModel.getTodo(todoId)

        binding.compose.setContent {
            theme {
                AddTodoScreen(
                    viewModel.todo.collectAsState().value,
                    true,
                    viewModel::observeState
                )
            }
        }

        return binding.root
    }
}
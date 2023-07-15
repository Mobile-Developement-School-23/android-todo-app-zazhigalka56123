package i.need.drugs.todoapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.TodoApp
import i.need.drugs.todoapp.databinding.FragmentSettingsBinding
import i.need.drugs.todoapp.domain.Constant
import i.need.drugs.todoapp.domain.Constant.THEME_DARK
import i.need.drugs.todoapp.domain.Constant.THEME_LIGHT
import i.need.drugs.todoapp.domain.Constant.THEME_SYSTEM
import i.need.drugs.todoapp.domain.model.Todo
import javax.inject.Inject

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding: FragmentSettingsBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
//        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)

        when(TodoApp.theme(sp)){
            THEME_LIGHT -> binding.buttonToggleGroup.check(R.id.btn_light_theme)
            THEME_SYSTEM -> binding.buttonToggleGroup.check(R.id.btn_system_theme)
            THEME_DARK -> binding.buttonToggleGroup.check(R.id.btn_dark_theme)
        }

        binding.btnDarkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            TodoApp.setTheme(THEME_DARK, sp)
        }
        binding.btnLightTheme.setOnClickListener{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            TodoApp.setTheme(THEME_LIGHT, sp)

        }
        binding.btnSystemTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            TodoApp.setTheme(THEME_SYSTEM, sp)

        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

}
package app.khodko.planner.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.extension.navigateExt
import app.khodko.planner.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel = getViewModelExt { LoginViewModel() }

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnRegistratin.setOnClickListener {
            navigateExt(LoginFragmentDirections.actionNavLoginToNavRegistration())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
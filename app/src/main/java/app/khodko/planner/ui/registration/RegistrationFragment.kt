package app.khodko.planner.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.bitmapToString
import app.khodko.planner.core.decodeUri
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.data.entity.User
import app.khodko.planner.databinding.FragmentRegistrationBinding
import app.khodko.planner.ui.activity.ImageChooserInterface
import app.khodko.planner.ui.activity.MainActivity
import app.khodko.planner.ui.activity.USER_ID_PREF

class RegistrationFragment : BaseFragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var regstrationViewModel: RegistrationViewModel
    private var icon = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        regstrationViewModel = getViewModelExt { RegistrationViewModel(App.instance.userRepository) }

        initObservers()
        initListeners()

        return binding.root
    }

    private fun initObservers() {
        regstrationViewModel.existUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = getString(R.string.registr_error_exists)
            } else {
                binding.textError.visibility = View.GONE
            }
        }
        regstrationViewModel.userId.observe(viewLifecycleOwner) {
            it?.let {
                val sharedPreferences = requireActivity().getSharedPreferences(USER_ID_PREF, 0)
                sharedPreferences.edit().putLong(USER_ID_PREF, it).apply()

                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun initListeners() {
        binding.btnRegistr.setOnClickListener {
            validate()?.let {
                it.icon = icon
                regstrationViewModel.save(it)
            }
        }
        binding.profileImage.setOnClickListener {
            val imageChooser = activity as ImageChooserInterface
            imageChooser.showImageChooser {
                it?.let { binding.profileImage.setImageURI(it)
                    val btm = decodeUri(requireContext(), it, 500)
                    icon = bitmapToString(btm!!) }
            }
        }
    }

    private fun validate(): User? {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        when {
            name.isEmpty() -> {
                binding.editTextName.requestFocus()
                binding.editTextName.error = getString(R.string.name_field_error)
            }
            name.isEmpty() -> {
                binding.editTextName.requestFocus()
                binding.editTextName.error = getString(R.string.email_field_error)
            }
            password.isEmpty() -> {
                binding.editTextPassword.requestFocus()
                binding.editTextPassword.error = getString(R.string.password_field_error)
            }
            else -> {
                return User(name = name, password = password, email = email)
            }
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
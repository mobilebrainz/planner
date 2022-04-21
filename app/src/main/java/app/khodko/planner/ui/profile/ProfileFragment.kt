package app.khodko.planner.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import app.khodko.planner.App
import app.khodko.planner.R
import app.khodko.planner.core.BaseFragment
import app.khodko.planner.core.bitmapToString
import app.khodko.planner.core.decodeUri
import app.khodko.planner.core.extension.getViewModelExt
import app.khodko.planner.core.stringToBitmap
import app.khodko.planner.data.entity.User
import app.khodko.planner.databinding.FragmentProfileBinding
import app.khodko.planner.ui.activity.ImageChooserInterface
import app.khodko.planner.ui.activity.LoginActivity
import app.khodko.planner.ui.activity.USER_ID_PREF

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var icon = ""
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = getViewModelExt { ProfileViewModel(App.instance.userRepository, checkUserId()) }

        initObservers()
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            validate()?.let {
                it.icon = icon
                profileViewModel.save(it)
            }
        }
        binding.btnLogout.setOnClickListener {
            requireActivity().getSharedPreferences(USER_ID_PREF, 0).edit { clear() }
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        binding.profileImage.setOnClickListener {
            val imageChooser = activity as ImageChooserInterface
            imageChooser.showImageChooser {
                it?.let {
                    binding.profileImage.setImageURI(it)
                    val btm = decodeUri(requireContext(), it, 500)
                    icon = bitmapToString(btm!!)
                }
            }
        }
    }

    private fun initObservers() {
        profileViewModel.existUser.observe(viewLifecycleOwner) {
            if (it) {
                binding.textError.visibility = View.VISIBLE
                binding.textError.text = getString(R.string.registr_error_exists)
            } else {
                binding.textError.visibility = View.GONE
            }
        }
        profileViewModel.savedUser.observe(viewLifecycleOwner) {
            showInfoSnackbar(R.string.user_saved)
        }
        profileViewModel.user.observe(viewLifecycleOwner) {
            it?.let { u ->
                binding.editTextName.setText(u.name)
                binding.editTextEmail.setText(u.email)
                binding.editTextPassword.setText(u.password)
                if (u.icon.isNotEmpty()) {
                    binding.profileImage.setImageBitmap(stringToBitmap(u.icon))
                    icon = u.icon
                }
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
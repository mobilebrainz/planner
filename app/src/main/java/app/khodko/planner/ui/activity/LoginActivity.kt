package app.khodko.planner.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import app.khodko.planner.R
import app.khodko.planner.databinding.ActivityLoginBinding

const val USER_ID_PREF = "USER_ID_PREF"

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        checkUserId()

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun checkUserId() {
        val userId = getSharedPreferences(USER_ID_PREF, 0).getLong(USER_ID_PREF, -1)
        if (userId > 0) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
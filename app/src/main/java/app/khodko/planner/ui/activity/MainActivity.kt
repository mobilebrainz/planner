package app.khodko.planner.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import app.khodko.planner.R
import app.khodko.planner.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_calendar, R.id.nav_notes, R.id.nav_profile, R.id.nav_about
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(this)

        checkUserId()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var handled = true
        when (menuItem.itemId) {
            R.id.logout -> logOut()
            else -> handled = menuItem.onNavDestinationSelected(navController)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return handled
    }

    private fun logOut() {
        getSharedPreferences(USER_ID_PREF, 0).edit { clear() }
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun checkUserId() {
        val userId = getSharedPreferences(USER_ID_PREF, 0).getLong(USER_ID_PREF, -1)
        if (userId > 0) {
            // todo: get user from room

        } else {
            logOut()
        }
    }
}
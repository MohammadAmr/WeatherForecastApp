package com.example.weatherforecastapp

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.weatherforecastapp.alerts.views.AlertsFragment
import com.example.weatherforecastapp.favorites.views.FavoritesFragment
import com.example.weatherforecastapp.home.views.HomeFragment
import com.example.weatherforecastapp.settings.views.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var dialog : Dialog
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigationDrawer()

        val sharedPref = getSharedPreferences("M3lsh", Context.MODE_PRIVATE)
        val isFirst : Boolean = sharedPref.getBoolean("isFirstTime", true)

        if (isFirst) {
            dialog = Dialog(this)
            dialog.apply {
                setContentView(R.layout.initial_settings_setup)
                getWindow()?.setBackgroundDrawable(getDrawable(R.drawable.background))
                getWindow()?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setCancelable(false)

                val btnDone: Button = findViewById(R.id.btnDone)
                btnDone.setOnClickListener(View.OnClickListener {
                    val editor = sharedPref.edit()
                    editor.apply{
                        putBoolean("isFirstTime", false)
                        apply()
                    }
                    dismiss();
                })
                show();
            }
        }

    }

    private fun initNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_favorites -> replaceFragment(FavoritesFragment(), it.title.toString())
                R.id.nav_settings -> replaceFragment(SettingsFragment(), it.title.toString())
                R.id.nav_alert -> replaceFragment(AlertsFragment(), it.title.toString())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
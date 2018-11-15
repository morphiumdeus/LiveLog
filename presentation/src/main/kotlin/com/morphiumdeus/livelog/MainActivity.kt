package com.morphiumdeus.livelog

import android.os.Bundle
import android.support.design.widget.BottomNavigationView

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.morphiumdeus.livelog.popularmovies.CategoryListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CategoryListFragment(), "categoryList")
                    .commitNow()
            title = getString(R.string.popular)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}

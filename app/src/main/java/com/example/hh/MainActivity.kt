package com.example.hh

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.hh.core.ClearViewModel
import com.example.hh.core.ProvideViewModel
import com.example.hh.core.presentation.Screen
import com.example.hh.favorite.presentation.screen.FavoriteVacanciesFragment
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.filters.presentation.screen.FiltersScreen
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.loadvacancies.presentation.screen.LoadVacanciesScreen
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.presentation.screen.MainFragment
import com.example.hh.main.presentation.screen.NavigateToHome
import com.example.hh.search.presentation.SearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Navigate {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel =
            (application as ProvideViewModel).viewModel<MainViewModel>(MainViewModel::class.java.simpleName)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            val fragment: Fragment = when (it.itemId) {
                R.id.main -> {
//                    viewModel.clearSearch()
//                    viewModel.clearLoadVacancies()
//                    viewModel.clearFilters()
                    MainFragment()

//                    supportFragmentManager.findFragmentByTag(MainFragment::class.java.simpleName)
//                        ?: MainFragment()
                }

                R.id.favorite -> {
                    viewModel.clearSearch()
                    viewModel.clearLoadVacancies()
                    viewModel.clearFilters()
                    FavoriteVacanciesFragment()
//                    supportFragmentManager.findFragmentByTag(FavoriteVacanciesFragment::class.java.simpleName)
//                        ?: FavoriteVacanciesFragment()
                }

                else -> throw IllegalStateException()
            }
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    fragment,
                    MainFragment::class.java.simpleName
                )
                .commit()
            true
        }

        if (savedInstanceState == null)
            bottomNavigationView.selectedItemId = R.id.main
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.fragment_container_view, supportFragmentManager)
    }

    override fun navigateToHome() {
        TODO("Not yet implemented")
    }
}

class MainViewModel(
    private val clearViewModel: ClearViewModel
) : ViewModel() {

    fun clearHome() = with(clearViewModel) {
        clear(MainViewModel::class.java.simpleName)
    }

    fun clearFilters() = with(clearViewModel) {
        clear(FiltersViewModel::class.java.simpleName)
    }

    fun clearLoadVacancies() {
        clearViewModel.clear(LoadVacanciesViewModel::class.java.simpleName)
    }

    fun clearSearch() {
        clearViewModel.clear(SearchViewModel::class.java.simpleName)
    }
}

interface Navigate : NavigateToLoadVacancies, NavigateToFilters, NavigateToHome {
    fun navigate(screen: Screen)

    override fun navigateToLoadVacancies() = navigate(LoadVacanciesScreen)
    override fun navigateToFilters() = navigate(FiltersScreen)
    //override fun navigateToHome() = navigate(MainScreen)
}

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
import com.example.hh.favorite.presentation.FavoriteVacanciesViewModel
import com.example.hh.favorite.presentation.screen.FavoriteVacanciesFragment
import com.example.hh.filters.presentation.FiltersViewModel
import com.example.hh.filters.presentation.screen.FiltersScreen
import com.example.hh.filters.presentation.screen.NavigateToFilters
import com.example.hh.loadvacancies.presentation.LoadVacanciesViewModel
import com.example.hh.loadvacancies.presentation.screen.LoadVacanciesScreen
import com.example.hh.loadvacancies.presentation.screen.NavigateToLoadVacancies
import com.example.hh.main.presentation.VacanciesViewModel
import com.example.hh.main.presentation.screen.MainFragment
import com.example.hh.main.presentation.screen.ProfileFragment
import com.example.hh.main.presentation.screen.ResponsesFragment
import com.example.hh.search.presentation.SearchViewModel
import com.example.hh.vacancydetails.presentation.screen.NavigateToVacancyDetails
import com.example.hh.vacancydetails.presentation.screen.VacancyDetailsScreen
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
                    val currentFragment1 =
                        supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                    if (currentFragment1 is MainFragment) {
                        return@setOnItemSelectedListener true
                    } else {
                        viewModel.clearAllWithoutMain()
                        MainFragment()
                    }
                }

                R.id.favorite -> {
                    viewModel.clearSearch()
                    viewModel.clearVacancies()
                    viewModel.clearLoadVacancies()
                    viewModel.clearFilters()
                    viewModel.clearLoadVacancies()
                    viewModel.clearVacancyDetails()
                    FavoriteVacanciesFragment()
                }

                R.id.responses -> {
                    viewModel.clearAllWithoutMain()
                    ResponsesFragment()
                }

                R.id.profile -> {
                    viewModel.clearAllWithoutMain()
                    ProfileFragment()
                }

                else -> throw IllegalStateException()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
            true
        }

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.main
        }
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.fragment_container_view, supportFragmentManager)
    }
}


class MainViewModel(
    private val clearViewModel: ClearViewModel
) : ViewModel() {

    fun clearFavorite() {
        clearViewModel.clear(FavoriteVacanciesViewModel::class.java.simpleName)
    }

    fun clearVacancies() {
        clearViewModel.clear(VacanciesViewModel::class.java.simpleName)
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

    fun clearVacancyDetails() {
        clearViewModel.clear(VacancyDetailsScreen::class.java.simpleName)
    }

    fun clearAllWithoutMain() {
        this.clearSearch()
        this.clearLoadVacancies()
        this.clearFilters()
        this.clearVacancies()
        this.clearVacancyDetails()
        this.clearFavorite()
    }
}

interface Navigate : NavigateToLoadVacancies, NavigateToFilters, NavigateToVacancyDetails {
    fun navigate(screen: Screen)

    override fun navigateToLoadVacancies() = navigate(LoadVacanciesScreen)
    override fun navigateToFilters() = navigate(FiltersScreen)
    override fun navigateToVacancyDetails(vacancyId: String, backstackName: String) =
        navigate(VacancyDetailsScreen(vacancyId, backstackName))
}


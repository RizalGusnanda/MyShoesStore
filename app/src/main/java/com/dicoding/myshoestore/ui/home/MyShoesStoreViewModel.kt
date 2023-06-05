package com.dicoding.myshoestore.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.myshoestore.data.ShoeRepository
import com.dicoding.myshoestore.model.Shoe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyShoesStoreViewModel(private val repository: ShoeRepository) : ViewModel() {
    private val _shoes = MutableStateFlow(
        repository.getShoes()
            .sortedBy { it.name }
    )
    val shoes: StateFlow<List<Shoe>> get() = _shoes

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _shoes.value = repository.searchShoes(_query.value)
            .sortedBy { it.name }
    }
}

class ViewModelFactory(private val repository: ShoeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyShoesStoreViewModel::class.java)) {
            return MyShoesStoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
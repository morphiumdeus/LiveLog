package com.morphiumdeus.livelog.details

import android.arch.lifecycle.MutableLiveData
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.usecases.GetCategoryDetails
import com.morphiumdeus.livelog.common.BaseViewModel
import com.morphiumdeus.livelog.common.SingleLiveEvent
import com.morphiumdeus.livelog.entities.Category

class CategoryDetailsViewModel(private val getCategoryDetails: GetCategoryDetails,
                               private val mapper: Mapper<CategoryEntity, Category>,
                               private val categoryId: Int) : BaseViewModel() {

    lateinit var logEntity: CategoryEntity
    var viewState: MutableLiveData<CategoryDetailsViewState> = MutableLiveData()
    var favoriteState: MutableLiveData<Boolean> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()

    init {
        viewState.value = CategoryDetailsViewState(isLoading = true)
    }

    fun getMovieDetails() {
        addDisposable(
                getCategoryDetails.getById(categoryId)
                        .map {
                            it.value?.let {
                                logEntity = it
                                mapper.mapFrom(logEntity)
                            } ?: run {
                                throw Throwable("Something went wrong :(")
                            }
                        }
                        .subscribe(
                                { onMovieDetailsReceived(it) },
                                { errorState.value = it }
                        )
        )
    }

    private fun onMovieDetailsReceived(category: Category) {

        val newViewState = viewState.value?.copy(
                isLoading = false,
                name = category.name)

        viewState.value = newViewState
    }
}
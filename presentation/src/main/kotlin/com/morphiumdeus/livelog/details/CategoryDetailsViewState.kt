package com.morphiumdeus.livelog.details

import com.morphiumdeus.livelog.entities.Video


data class CategoryDetailsViewState(
        var isLoading: Boolean = true,
        var name: String? = null
)
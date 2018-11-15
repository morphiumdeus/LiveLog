package com.morphiumdeus.data.api

import com.google.gson.annotations.SerializedName
import com.morphiumdeus.data.entities.CategoryData

class CategoryListResult {

    var page: Int = 0
    @SerializedName("results")
    lateinit var categories: List<CategoryData>
}
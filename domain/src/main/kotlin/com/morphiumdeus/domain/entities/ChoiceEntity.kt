package com.morphiumdeus.domain.entities

import java.util.*

data class ChoiceEntity(
        val id: Int,
        val categoryId: Int,
        val name: String,
        val time: Calendar? = null
) {
}
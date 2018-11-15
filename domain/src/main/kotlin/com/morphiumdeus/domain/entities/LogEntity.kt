package com.morphiumdeus.domain.entities

import java.util.*

data class LogEntity(
        var id: Int = 0,
        val categoryId: Int,
        var choice: ChoiceEntity,
        var timestamp: Calendar
)
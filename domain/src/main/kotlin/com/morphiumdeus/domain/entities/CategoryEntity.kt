package com.morphiumdeus.domain.entities

data class CategoryEntity(
        var id: Int,
        var name: String,
        var reminder: ReminderEntity
)
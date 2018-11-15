package com.morphiumdeus.livelog.entities

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ChoiceEntity
import com.morphiumdeus.domain.entities.ReminderEntity

data class Category (
        var id: Int = 0,
        var name: String,
        var reminder: Reminder) {
}
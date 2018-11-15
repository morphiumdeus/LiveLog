package com.morphiumdeus.domain.entities

data class ReminderEntity(
        var date: String,
        var time: String,
        var repeat: Boolean,
        var repeatNo: Int,
        var repeatType: String,
        var active: Boolean
)
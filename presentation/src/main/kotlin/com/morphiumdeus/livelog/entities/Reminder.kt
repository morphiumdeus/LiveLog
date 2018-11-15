package com.morphiumdeus.livelog.entities

data class Reminder (
        var date: String,
        var time: String,
        var repeat: Boolean,
        var repeatNo: Int,
        var repeatType: String,
        var active: Boolean
)
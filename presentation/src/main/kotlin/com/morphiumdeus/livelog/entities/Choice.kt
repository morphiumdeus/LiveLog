package com.morphiumdeus.livelog.entities

import java.util.*

data class Choice (
        var id: Int,
        var name: String,
        var time: Calendar? = null
)

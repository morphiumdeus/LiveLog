package com.morphiumdeus.livelog.entities

import com.morphiumdeus.domain.entities.ChoiceEntity
import java.util.*

data class Log(
        var id: Int = 0,
        var choice: ChoiceEntity,
        var timestamp: Calendar
)
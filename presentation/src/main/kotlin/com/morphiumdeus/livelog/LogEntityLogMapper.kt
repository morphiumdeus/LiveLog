package com.morphiumdeus.livelog

import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ChoiceEntity
import com.morphiumdeus.domain.entities.LogEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import com.morphiumdeus.livelog.entities.Category
import com.morphiumdeus.livelog.entities.Choice
import com.morphiumdeus.livelog.entities.Log
import com.morphiumdeus.livelog.entities.Reminder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogEntityLogMapper @Inject constructor() : Mapper<LogEntity, Log>() {

    override fun mapFrom(from: LogEntity): Log {
        val log = Log(
                id = from.id,
                choice = from.choice,
                timestamp = from.timestamp
        )

        return log
    }
}
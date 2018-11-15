package com.morphiumdeus.livelog

import com.morphiumdeus.data.entities.ReminderData
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderDataReminderEntityMapper @Inject constructor() : Mapper<ReminderData, ReminderEntity>() {

    override fun mapFrom(from: ReminderData): ReminderEntity {
        val reminder = ReminderEntity(
                date = from.date,
                time = from.time,
                repeat = from.repeat,
                repeatNo = from.repeatNo,
                repeatType = from.repeatType,
                active = from.active
        )

        return reminder
    }
}
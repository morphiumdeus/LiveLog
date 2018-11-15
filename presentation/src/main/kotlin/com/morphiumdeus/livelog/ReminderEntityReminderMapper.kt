package com.morphiumdeus.livelog

import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import com.morphiumdeus.livelog.entities.Category
import com.morphiumdeus.livelog.entities.Reminder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderEntityReminderMapper @Inject constructor() : Mapper<ReminderEntity, Reminder>() {

    override fun mapFrom(from: ReminderEntity): Reminder {
        val reminder = Reminder(
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
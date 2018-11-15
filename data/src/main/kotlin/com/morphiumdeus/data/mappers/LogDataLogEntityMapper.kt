package com.morphiumdeus.livelog

import com.morphiumdeus.data.entities.LogData
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ChoiceEntity
import com.morphiumdeus.domain.entities.LogEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogDataLogEntityMapper @Inject constructor() : Mapper<LogData, LogEntity>() {

    override fun mapFrom(from: LogData): LogEntity {
        val log = LogEntity(
                id = from.id,
                categoryId = from.categoryId,
                choice = from.choice,
                timestamp = from.timestamp
        )

        return log
    }
}
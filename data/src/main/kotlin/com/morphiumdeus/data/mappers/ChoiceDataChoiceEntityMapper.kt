package com.morphiumdeus.livelog

import com.morphiumdeus.data.entities.ChoiceData
import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ChoiceEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChoiceDataChoiceEntityMapper @Inject constructor() : Mapper<ChoiceData, ChoiceEntity>() {

    override fun mapFrom(from: ChoiceData): ChoiceEntity {
        val choice = ChoiceEntity(
                id = from.id,
                categoryId = from.categoryId,
                name = from.name,
                time = from.time
        )

        return choice
    }
}
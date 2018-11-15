package com.morphiumdeus.livelog

import com.morphiumdeus.domain.common.Mapper
import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.livelog.entities.Category
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */

@Singleton
class CategoryEntityCategoryMapper @Inject constructor() : Mapper<CategoryEntity, Category>() {

    override fun mapFrom(
            from: CategoryEntity
    ): Category {
        val reminderEntityReminderMapper = ReminderEntityReminderMapper()

        return Category(
                id = from.id,
                reminder = reminderEntityReminderMapper.mapFrom(from.reminder),
                name = from.name
        )
    }
}
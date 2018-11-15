package com.morphiumdeus.domain.common

import com.morphiumdeus.domain.entities.CategoryEntity
import com.morphiumdeus.domain.entities.ChoiceEntity
import com.morphiumdeus.domain.entities.LogEntity
import com.morphiumdeus.domain.entities.ReminderEntity
import java.util.*

/**
 * Created by Yossi Segev on 09/02/2018.
 */
class DomainTestUtils {

    companion object {
        fun getTestLogEntity(id: Int): LogEntity {
            return LogEntity(
                    id = id,
                    categoryId = 0,
                    choice = getTestChoiceEntity(0),
                    timestamp = Calendar.getInstance()
                    )
        }
        
        fun getTestReminderEntity(): ReminderEntity {
            return ReminderEntity(
                    date = "03/11/2018",
                    time = "22:09",
                    repeat = true,
                    repeatNo = 8,
                    repeatType = "Hour",
                    active = false
            )
        }

        fun getTestChoiceEntity(id: Int): ChoiceEntity {
            return ChoiceEntity(
                    id = id,
                    categoryId = 0,
                    name = "Choice $id",
                    time = Calendar.getInstance())
        }

        fun getTestCategoryEntity(id: Int): CategoryEntity {
            return CategoryEntity(
                    id = id,
                    name = "Choice $id",
                    reminder = getTestReminderEntity())
        }

        fun generateCategoryEntityList(): List<CategoryEntity> {
            return (0..4).map { getTestCategoryEntity(it) }
        }
        fun generateLogEntityList(): List<LogEntity> {
            return (0..4).map { getTestLogEntity(it) }
        }
        fun generateChoiceEntityList(): List<ChoiceEntity> {
            return (0..4).map { getTestChoiceEntity(it) }
        }
    }
}
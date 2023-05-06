package dk.ravnely.chicken_data.dto.input

import dk.ravnely.chicken_data.entity.GroupByUnit

enum class GroupByUnitInput {
  DAY,
  MONTH,
  YEAR
}

fun GroupByUnitInput.toInternal(): GroupByUnit {
  return when (this) {
    GroupByUnitInput.DAY -> GroupByUnit.DAILY
    GroupByUnitInput.MONTH -> GroupByUnit.MONTHLY
    GroupByUnitInput.YEAR -> GroupByUnit.YEARLY
  }
}

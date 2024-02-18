package com.mrozowski.softwarepartnertask.domain

import com.mrozowski.softwarepartnertask.domain.model.Attendance
import com.mrozowski.softwarepartnertask.domain.model.CalculationResult
import com.mrozowski.softwarepartnertask.domain.model.Parent
import com.mrozowski.softwarepartnertask.domain.model.School

import java.time.OffsetDateTime
import java.time.ZoneOffset

class Fixtures {

  static def SCHOOL_ID = 1l
  static def PARENT_ID = 1l
  static def CHILD_ID = 1l
  static def ATTENDANCE_ID = 1l
  static def PARENT_FIST_NAME = "John"
  static def PARENT_LAST_NAME = "Kowalski"
  static def CHILD_FIRST_NAME = "Marek"
  static def SCHOOL_NAME = "SchoolOfRock"
  static def HOUR_PRICE_IN_CENT = 250
  static def TOTAL_SPEND_HOURS = 7
  static def TOTAL_PAYED_HOURS = 2
  static def TOTAL_COST = 500

  static def SCHOOL = new School(SCHOOL_ID, SCHOOL_NAME, HOUR_PRICE_IN_CENT)

  static def CHILD = Parent.Child.builder()
      .id(CHILD_ID)
      .firstname(CHILD_FIRST_NAME)
      .lastName(PARENT_LAST_NAME)
      .school(SCHOOL)
      .build()

  static def PARENT = Parent.builder()
      .id(PARENT_ID)
      .firstname(PARENT_FIST_NAME)
      .lastname(PARENT_LAST_NAME)
      .children([CHILD])
      .build()


  static def START_DATE = OffsetDateTime.of(2024, 02, 01, 0, 0, 0, 0, ZoneOffset.UTC)
  static def END_DATE = OffsetDateTime.of(2024, 02, 28, 23, 59, 59, 9999, ZoneOffset.UTC)

  static def ENTRY_DATE = createDefaultDatetimeFromTime(7, 00)
  static def EXIT_DATE = createDefaultDatetimeFromTime(14, 00)

  static def ATTENDANCE = Attendance.builder()
      .id(ATTENDANCE_ID)
      .childId(CHILD_ID)
      .entryDate(ENTRY_DATE)
      .exitDate(EXIT_DATE)
      .build()

  static def CALCULATION_RESULT = CalculationResult.builder()
      .totalSpendHours(TOTAL_SPEND_HOURS)
      .totalCost(TOTAL_COST)
      .payedHours(TOTAL_PAYED_HOURS)
      .build()

  static OffsetDateTime createDefaultDatetimeFromTime(int hour, int minute) {
    OffsetDateTime.of(2024, 2, 1, hour, minute, 0, 0, ZoneOffset.UTC)
  }
}

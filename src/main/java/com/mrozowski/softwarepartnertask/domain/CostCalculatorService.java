package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.model.Attendance;
import com.mrozowski.softwarepartnertask.domain.model.CalculationResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
class CostCalculatorService {

  private LocalTime startFreeTime = LocalTime.of(7, 0);
  private LocalTime endFreeTime = LocalTime.of(12, 0);

  CalculationResult calculate(List<Attendance> attendanceList, int hourCostInCents) {
    var totalSpendHours = 0;
    var totalPayedHours = 0;

    for (var attendance : attendanceList) {
      var entryTime = attendance.entryDate().toLocalTime();
      var existTime = attendance.exitDate().toLocalTime();

      totalPayedHours += getPayedHours(entryTime, existTime);
      totalSpendHours += getHoursBetweenTimes(entryTime, existTime);
    }

    var totalCost = hourCostInCents * totalPayedHours;
    return CalculationResult.builder()
        .totalSpendHours(totalSpendHours)
        .payedHours(totalPayedHours)
        .totalCost(totalCost)
        .build();
  }

  private int getPayedHours(LocalTime entryTime, LocalTime existTime) {
    var payedHours = 0;

    if (entryTime.isBefore(startFreeTime)) {
      payedHours += getHoursBetweenTimes(entryTime, startFreeTime);
    }

    if (existTime.isAfter(endFreeTime)) {
      payedHours += getHoursBetweenTimes(endFreeTime, existTime);
    }

    return payedHours;
  }

  private int getHoursBetweenTimes(LocalTime enterTime, LocalTime exitTime) {
    return exitTime.getHour() - enterTime.getHour() + (exitTime.getMinute() > 0 ? 1 : 0);
  }
}

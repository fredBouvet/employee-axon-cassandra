package fr.fbouvet.employee.api.query;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record FindEmployeeByBirthDateQuery(LocalDate birthDate) {
}

package fr.fbouvet.employee.api.query;

import lombok.Builder;

import java.util.UUID;


@Builder
public record FindEmployeeByIdQuery(UUID id) {
}

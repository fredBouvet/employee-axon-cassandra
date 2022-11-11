package fr.fbouvet.employee.api.query;

import lombok.Builder;


@Builder
public record FindEmployeeByNameQuery(String name) {
}

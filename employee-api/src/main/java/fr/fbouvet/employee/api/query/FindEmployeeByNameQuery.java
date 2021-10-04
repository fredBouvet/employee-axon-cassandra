package fr.fbouvet.employee.api.query;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindEmployeeByNameQuery {

  String name;
}

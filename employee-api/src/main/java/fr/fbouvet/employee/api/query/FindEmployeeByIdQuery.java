package fr.fbouvet.employee.api.query;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindEmployeeByIdQuery {

  UUID id;
}

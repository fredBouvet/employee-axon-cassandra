package fr.fbouvet.employee.domain.query.queryhandlers;

import fr.fbouvet.employee.api.query.model.EmployeeView;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeViewQueryRepository {

  Optional<EmployeeView> findById(UUID id);

  List<EmployeeView> findByName(String name);
}

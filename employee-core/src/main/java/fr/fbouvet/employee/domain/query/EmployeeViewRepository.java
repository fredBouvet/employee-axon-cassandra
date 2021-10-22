package fr.fbouvet.employee.domain.query;

import fr.fbouvet.employee.api.query.model.EmployeeView;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeViewRepository {

  void insertEmployee(EmployeeView employeeView);

  void changeName(UUID id, String name);

  Optional<EmployeeView> findById(UUID id);

  List<EmployeeView> findByName(String name);

  List<EmployeeView> findByBirthDate(LocalDate birthDate);
}

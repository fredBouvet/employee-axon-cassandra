package fr.fbouvet.employee.domain.query.projectors;

import fr.fbouvet.employee.api.query.model.EmployeeView;

public interface EmployeeViewProjectorRepository {

  void insertEmployee(EmployeeView employeeView);

}

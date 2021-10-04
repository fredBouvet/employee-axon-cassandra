package fr.fbouvet.employee.domain.query.queryhandlers;

import fr.fbouvet.employee.api.query.FindEmployeeByIdQuery;
import fr.fbouvet.employee.api.query.FindEmployeeByNameQuery;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.domain.query.EmployeeViewRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeQueryHandlers {

  private final EmployeeViewRepository employeeViewRepository;

  @QueryHandler
  public Optional<EmployeeView> findById(FindEmployeeByIdQuery query) {
    return employeeViewRepository.findById(query.getId());
  }

  @QueryHandler
  public List<EmployeeView> findByName(FindEmployeeByNameQuery query) {
    return employeeViewRepository.findByName(query.getName());
  }
}

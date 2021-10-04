package fr.fbouvet.employee.domain.query.queryhandlers;

import fr.fbouvet.employee.api.query.FindEmployeeByIdQuery;
import fr.fbouvet.employee.api.query.FindEmployeeByNameQuery;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeQueryHandlers {

  private final EmployeeViewQueryRepository employeeViewQueryRepository;

  @QueryHandler
  public Optional<EmployeeView> findById(FindEmployeeByIdQuery query) {
    return employeeViewQueryRepository.findById(query.getId());
  }

  @QueryHandler
  public List<EmployeeView> findByName(FindEmployeeByNameQuery query) {
    return employeeViewQueryRepository.findByName(query.getName());
  }
}

package fr.fbouvet.employee.domain.query.queryhandlers;

import fr.fbouvet.employee.api.query.FindEmployeeByBirthDateQuery;
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

  private final EmployeeViewRepository repository;

  @QueryHandler
  public Optional<EmployeeView> findById(FindEmployeeByIdQuery query) {
    return repository.findById(query.id());
  }

  @QueryHandler
  public List<EmployeeView> findByName(FindEmployeeByNameQuery query) {
    return repository.findByName(query.name());
  }

  @QueryHandler
  public List<EmployeeView> findByBirthDate(FindEmployeeByBirthDateQuery query) {
    return repository.findByBirthDate(query.birthDate());
  }

}

package fr.fbouvet.employee.domain.query.projectors;

import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.domain.query.EmployeeViewRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeProjector {

  private final EmployeeViewRepository repository;

  @EventHandler
  public void onEmployeeCreated(EmployeeCreatedEvent employeeCreatedEvent) {

    repository.insertEmployee(
        EmployeeView.builder()
            .id(employeeCreatedEvent.getId())
            .name(employeeCreatedEvent.getName())
            .address(employeeCreatedEvent.getAddress())
            .email(employeeCreatedEvent.getEmail())
            .birthDate(employeeCreatedEvent.getBirthDate())
            .build()
    );
  }
}

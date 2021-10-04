package fr.fbouvet.employee.domain.query.projectors;

import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeProjector {

  private final EmployeeViewProjectorRepository repository;

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

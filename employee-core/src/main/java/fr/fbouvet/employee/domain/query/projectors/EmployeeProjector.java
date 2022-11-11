package fr.fbouvet.employee.domain.query.projectors;

import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.event.EmployeeNameChangedEvent;
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
            .id(employeeCreatedEvent.id())
            .name(employeeCreatedEvent.name())
            .address(employeeCreatedEvent.address())
            .email(employeeCreatedEvent.email())
            .birthDate(employeeCreatedEvent.birthDate())
            .build()
    );
  }

  @EventHandler
  public void onNameChanged(EmployeeNameChangedEvent event) {
    repository.changeName(event.id(), event.name());
  }
}

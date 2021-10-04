package fr.fbouvet.employee.application.rest;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf;
import static org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf;

import fr.fbouvet.employee.api.command.CreateEmployeeCommand;
import fr.fbouvet.employee.api.query.FindEmployeeByIdQuery;
import fr.fbouvet.employee.api.query.FindEmployeeByNameQuery;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.application.rest.dto.EmployeeCreationDto;
import fr.fbouvet.employee.application.rest.dto.EmployeeDto;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final ReactorQueryGateway queryGateway;
  private final ReactorCommandGateway commandGateway;

  @PostMapping
  public Mono<UUID> createEmployee(@RequestBody EmployeeCreationDto employeeCreationDto) {

    return commandGateway.send(
        CreateEmployeeCommand.builder()
            .id(randomUUID())
            .name(employeeCreationDto.getName())
            .address(employeeCreationDto.getAddress())
            .email(employeeCreationDto.getEmail())
            .birthDate(employeeCreationDto.getBirthDate())
            .build()
    );
  }

  @GetMapping("/{id}")
  public Mono<EmployeeDto> findById(@PathVariable UUID id) {

    return queryGateway.query(
        FindEmployeeByIdQuery.builder().id(id).build(),
        instanceOf(EmployeeView.class)
    ).map(this::toDto);
  }

  @GetMapping("/search/by-name/{name}")
  public Mono<List<EmployeeDto>> findByName(@PathVariable String name) {

    return queryGateway.query(
        FindEmployeeByNameQuery.builder().name(name).build(),
        multipleInstancesOf(EmployeeView.class)
    ).map(employees -> employees.stream()
        .map(this::toDto)
        .collect(toList())
    );
  }

  private EmployeeDto toDto(EmployeeView employeeView) {
    return EmployeeDto.builder()
        .id(employeeView.getId())
        .name(employeeView.getName())
        .address(employeeView.getAddress())
        .email(employeeView.getEmail())
        .birthDate(employeeView.getBirthDate())
        .build();
  }
}

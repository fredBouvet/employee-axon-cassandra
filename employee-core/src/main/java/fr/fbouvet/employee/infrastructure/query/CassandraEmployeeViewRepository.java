package fr.fbouvet.employee.infrastructure.query;

import static java.util.stream.Collectors.toList;

import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.domain.query.EmployeeViewRepository;
import fr.fbouvet.employee.infrastructure.query.byid.EmployeeById;
import fr.fbouvet.employee.infrastructure.query.byid.EmployeeByIdRepository;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeByName;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeByNameRepository;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeKeyByName;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class CassandraEmployeeViewRepository implements EmployeeViewRepository {

  private final EmployeeByIdRepository repositoryById;
  private final EmployeeByNameRepository repositoryByName;

  @Override
  public void insertEmployee(EmployeeView employeeView) {
    repositoryById.insert(toEmployeeById(employeeView));
    repositoryByName.insert(toEmployeeByName(employeeView));
  }

  @Override
  public void changeName(UUID id, String previousName, String name) {

    repositoryById.insert(EmployeeById.builder().id(id).name(name).build());

    repositoryByName.findById(
        EmployeeKeyByName.builder()
            .id(id)
            .name(previousName)
            .build()
    ).ifPresent(employeeByName -> {
          repositoryByName.delete(employeeByName);
          repositoryByName.insert(
              EmployeeByName.builder()
                  .key(EmployeeKeyByName.builder().id(id).name(name).build())
                  .address(employeeByName.getAddress())
                  .email(employeeByName.getEmail())
                  .birthDate(employeeByName.getBirthDate())
                  .build()
          );
        }
    );
  }

  @Override
  public Optional<EmployeeView> findById(UUID id) {
    return repositoryById.findById(id).map(this::toView);
  }

  @Override
  public List<EmployeeView> findByName(String name) {
    return repositoryByName.findByName(name).stream()
        .map(this::toView)
        .collect(toList());
  }

  private EmployeeView toView(EmployeeById employeeById) {

    return EmployeeView.builder()
        .id(employeeById.getId())
        .name(employeeById.getName())
        .address(employeeById.getAddress())
        .email(employeeById.getEmail())
        .birthDate(employeeById.getBirthDate())
        .build();
  }

  private EmployeeById toEmployeeById(EmployeeView employeeView) {

    return EmployeeById.builder()
        .id(employeeView.getId())
        .name(employeeView.getName())
        .address(employeeView.getAddress())
        .email(employeeView.getEmail())
        .birthDate(employeeView.getBirthDate())
        .build();
  }

  private EmployeeView toView(EmployeeByName employeeByName) {

    return EmployeeView.builder()
        .id(employeeByName.getKey().getId())
        .name(employeeByName.getKey().getName())
        .address(employeeByName.getAddress())
        .email(employeeByName.getEmail())
        .birthDate(employeeByName.getBirthDate())
        .build();
  }

  private EmployeeByName toEmployeeByName(EmployeeView employeeView) {

    return EmployeeByName.builder()
        .key(
            EmployeeKeyByName.builder()
                .id(employeeView.getId())
                .name(employeeView.getName())
                .build()
        )
        .address(employeeView.getAddress())
        .email(employeeView.getEmail())
        .birthDate(employeeView.getBirthDate())
        .build();
  }
}

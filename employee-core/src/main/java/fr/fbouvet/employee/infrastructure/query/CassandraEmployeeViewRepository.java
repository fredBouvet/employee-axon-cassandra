package fr.fbouvet.employee.infrastructure.query;

import static java.util.stream.Collectors.toList;

import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.domain.query.EmployeeViewRepository;
import fr.fbouvet.employee.infrastructure.query.bybirthdate.EmployeeByBirthDate;
import fr.fbouvet.employee.infrastructure.query.bybirthdate.EmployeeByBirthDateRepository;
import fr.fbouvet.employee.infrastructure.query.bybirthdate.EmployeeKeyByBirthDate;
import fr.fbouvet.employee.infrastructure.query.byid.EmployeeById;
import fr.fbouvet.employee.infrastructure.query.byid.EmployeeByIdRepository;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeByName;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeByNameRepository;
import fr.fbouvet.employee.infrastructure.query.byname.EmployeeKeyByName;
import java.time.LocalDate;
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
  private final EmployeeByBirthDateRepository repositoryByBirthDate;

  @Override
  public void insertEmployee(EmployeeView employeeView) {
    repositoryById.insert(toEmployeeById(employeeView));
    repositoryByName.insert(toEmployeeByName(employeeView));
    repositoryByBirthDate.insert(toEmployeeByBirthDate(employeeView));
  }

  @Override
  public void changeName(UUID id, String name) {

    repositoryById.findById(id).ifPresent(
        employee -> {

          repositoryById.insert(EmployeeById.builder().id(id).name(name).build());

          repositoryByName.deleteById(
              EmployeeKeyByName.builder().id(id).name(employee.getName()).build()
          );

          repositoryByName.insert(
              EmployeeByName.builder()
                  .key(EmployeeKeyByName.builder().id(id).name(name).build())
                  .address(employee.getAddress())
                  .email(employee.getEmail())
                  .birthDate(employee.getBirthDate())
                  .build()
          );

          repositoryByBirthDate.insert(
              EmployeeByBirthDate.builder()
                  .key(
                      EmployeeKeyByBirthDate.builder()
                          .id(id)
                          .birthDate(employee.getBirthDate())
                          .build()
                  )
                  .name(name)
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
    return repositoryByName.findByName(name)
        .map(this::toView)
        .collect(toList());
  }

  @Override
  public List<EmployeeView> findByBirthDate(LocalDate birthDate) {
    return repositoryByBirthDate.findByBirthDate(birthDate)
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

  private EmployeeView toView(EmployeeByBirthDate employeeByBirthDate) {

    return EmployeeView.builder()
        .id(employeeByBirthDate.getKey().getId())
        .birthDate(employeeByBirthDate.getKey().getBirthDate())
        .name(employeeByBirthDate.getName())
        .address(employeeByBirthDate.getAddress())
        .email(employeeByBirthDate.getEmail())
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

  private EmployeeByBirthDate toEmployeeByBirthDate(EmployeeView employeeView) {

    return EmployeeByBirthDate.builder()
        .key(EmployeeKeyByBirthDate.builder()
            .birthDate(employeeView.getBirthDate())
            .id(employeeView.getId())
            .build()
        )
        .name(employeeView.getName())
        .address(employeeView.getAddress())
        .email(employeeView.getEmail())
        .build();
  }
}

package fr.fbouvet.employee.domain.query.queryhandlers;

import fr.fbouvet.employee.api.query.FindEmployeeByBirthDateQuery;
import fr.fbouvet.employee.api.query.FindEmployeeByIdQuery;
import fr.fbouvet.employee.api.query.FindEmployeeByNameQuery;
import fr.fbouvet.employee.api.query.model.EmployeeView;
import fr.fbouvet.employee.api.query.model.EmployeeViews;
import fr.fbouvet.employee.domain.query.EmployeeViewRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeQueryHandlers {

    private final EmployeeViewRepository repository;

    @QueryHandler
    public Optional<EmployeeView> findById(FindEmployeeByIdQuery query) {
        return repository.findById(query.id());
    }

    @QueryHandler
    public EmployeeViews findByName(FindEmployeeByNameQuery query) {
        List<EmployeeView> employees = repository.findByName(query.name());
        return new EmployeeViews(employees != null ? employees : new ArrayList<>());
    }

    @QueryHandler
    public EmployeeViews findByBirthDate(FindEmployeeByBirthDateQuery query) {
        List<EmployeeView> employees = repository.findByBirthDate(query.birthDate());
        return new EmployeeViews(employees != null ? employees : new ArrayList<>());
    }

}

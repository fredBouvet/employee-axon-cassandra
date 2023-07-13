package fr.fbouvet.employee.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path.Node;
import jakarta.validation.Path.ParameterNode;
import jakarta.validation.Validation;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.Set;

import static jakarta.validation.ElementKind.PARAMETER;
import static java.util.stream.Collectors.joining;


public class Validator {

    private static final jakarta.validation.Validator defaultValidator =
            Validation.buildDefaultValidatorFactory().getValidator();

    private Validator() {
    }

    public static <T> void validate(Class<T> clazz, Object... args) {

        Set<ConstraintViolation<T>> violations = defaultValidator.forExecutables()
                .validateConstructorParameters((Constructor<T>) clazz.getDeclaredConstructors()[0], args);
        if (!violations.isEmpty()) {

            throw new IllegalArgumentException(
                    violations.stream()
                            .sorted(Comparator.comparingInt(Validator::getParameterIndex))
                            .map(violation -> getParameterName(violation) + " - " + violation.getMessage())
                            .collect(joining(System.lineSeparator()))
            );
        }
    }

    private static String getParameterName(ConstraintViolation<?> cv) {
        for (Node node : cv.getPropertyPath()) {
            if (node.getKind() == PARAMETER) {
                return node.getName();
            }
        }

        return "";
    }

    private static int getParameterIndex(ConstraintViolation<?> cv) {
        for (Node node : cv.getPropertyPath()) {
            if (node.getKind() == PARAMETER) {
                return node.as(ParameterNode.class).getParameterIndex();
            }
        }

        return -1;
    }
}

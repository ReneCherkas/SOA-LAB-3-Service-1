package ru.soa_3.first_service.service;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import ru.soa_3.first_service.model.Flat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlatSpecification {

    public static <T extends Comparable<? super T>> Specification<Flat> filterBy(String field, String operator, String value) {
        return (root, query, criteriaBuilder) -> {
            // Обработка вложенных полей (например, coordinates.x или house.name)
            String[] fieldParts = field.split("\\.");
            Path<?> path = root.get(fieldParts[0]);
            for (int i = 1; i < fieldParts.length; i++) {
                path = path.get(fieldParts[i]);
            }

            // Приведение типа к Path<T>, чтобы использовать методы сравнения
            Path<T> comparablePath = (Path<T>) path;
            T comparableValue = (T) parseValue(path.getJavaType(), value);

            switch (operator) {
                case "=":
                    return criteriaBuilder.equal(comparablePath, comparableValue);
                case "!=":
                    return criteriaBuilder.notEqual(comparablePath, comparableValue);
                case ">":
                    return criteriaBuilder.greaterThan(comparablePath, comparableValue);
                case "<":
                    return criteriaBuilder.lessThan(comparablePath, comparableValue);
                case ">=":
                    return criteriaBuilder.greaterThanOrEqualTo(comparablePath, comparableValue);
                case "<=":
                    return criteriaBuilder.lessThanOrEqualTo(comparablePath, comparableValue);
                default:
                    return null;
            }
        };
    }

    public static Specification<Flat> buildFilters(List<String> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String filter : filters) {
                filter = filter.trim();

                String operator = null;
                if (filter.contains("!=")) {
                    operator = "!=";
                } else if (filter.contains(">=")) {
                    operator = ">=";
                } else if (filter.contains("<=")) {
                    operator = "<=";
                } else if (filter.contains("=")) {
                    operator = "=";
                } else if (filter.contains(">")) {
                    operator = ">";
                } else if (filter.contains("<")) {
                    operator = "<";
                }

                if (operator != null) {
                    String[] parts = filter.split(operator, 2);
                    if (parts.length < 2) {
                        continue;
                    }
                    String field = parts[0].trim();
                    String value = parts[1].trim();

                    predicates.add(filterBy(field, operator, value).toPredicate(root, query, criteriaBuilder));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Sort buildSort(List<String> sort) {
        List<Sort.Order> orders = sort.stream()
                .map(s -> s.startsWith("<") ? Sort.Order.desc(s.substring(1)) : Sort.Order.asc(s))
                .collect(Collectors.toList());
        return Sort.by(orders);
    }

    public static Specification<Flat> createSpecification(List<String> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }
        return FlatSpecification.buildFilters(filters);
    }

    // Преобразование строки в нужный тип данных
    private static Object parseValue(Class<?> fieldType, String value) {
        if (fieldType.isEnum()) {
            return Enum.valueOf((Class<Enum>) fieldType, value.toUpperCase());
        }
        if (fieldType.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (fieldType.equals(Float.class)) {
            return Float.parseFloat(value);
        }
        if (fieldType.equals(Double.class)) {
            return Double.parseDouble(value);
        }
        return value;
    }
}


package org.example.HR_ManagementSystem.dao.specifications;

import jakarta.persistence.criteria.*;
import org.example.HR_ManagementSystem.dao.entity.Employee;
import org.example.HR_ManagementSystem.dao.entity.Position;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeSpecifications {

    public Specification<Employee> filterByCriteria(EmployeeFilter filter) {
        return (root, query, criteriaBuilder) -> {
            // Создаем предикаты для фильтрации
            List<Predicate> predicates = new ArrayList<>();

            // Фильтрация по ФИО (если задано)
            if (filter.getFio() != null) {
                String fio = filter.getFio().toLowerCase();
                fio = fio.substring(0, 1).toUpperCase() + fio.substring(1);
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("lastName"), "%" + fio + "%"),
                        criteriaBuilder.like(root.get("firstName"), "%" + fio + "%"),
                        criteriaBuilder.like(root.get("middleName"), "%" + fio + "%")
                ));
            }

            // Фильтрация по дате начала (если задано)
            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), filter.getStartDate()));
            }

            // Фильтрация по дате окончания (если задано)
            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), filter.getEndDate()));
            }

            // Фильтрация по названию должности (если задано)
            if (filter.getPositionName() != null) {
                Join<Employee, Position> join = root.join("position", JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(join.get("name"), filter.getPositionName()));
            }

            // Фильтрация по признаку увольнения (если задано)
            if (filter.getTerminates() != null) {
                predicates.add(criteriaBuilder.equal(root.get("terminated"), filter.getTerminates()));
            }

            // Сортировка
            if (filter.getSort() != null) {
                query.orderBy(criteriaBuilder.asc(root.get("lastName")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Employee> withPosition() {
        return (root, query, cb) -> {
            if (!alreadyFetched(root, "position")) {
                root.fetch("position", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }

    private boolean alreadyFetched(From<?, ?> from, String attribute) {
        for (Fetch<?, ?> f : from.getFetches()) {
            if (f.getAttribute().getName().equals(attribute)) {
                return true;
            }
        }
        return false;
    }
}


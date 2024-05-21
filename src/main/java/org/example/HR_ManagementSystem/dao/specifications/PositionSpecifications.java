package org.example.HR_ManagementSystem.dao.specifications;

import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import org.example.HR_ManagementSystem.dao.entity.Position;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PositionSpecifications {

    public Specification<Position> withEmployees() {
        return (root, query, cb) -> {
            if (!alreadyFetched(root, "employees")) {
                root.fetch("employees", JoinType.LEFT);
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

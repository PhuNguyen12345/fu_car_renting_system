package com.mss301.car.infrastructure.specification;

import com.mss301.car.api.dto.AdminCarSearchCriteria;
import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.vo.CarStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AdminCarSpecification {
    public static Specification<Car> buildQuery(AdminCarSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // Admin không thấy xe bị soft delete
            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            if (criteria == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (StringUtils.hasText(criteria.getKeyword())) {
                Predicate nameMatch = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), 
                    "%" + criteria.getKeyword().toLowerCase() + "%"
                );
                Predicate plateMatch = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("licensePlate")), 
                    "%" + criteria.getKeyword().toLowerCase() + "%"
                );
                predicates.add(criteriaBuilder.or(nameMatch, plateMatch));
            }

            if (StringUtils.hasText(criteria.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), CarStatus.valueOf(criteria.getStatus())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.mss301.car.infrastructure.specification;

import com.mss301.car.api.dto.CarSearchCriteria;
import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.vo.CarStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CarSpecification {
    public static Specification<Car> buildQuery(CarSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Luôn luôn chỉ tìm xe chưa bị xoá (soft delete) và không bảo trì
            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            predicates.add(criteriaBuilder.notEqual(root.get("status"), CarStatus.MAINTENANCE));

            if (criteria == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (StringUtils.hasText(criteria.getKeyword())) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), 
                    "%" + criteria.getKeyword().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(criteria.getCity())) {
                predicates.add(criteriaBuilder.equal(
                    root.get("location").get("city"), 
                    criteria.getCity()
                ));
            }

            if (StringUtils.hasText(criteria.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type"), criteria.getType()));
            }

            if (StringUtils.hasText(criteria.getTransmission())) {
                predicates.add(criteriaBuilder.equal(root.get("transmission"), criteria.getTransmission()));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerDay"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerDay"), criteria.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

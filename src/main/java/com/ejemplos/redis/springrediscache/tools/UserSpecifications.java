package com.ejemplos.redis.springrediscache.tools;

import java.util.Optional;
import java.util.Set;

import com.ejemplos.redis.springrediscache.entities.User;
import com.ejemplos.redis.springrediscache.entities.User_;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public final class UserSpecifications {
    
    public static Specification<User> createUserSpecifications(UserSearchCriteria searchCriteria) {
        return salaryRateBetween(searchCriteria.getMinSalaryRate(), searchCriteria.getMaxSalaryRate())
                .and(ageEqualTo(searchCriteria.getAge()))        
                .and(namesIn(searchCriteria.getNames()));
    }

    public static Specification<User> salaryRateBetween(Optional<Float> minSalaryRank, Optional<Float> maxSalaryRank) {
        return (root, query, builder) -> {
            return minSalaryRank.map(min -> {
                return maxSalaryRank.map(max -> builder.between(root.get(User_.salary), min, max)).orElse(null);
            }).orElse(null);
        };
    }

    public static Specification<User> ageEqualTo(Optional<Integer> age) {
        return (root, query, builder) -> {
            return age.map(aAge -> builder.equal(root.get(User_.age), String.valueOf(aAge)))
                    .orElse(null);
        };
    }

    public static Specification<User> namesIn(Set<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            return null;
        }
        return (root, query, builder) -> {
            return root.get(User_.name).in(names);
        };
    }
}
package com.ejemplos.redis.springrediscache.tools;

import java.util.Optional;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSearchCriteria {
    
    private Set<String> names;
    private Optional<Integer> age;
    private Optional<Float> minSalaryRate;
    private Optional<Float> maxSalaryRate;

}

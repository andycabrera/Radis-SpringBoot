package com.ejemplos.redis.springrediscache.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Float> salary;
    public static volatile SingularAttribute<User, Integer> age;
}

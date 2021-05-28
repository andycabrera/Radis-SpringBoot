package com.ejemplos.redis.springrediscache.service;

import java.util.List;

import com.ejemplos.redis.springrediscache.entities.User;

public interface IUserServices   {
    
    List<User> findPaginated(int pageNo, int pageSize);
}

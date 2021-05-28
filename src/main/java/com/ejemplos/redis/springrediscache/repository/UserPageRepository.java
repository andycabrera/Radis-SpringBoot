package com.ejemplos.redis.springrediscache.repository;

import com.ejemplos.redis.springrediscache.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPageRepository extends PagingAndSortingRepository<User, Long>{
    
    /* Use only as example to log when call repository from service*/
    default public Page<User> findAllUsers(Pageable pageable)  {
    	System.out.println("Call repository to get all users pageable");
    	return this.findAll(pageable);
    }
}

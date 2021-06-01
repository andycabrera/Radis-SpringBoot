package com.ejemplos.redis.springrediscache.service;

import com.ejemplos.redis.springrediscache.config.CacheConfig;
import com.ejemplos.redis.springrediscache.entities.User;
import com.ejemplos.redis.springrediscache.repository.UserPageRepository;
import com.ejemplos.redis.springrediscache.repository.UserRepository;
import com.ejemplos.redis.springrediscache.tools.UserSearchCriteria;
import com.ejemplos.redis.springrediscache.tools.UserSpecifications;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserServices{

    private UserRepository userRepository;

    private UserPageRepository userPageRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserPageRepository userPageRepository) {
        this.userRepository = userRepository;
        this.userPageRepository = userPageRepository;
    }
    
    public List<User> retrieveUsers(UserSearchCriteria searchCriteria) {
        Specification<User> userSpecifications = UserSpecifications.createUserSpecifications(searchCriteria);
        return this.userRepository.findAll(userSpecifications);
    }

    @Override
    @Cacheable(cacheNames = CacheConfig.USERS_CACHE, key = "'andres' + #pageNo + #pageSize", unless = "#result == null")
    public List<User> findPaginated(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<User> pagedResult = userPageRepository.findAllUsers(paging);
        return pagedResult.toList();
    }
    
    public User save(User user) {
    	return this.userRepository.save(user);
    }

    @Cacheable(cacheNames = CacheConfig.USERS_CACHE, key= "0", unless = "#result == null")
    public List<User> findAll() {
        return this.userRepository.findAllUsers();
    }

    @Cacheable(cacheNames = CacheConfig.USER_CACHE, unless = "#result == null")
    public User findById(Long id) {
        return this.userRepository.findUser(id).orElse(null);
    }

    @CachePut(cacheNames = CacheConfig.USER_CACHE, key = "#id", unless = "#result == null")
    public User updateSalary(Long id, float salary) {
        int res = this.userRepository.updateSalary(id, salary);
        return res > 0 ? this.userRepository.findById(id).orElse(null): null;
    }

    @CacheEvict(cacheNames = CacheConfig.USER_CACHE, key = "#id")
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}

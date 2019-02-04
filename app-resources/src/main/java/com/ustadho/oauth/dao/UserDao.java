package com.ustadho.oauth.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ustadho.oauth.domain.User;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, String> {
    Optional<User> findOneByUsername(String username);

//    @Override
//    @Query("from User u " +
//            "join u.role r " +
//            "left join fetch r.permissionSet ps ")
//    Page<User> findAll(Pageable pageable);
}
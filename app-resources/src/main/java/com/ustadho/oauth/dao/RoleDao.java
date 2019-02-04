package com.ustadho.oauth.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ustadho.oauth.domain.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, String> {
    Optional<Role> findOneByName(String name);

}

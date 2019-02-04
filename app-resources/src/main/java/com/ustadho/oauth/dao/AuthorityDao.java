package com.ustadho.oauth.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ustadho.oauth.domain.Authority;

public interface AuthorityDao extends PagingAndSortingRepository<Authority, String> {
    public Optional<Authority> findOneByName(String name);

}
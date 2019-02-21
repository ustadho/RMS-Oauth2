package com.mitrais.rms.resource.repository;

import com.mitrais.rms.resource.domain.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
}

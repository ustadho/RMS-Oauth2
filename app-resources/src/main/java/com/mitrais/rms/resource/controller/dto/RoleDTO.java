package com.mitrais.rms.resource.controller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.mitrais.rms.resource.domain.Permission;
import com.mitrais.rms.resource.domain.Role;

import lombok.Data;

@Data
public class RoleDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private Set<Permission> permissions;

    public RoleDTO(){}

    public RoleDTO(Role role){
        this(role.getId(), role.getName(),  role.getPermissions());
    }
    public RoleDTO(Long id, String name, List<Permission> as){
        this.id = id;
        this.name = name;
//        ps.size();
//        this.permissions = as;
//        this.permissions = ps.stream().map(permission -> {
//          Permission p = new Permission();
//          p.setId(permission.getId());
//          p.setLabel(permission.getLabel());
//          p.setValue(permission.getValue());
//          p.setRoles(null);
//          return p;
//        }).collect(Collectors.toSet());
    }
}

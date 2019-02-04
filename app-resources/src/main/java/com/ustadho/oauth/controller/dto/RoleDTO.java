package com.ustadho.oauth.controller.dto;

import java.io.Serializable;
import java.util.Set;

import com.ustadho.oauth.domain.Authority;
import com.ustadho.oauth.domain.Role;

import lombok.Data;

@Data
public class RoleDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String name;
    private String description;
    private Set<Authority> authorities;

    public RoleDTO(){}

    public RoleDTO(Role role){
        this(role.getId(), role.getName(), role.getDescription(), role.getAuthorities());
    }
    public RoleDTO(String id, String name, String description, Set<Authority> as){
        this.id = id;
        this.name = name;
        this.description = description;
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

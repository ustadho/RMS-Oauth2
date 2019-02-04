package com.ustadho.oauth.controller.dto;
import com.ustadho.oauth.domain.Role;
import com.ustadho.oauth.domain.User;

import lombok.Data;


@Data
public class UserDTO {
    private String id;
    private String username;
    private Boolean active;
    private Role role;

    public UserDTO(){}

    public UserDTO(User u){
        this(u.getId(), u.getUsername(), u.getActive(), u.getRole());
    }
    public UserDTO(String id, String username, Boolean active, Role role){
        this.id = id;
        this.username = username;
        this.active = active;
        //this.role = role;
        this.role = new Role(role.getId(), role.getName(), role.getDescription());
//        this.role = new Role(role.getId(), role.getName(), role.getDescription(),
//                role.getPermissionSet().stream().map(p -> {
//                    Permission permission = new Permission();
//                    permission.setId(p.getId());
//                    permission.setLabel(p.getLabel());
//                    permission.setValue(p.getValue());
//                    return  permission;
//                }).collect(Collectors.toSet()));
    }
}
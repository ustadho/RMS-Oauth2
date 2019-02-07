package com.mitrais.rms.resource.controller.dto;
import java.util.ArrayList;
import java.util.List;

import com.mitrais.rms.resource.domain.Role;
import com.mitrais.rms.resource.domain.User;

import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String username;
    private Boolean active;
    private List<Role> roles = new ArrayList<>();

    public UserDTO(){}

    public UserDTO(User u){
        this(u.getId(), u.getUsername(), u.isEnabled(), u.getRoles());
    }
    public UserDTO(Long id, String username, Boolean enabled, List<Role> roles){
        this.id = id;
        this.username = username;
        this.active = enabled;
        /*for(Role r: roles) {
        	roles.add(new Role(r.getId(), r.getName()));
        }*/
        
    }
}
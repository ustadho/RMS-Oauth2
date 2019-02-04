package com.ustadho.oauth.domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "c_role")
@Data
public class Role {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name="c_role_authority",
            joinColumns=@JoinColumn(name="role_id", nullable=false),
            inverseJoinColumns=@JoinColumn(name="authority_id", nullable=false)
    )
    private Set<Authority> authorities = new HashSet<Authority>();

    public Role(){}

    public Role(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role(String id, String name, String description, Set<Authority> auth){
        this.id = id;
        this.name = name;
        this.description = description;
        this.authorities = auth;
    }
}

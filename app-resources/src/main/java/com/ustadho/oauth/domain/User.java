package com.ustadho.oauth.domain;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "c_user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}

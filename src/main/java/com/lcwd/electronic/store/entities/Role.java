package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    private String roleId;
    private String roleName;
//    @ManyToMany(mappedBy = "roles")
//    private Set Users;
}
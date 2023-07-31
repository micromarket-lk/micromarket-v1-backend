package com.sahan.core.Entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable, UserDetails {
    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private String id;

    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "username is mandatory")
    private String userName;
    @NotBlank(message = "profileImageUrl is mandatory")
    private String profileImageUrl;
    @NotBlank(message = "lastLoginDate is mandatory")
    private Date lastLoginDate;
    @NotBlank(message = "lastLoginDateDisplay is mandatory")
    private Date lastLoginDateDisplay;
    @NotBlank(message = "joinDate is mandatory")
    private Date joinDate;
    @NotBlank(message = "role is mandatory")
    private String role;
    @NotBlank(message = "authorities are mandatory")
    private String[] authorities;
    @NotBlank(message = "authorities is mandatory")
    private boolean C;
    @NotBlank(message = "isNotLocked is mandatory")
    private boolean isNotLocked;

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

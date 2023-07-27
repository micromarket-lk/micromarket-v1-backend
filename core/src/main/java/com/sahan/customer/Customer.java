package com.sahan.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer implements Serializable {
    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    private Integer id;

    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotBlank(message = "email is mandatory")
    private String email;
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
}

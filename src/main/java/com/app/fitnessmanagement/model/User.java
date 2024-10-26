package com.app.fitnessmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name")
    @NotBlank(message = "Full name should not be blank")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email should not be blank")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be blank")
    private String password;

    @Column(name = "age")
    @NotNull(message = "Age should not be blank")
    private Integer age;

    @Column(name = "height")
    @NotNull(message = "Height should not be blank")
    private Integer height;

    @Column(name = "weight")
    @NotNull(message = "Weight should not be blank")
    private Integer weight;

    @Column(name = "gender")
    @NotBlank(message = "Gender should not be blank")
    private String gender;

    @Column(name = "role")
    private String role;
}

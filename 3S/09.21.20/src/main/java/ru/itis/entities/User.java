package ru.itis.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String login;
    private String password;
    private String UUID;

}

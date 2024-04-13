package com.withSchool.DTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignUpDTO {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private Boolean sex;
    private String phoneNumber;
    private String address;
    private LocalDateTime birthDate;
    private int accountType;
    private String userCode;
    private String parentCode;
}
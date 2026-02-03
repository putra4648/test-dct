package com.example.test_dct.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class ContactDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private List<GroupDTO> groups;
    private Long userId;
}

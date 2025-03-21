package com.reserve.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class createMember {

    private String userId;
    private String userName;
    private String email;
    private String phone;
    private int level;
    private int point;

}

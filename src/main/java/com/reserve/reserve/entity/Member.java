package com.reserve.reserve.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 
@Table(name = "MEMBER_TBL")
@Builder
public class Member {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "LEVEL", nullable = false)
    @ColumnDefault("0")
    private int level;

    @Column(name = "POINT", nullable = true)
    @ColumnDefault("0")
    private int point;

    public void spendPoint(int point){
        this.point -= point;
    }

    public void returnPoint(int point){
        this.point += point;
    }

}

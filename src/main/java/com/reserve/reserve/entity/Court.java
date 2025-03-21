package com.reserve.reserve.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import com.reserve.reserve.dto.ModifyCourt;
import com.reserve.reserve.util.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "courts")
@Getter
@DynamicUpdate // 변경한 필드만 대응
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SportType sportType;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private int capacity;

    @Comment("true : 실내, false : 야외")
    @Column(columnDefinition = "TINYINT(1) default 1")
    private boolean indoor;

    @Comment("true : 활성화, false : 비활성화")
    @Column(columnDefinition = "TINYINT(1) default true")
    private boolean active;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_IDX")
    private Facility facility;

    public void modifyCourt(ModifyCourt modifyCourt){
        this.name = modifyCourt.getName();
        this.sportType = modifyCourt.getSportType();
        this.description = modifyCourt.getDescription();
        this.capacity = modifyCourt.getCapacity();
        this.indoor = modifyCourt.isIndoor();
        this.active = modifyCourt.isActive();
        this.updateAt = Utils.dateFormat(LocalDateTime.now());
    }

}

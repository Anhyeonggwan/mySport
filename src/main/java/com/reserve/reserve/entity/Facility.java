package com.reserve.reserve.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.reserve.reserve.util.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "FACILITY_TBL")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FACILITY_IDX") 
    private Long idx;
    
    @Column(name = "ADRESS", nullable = false)
    private String adress;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IS_NEW", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("true")
    private boolean isNew;

    @Column(name = "content", nullable = true, columnDefinition = "text")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    // @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<FacilityImage> facilityImages = new ArrayList<>();

    public Facility(String address, String name) {
        this.adress = address;
        this.name = name;
        this.createDate = Utils.dateFormat(LocalDateTime.now());
    }

    public void modifyFacility(String name, String adress, String content){
        this.name = name;
        this.adress = adress;
        this.content = content;
        this.updateDate = Utils.dateFormat(LocalDateTime.now());
    }
}

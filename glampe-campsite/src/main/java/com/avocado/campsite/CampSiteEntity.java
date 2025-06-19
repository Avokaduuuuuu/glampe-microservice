package com.avocado.campsite;

import com.avocado.campsite.enums.CampSiteStatusEnum;
import com.avocado.camptype.CampTypeEntity;
import com.avocado.gallery.CampSiteGalleryEntity;
import com.avocado.placetype.PlaceTypeEntity;
import com.avocado.report.ReportEntity;
import com.avocado.selection.SelectionEntity;
import com.avocado.utility.UtilityEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "camp_site")
@EntityListeners(AuditingEntityListener.class)
public class CampSiteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "city")
    String city;

    @Column(name = "latitude")
    Double latitude;

    @Column(name = "longitude")
    Double longitude;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    CampSiteStatusEnum status;

    @Column(name = "message")
    @Builder.Default
    String message = "";

    @Column(name = "deposit_rate")
    Double depositRate;

    @Column(name = "description")
    String description = "";

    @Column(name = "id_user")
    Long userId;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "campSite", cascade = CascadeType.ALL)
    List<CampSiteGalleryEntity> galleries;

    @OneToMany(mappedBy = "campSite", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CampTypeEntity> campTypes;

    @ManyToMany
    @JoinTable(
            name = "camp_site_utility",
            joinColumns = @JoinColumn(name = "id_camp_site"),
            inverseJoinColumns = @JoinColumn(name = "id_utility")
    )
    List<UtilityEntity> utilities;

    @ManyToMany
    @JoinTable(
            name = "camp_site_place_type",
            joinColumns = @JoinColumn(name = "id_camp_site"),
            inverseJoinColumns = @JoinColumn(name = "id_place_type")
    )
    List<PlaceTypeEntity> placeTypes;

    @OneToMany(mappedBy = "campSite")
    List<ReportEntity> reports;

    @OneToMany(mappedBy = "campSite", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SelectionEntity> selections;
}

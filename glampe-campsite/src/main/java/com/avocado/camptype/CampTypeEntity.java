package com.avocado.camptype;

import com.avocado.camp.CampEntity;
import com.avocado.campsite.CampSiteEntity;
import com.avocado.customprice.CustomPriceEntity;
import com.avocado.facility.FacilityEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "camp_type")
@EntityListeners(AuditingEntityListener.class)
public class CampTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "type")
    String type;

    @Column(name = "capacity")
    Integer capacity;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "weekend_price")
    BigDecimal weekendPrice;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "image")
    String image;

    @Column(name = "is_deleted")
    @Builder.Default
    Boolean isDeleted = false;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_camp_site")
    CampSiteEntity campSite;

    @ManyToMany
    @JoinTable(name = "camp_type_facility", joinColumns = @JoinColumn(name = "id_camp_type"), inverseJoinColumns = @JoinColumn(name = "id_facility"))
    List<FacilityEntity> facilities;

    @OneToMany(mappedBy = "campType", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CampEntity> camps;

    @OneToMany(mappedBy = "campType")
    List<CustomPriceEntity> customPrices;
}

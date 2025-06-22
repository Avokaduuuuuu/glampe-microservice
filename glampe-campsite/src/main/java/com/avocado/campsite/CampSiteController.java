package com.avocado.campsite;


import com.avocado.campsite.dto.req.CampSiteCreateRequest;
import com.avocado.campsite.dto.req.CampSiteUpdateRequest;
import com.avocado.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/campsites")
@RequiredArgsConstructor
@Slf4j
public class CampSiteController {

    private final CampSiteService campSiteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCampSiteById(@PathVariable("id") Long id){
        log.info("Get camp site by id: {}", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK.value())
                                .data(campSiteService.getCampSiteById(id))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<?> getAll(CampSiteFilterParams filterParams){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK.value())
                                .data(campSiteService.getAll(filterParams))
                                .build()
                );
    }

    @PostMapping
    public ResponseEntity<?> addCampSite(@RequestBody @Valid CampSiteCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.builder()
                                .message("Success")
                                .statusCode(HttpStatus.CREATED.value())
                                .data(campSiteService.addCampSite(request))
                                .build()
                );
    }

    @PostMapping("/batch")
    public ResponseEntity<?> fetchBatch(@RequestBody List<Long> ids){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK.value())
                                .data(campSiteService.fetchBatch(ids))
                                .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampSite(@RequestBody @Valid CampSiteUpdateRequest request, @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK.value())
                                .data(campSiteService.updateCampSite(request,id))
                                .build()
                );
    }

}

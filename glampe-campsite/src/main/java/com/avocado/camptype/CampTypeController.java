package com.avocado.camptype;

import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/campsites/camp-types")
@RequiredArgsConstructor
public class CampTypeController {

    private final CampTypeService campTypeService;

    @PostMapping("/batch")
    public ResponseEntity<?> getCampTypeByIds(@RequestBody List<Long> ids) {
        log.info("getCampTypeByIds");
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .data(campTypeService.getCampTypesByIds(ids))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getCampTypes(CampTypeFilterParams params) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .data(campTypeService.getAllCampTypes(params))
                        .build()
        );
    }
}

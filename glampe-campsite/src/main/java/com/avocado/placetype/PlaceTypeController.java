package com.avocado.placetype;


import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/campsites/place-types")
@RequiredArgsConstructor
public class PlaceTypeController {

    private final PlaceTypeService placeTypeService;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Fetch All Place Types")
                        .data(placeTypeService.fetchAll())
                        .build()
        );
    }
}

package com.avocado.selection;

import com.avocado.selection.dto.req.SelectionUpdateRequest;
import com.avocado.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/campsites/selections")
@RequiredArgsConstructor
public class SelectionController {

    private final SelectionService selectionService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .data(selectionService.getById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SelectionUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .data(selectionService.update(request, id))
                        .build()
        );
    }

    @PostMapping("/batch")
    public ResponseEntity<?> batch(@RequestBody List<Long> ids){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .data(selectionService.batch(ids))
                        .build()
        );
    }
}

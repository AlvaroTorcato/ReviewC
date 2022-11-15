package com.example.reviewc.controller;

import com.example.reviewc.model.AggregateRating;
import com.example.reviewc.model.Review;
import com.example.reviewc.model.ReviewDTO;
import com.example.reviewc.model.ReviewDetailsDTO;
import com.example.reviewc.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Tag(name = "Reviews", description = "Endpoints for managing reviews")
@RequestMapping("/reviews")
@RestController
@Controller
public class ReviewController {
    @Autowired
    ReviewService service;
    @Operation(summary = "Create a review")
    @PostMapping(value = "/{sku}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO create(@RequestBody final ReviewDetailsDTO resource, @PathVariable("sku") final String sku, HttpServletRequest request) throws IOException {
        return service.createReview(resource,sku, request);
    }

    @Operation(summary = "Delete a review")
    @DeleteMapping(value = "/{idReview}")
    public ResponseEntity<Review> delete(@PathVariable("idReview") final int id, HttpServletRequest request) {
        service.deleteById(id,request);
        return ResponseEntity.noContent().build();
    }



}

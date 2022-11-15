package com.example.reviewc.controller;

import com.example.reviewc.model.ChangeStatus;
import com.example.reviewc.model.ReviewDTO;
import com.example.reviewc.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Moderator", description = "Endpoints for managing reviews")
@RequestMapping("/moderator")
@RestController
@Controller
public class ModeratorController {
    @Autowired
    ReviewService service;

    @Operation(summary = "Change the status of the review")
    @PutMapping(value = "/pending/{idReview}")
    public ReviewDTO changeStatus(@PathVariable("idReview") final int idReview, @RequestBody final ChangeStatus resource, HttpServletRequest request){
        return service.changeStatus(idReview,resource,request);
    }
}

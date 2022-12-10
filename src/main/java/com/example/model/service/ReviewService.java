package com.example.model.service;

import com.example.model.*;
import com.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private RequestService service;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    ProductService serviceProduct;

    @Autowired
    JWTService serviceJWT;

    @Autowired

    public ReviewDTO createReview(final ReviewDetailsDTO resource, String sku, HttpServletRequest request) throws IOException {
        /*int statusCode = getStatusCodeOfProduct(sku);
        if (statusCode == 404){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }*/
        String jwt = service.parseJwt(request);
        UserDetailsDTO user = serviceJWT.searchForUser(jwt);

        if (!user.getRoles().equals("[MODERATOR]") && !user.getRoles().equals("[COSTUMER]")){
            System.out.println(user.getRoles());
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Can´t be accessed by this user");
        }
        ProductDetailsDTO p = serviceProduct.findProductBySku(sku);
        if (p == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }
        Review review = new Review(resource.getText(), resource.getRating(),sku,user.getId());
        repository.save(review);
        rabbitMQSender.send(review);
        ReviewDTO reviewDTO = new ReviewDTO(review);
        return reviewDTO;
    }
    public ReviewDTO changeStatus(int idReview, ChangeStatus resource, HttpServletRequest request) {
        String jwt = service.parseJwt(request);
        UserDetailsDTO user = serviceJWT.searchForUser(jwt);
        if (!user.getRoles().equals("[MODERATOR]")){
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Can´t be accessed by this user");
        }
        String updateString = resource.updateString();
        repository.updateReview(updateString,idReview);
        Change change = new Change(idReview, updateString);
        rabbitMQSender.sendUpdate(change);
        ReviewDTO reviewDTO = repository.findReviewById(idReview);
        return reviewDTO;
    }



    public int getStatusCodeOfProduct(String sku){
        String urlRequest = "http://localhost:8081/products/" + sku;
        int statusCode = service.getStatusOfRequest(urlRequest);
        return statusCode;
    }

    public ReviewDTO findReviewById(int reviewId) {
        ReviewDTO review= repository.findReviewByIdAndApproved(reviewId);
        if (review == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }
        return review;
    }

    public void deleteById(int idReview,HttpServletRequest request){
        String jwt = service.parseJwt(request);
        UserDetailsDTO user = serviceJWT.searchForUser(jwt);
        if (!user.getRoles().equals("[MODERATOR]") && !user.getRoles().equals("[COSTUMER]")){
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN, "Can´t be accessed by this user");
        }
        ReviewDTO review= findReviewById(idReview);
        //String urlRequest = "http://localhost:8083/votes/search/" + idReview;
        //int statusCode = service.getStatusOfRequest(urlRequest);
        boolean find = clientVotes.send(idReview);
        if (find == false && review.getUserid() == user.getId()){
            repository.deleteByIdReview(idReview);
            rabbitMQSender.sendDelete(idReview);
        }
        System.out.println(find);
    }

    public ReviewDTO updateReviewWithVote(int reviewId, String status) {
        ReviewDTO review= repository.findReviewByIdAndApproved(reviewId);
        if (review == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found");
        }
        else {
            int totalVotes = review.getTotalVotes();
            int upVotes = review.getUpVotes();
            int downVotes = review.getDownVotes();
            totalVotes += 1;
            if (status == "true"){
                upVotes += 1;
            }
            else {
                downVotes += 1;
            }
            repository.updateReviewWithVote(review.getId(),upVotes,downVotes,totalVotes);
            Votes vote = new Votes(review.getId(),upVotes,downVotes,totalVotes);
            rabbitMQSender.sendVoteUpdate(vote);
        }
        return repository.findReviewByIdAndApproved(reviewId);
    }

}

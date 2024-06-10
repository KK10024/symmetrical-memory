package com.rubypaper.comment;

import java.security.Principal;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.rubypaper.travel.Travel;
import com.rubypaper.travel.TravelService;
import com.rubypaper.user.SiteUser;
import com.rubypaper.user.UserService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private TravelService travelService;

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/create/travel/{id}")
    public String createQuestionComment(CommentForm commentForm) {
        return "comment_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create/travel/{id}")
    public String createQuestionComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
            BindingResult bindingResult, Principal principal) {
        Optional<Travel> travel = Optional.ofNullable(this.travelService.getTravel(id));
        Optional<SiteUser> user = Optional.ofNullable(this.userService.getUser(principal.getName()));
        if (travel.isPresent() && user.isPresent()) {
            if (bindingResult.hasErrors()) {
                return "comment_form";
            }
            Comment c = this.commentService.create(travel.get(), user.get(), commentForm.getContent());
            return String.format("redirect:/travel/detail/%s", c.getTravelId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            commentForm.setContent(c.getContent());
        }
        return "comment_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal,
            @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            c = this.commentService.modify(c, commentForm.getContent());
            return String.format("redirect:/travel/detail/%s", c.getTravelId());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(Principal principal, @PathVariable("id") Integer id) {
        Optional<Comment> comment = this.commentService.getComment(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            if (!c.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            }
            this.commentService.delete(c);
            return String.format("redirect:/travel/detail/%s", c.getTravelId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }    
}

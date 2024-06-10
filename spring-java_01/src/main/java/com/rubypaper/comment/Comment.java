package com.rubypaper.comment;

import java.time.LocalDateTime;

import com.rubypaper.travel.Travel;
import com.rubypaper.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
    

    @ManyToOne
    private Travel travel;
    
    public Integer getTravelId() {
        Integer result = null;
        if (this.travel != null) {
            result = this.travel.getId();
        } 
        return result;
    }
}


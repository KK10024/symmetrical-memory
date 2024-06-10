package com.rubypaper.travel;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.rubypaper.comment.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)

public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50)
	private String name;
    
    @Column(length = 200)
	private String addr;
    
    @Column(length = 200)
	private String detailaddr;
    
    @Column(length = 200)
    private String imgaddr;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @CreatedDate
	private LocalDateTime create_date;
    
    @OneToMany(mappedBy = "travel")
    private List<Comment> commentList;
}

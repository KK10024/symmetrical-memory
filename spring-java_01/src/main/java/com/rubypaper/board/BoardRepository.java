package com.rubypaper.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubypaper.travel.Travel;


public interface BoardRepository extends JpaRepository<Board, Integer> {
	Page<Board> findAll(Pageable pageable);
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

}
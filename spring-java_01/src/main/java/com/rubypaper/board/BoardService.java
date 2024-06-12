package com.rubypaper.board;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rubypaper.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	  
	public Board create(String subject, String content) {
		Board board = new Board();
		board.setSubject(subject);
		board.setContent(content);
	    this.boardRepository.save(board);
	    return board;
	}

    private Specification<Board> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Board> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"));      // 내용 
            }
        };
    }
    public Page<Board> getList(int page, String kw) {
	    List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Board> spec = search(kw);
        
        return this.boardRepository.findAll(spec, pageable);
    }
    public Board getBoard(Integer id) {  
        Optional<Board> board = this.boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new DataNotFoundException("board not found");
        }
    }
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}

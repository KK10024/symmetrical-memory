package com.rubypaper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubypaper.board.BoardService;

@SpringBootTest
public class ApplicationTests {
	
	   @Autowired
	    private BoardService boardService;

	    @Test
	    void testJpa() {
	        for (int i = 1; i <= 300; i++) {
	            String subject = String.format("테스트 데이터입니다:[%03d]", i);
	            String content = "내용무";
	            this.boardService.create(subject, content);
	        }
	    }
}

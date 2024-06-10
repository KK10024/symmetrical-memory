package com.rubypaper.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController{
	
	private final BoardService boardService;
	
	/*
	 * 게시판리스트
	 * */
	@GetMapping("/board_list")
    public String Board_List(Model model, @RequestParam(value="page", defaultValue="0") int page,
    		@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Board> paging = this.boardService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board/list";
    }
	
	/*
	 * 게시판등록
	 * GET요청
	 * */
	@GetMapping("/board_form")
    public String Board_Create_Form(Board board) {
        return "board/board_form";
    }
	/*
	 * 게시판 상세보기
	 * */
	@GetMapping(value ="/detail/{id}")
	public String Board_Detail(Model model, @PathVariable("id") Integer id) {
		Board board = this.boardService.getBoard(id);
		model.addAttribute("board", board);
		return "board/detail";
	}
	/*
	 * 게시판 글삭제 
	 **/
    @GetMapping("/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/board_list";
    }
	/*
	 * 게시판 등록 
	 * POST요청
	 **/
	@PostMapping("/board_form")
	public String Create_Form(Board board) {
		boardService.create(board.getSubject(), board.getContent());
		return "redirect:/board/board_list";
	}
	
}
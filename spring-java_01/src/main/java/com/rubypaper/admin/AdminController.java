package com.rubypaper.admin;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.rubypaper.board.Board;
import com.rubypaper.board.BoardService;
import com.rubypaper.travel.Travel;
import com.rubypaper.travel.TravelService;
import com.rubypaper.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController { 
    private final AdminService adminService;
    private final TravelService travelService;
	private final BoardService boardService;

		@GetMapping("")
	    public String admin_index(){
	        return "/admin/main";
	    }
		
		/**
		 * 유저 리스트 조회 
		 * **/
	    @GetMapping("/userlist")
	    public String User_list(Model model) {
	    	List<SiteUser> users = adminService.getUsers();
			model.addAttribute("users" ,users);
		    return "/admin/userlist";
			}
	    
	    
	    
	    /**
		 * 여행지 리스트 조회 
		 * **/
	    @GetMapping("/travellist")
	    public String travel_list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
	    		@RequestParam(value = "kw", defaultValue = "") String kw) {
			Page<Travel> travel = this.travelService.getList(page, kw);
			model.addAttribute("travel" ,travel);
	        model.addAttribute("kw", kw);
		    return "admin/travel_list";
			}
		/*
		 * 게시판리스트
		 * */
		@GetMapping("/boardlist")
	    public String Board_List(Model model, @RequestParam(value="page", defaultValue="0") int page,
	    		@RequestParam(value = "kw", defaultValue = "") String kw) {
			Page<Board> board = this.boardService.getList(page, kw);
	        model.addAttribute("board", board);
	        model.addAttribute("kw", kw);
	        return "admin/board_list";
	    }
		/*
		 * 여행지 리스트 상세보기
		 * */
		@GetMapping(value ="/travel_detail/{id}")
		public String travel_Detail(Model model, @PathVariable("id") Integer id) {
			Travel travel = this.travelService.getTravel(id);
			model.addAttribute("travel", travel);
			return "admin/travel_detail";
		}	    
	    /**
		 * 여행지 리스트 등록 get
		 * **/
		@GetMapping("/travel_form")
	    public String travel_Form(Travel travel) {
	        return "admin/travel_form";
	    }
		/*
		 * 여행지 리스트 글삭제 
		 **/
	    @GetMapping("/delete")
	    public String boardDelete(Integer id){
	    	travelService.travelDelete(id);
	        return "redirect:/admin/travellist";
	    }
	    /**
		 * 여행지 리스트 등록 post
		 * **/
		@PostMapping("/travel_form")
		public String Create_Form(@RequestPart MultipartFile pic, Travel travel) {
			travelService.create(travel.getName(), travel.getAddr(), travel.getDetailaddr(), travel.getImgaddr(), travel.getContent(),pic);
			return "redirect:/admin/travellist";
		}
		
}
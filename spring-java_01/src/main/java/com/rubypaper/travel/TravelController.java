package com.rubypaper.travel;



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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/travel")
public class TravelController {
	
	private final TravelService travelService;
	/*
	 * 여행지 리스트
	 * */
	@GetMapping("/travel_list")
    public String Travel_List(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "kw", defaultValue = "") String kw) {
			Page<Travel> travelList = this.travelService.getList(page, kw);
			model.addAttribute("travelList", travelList);
	        model.addAttribute("kw", kw);
	        return "travel/list";
	}
	/*
	 * 여행지 상세보기
	 * */
	@GetMapping(value ="/detail/{id}")
	public String Travel_Detail(Model model, @PathVariable("id") Integer id) {
		Travel travel = this.travelService.getTravel(id);
		model.addAttribute("travel", travel);
		return "travel/detail";
	}
    /**
	 * 여행지 리스트 등록 get
	 * **/
	@GetMapping("/travel_form")
    public String travel_Form(Travel travel) {
        return "travel/travel_form";
    }
	/*
	 * 여행지 리스트 글삭제 
	 **/
    @GetMapping("/delete")
    public String travel_Delete(Integer id){
    	travelService.travelDelete(id);
        return "redirect:/travel/travel_list";
    }
    /**
	 * 여행지 리스트 등록 post
	 * **/
	@PostMapping("/travel_form")
	public String Create_Form(@RequestPart MultipartFile pic, Travel travel) {
		travelService.create(travel.getName(), travel.getAddr(), travel.getDetailaddr(), travel.getImgaddr(), travel.getContent(),pic);
		return "redirect:/travel/travel_list";
	}
	
}

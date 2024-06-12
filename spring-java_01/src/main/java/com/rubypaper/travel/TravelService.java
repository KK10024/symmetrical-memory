package com.rubypaper.travel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rubypaper.DataNotFoundException;
import com.rubypaper.board.Board;
import com.rubypaper.user.SiteUser;

import jakarta.transaction.Transactional;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TravelService {
	private final TravelRepository travelRepository;
	
	public Travel create(String name, String addr, String detailaddr, String imgaddr, String content, MultipartFile pic) {
		UUID uuid = UUID.randomUUID();
		String imgeFileName = uuid+ "_" + pic.getOriginalFilename();
		String path = "C:/Users/user/eclipse-workspace/spring-java_01/src/main/resources/static/img/";
		Path imgePath = Paths.get(path + imgeFileName);
		try {
			Files.write(imgePath, pic.getBytes());
		}catch (Exception e) {
			// TODO: handle exception
		}
		Travel travel = new Travel();
		travel.setName(name);
		travel.setAddr(addr);
		travel.setDetailaddr(detailaddr);
		travel.setImgaddr(imgeFileName);
		travel.setContent(content);
	    this.travelRepository.save(travel);
		return travel;
	}
	 private Specification<Travel> search(String kw) {
	        return new Specification<>() {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public Predicate toPredicate(Root<Travel> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                query.distinct(true);  // 중복을 제거 
	                return cb.or(cb.like(q.get("addr"), "%" + kw + "%")); // 주소 
//	                       cb.like(q.get("content"), "%" + kw + "%"));      // 내용 

	            }
	        };
	    }
	 
	
	public Page<Travel> getList(int page, String kw) {
	    List<Sort.Order> sorts = new ArrayList<>();
	    sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
	    Specification<Travel> spec = search(kw);

	    return this.travelRepository.findAll(spec, pageable);

	}
    public Travel getTravel(Integer id) {  
        Optional<Travel> travel = this.travelRepository.findById(id);
        if (travel.isPresent()) {
            return travel.get();
        } else {
            throw new DataNotFoundException("travel not found");
        }
    }
    
    public void travelDelete(Integer id){
    	travelRepository.deleteById(id);
    }
}

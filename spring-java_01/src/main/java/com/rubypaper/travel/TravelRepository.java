package com.rubypaper.travel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TravelRepository extends JpaRepository<Travel, Integer>  {
      Page<Travel> findAll(Pageable pageable);
	  Page<Travel> findAll(Specification<Travel> spec, Pageable pageable);
}

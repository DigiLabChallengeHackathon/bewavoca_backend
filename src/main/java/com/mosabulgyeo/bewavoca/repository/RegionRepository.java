package com.mosabulgyeo.bewavoca.repository;

import com.mosabulgyeo.bewavoca.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findByLevel(int level);
}
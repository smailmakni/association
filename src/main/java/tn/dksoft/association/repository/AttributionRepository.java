package tn.dksoft.association.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Attribution;

public interface AttributionRepository extends JpaRepository<Attribution, Long> {

	Page<Optional<Attribution>> findByEquipmentId(Long id, Pageable pageable);
}

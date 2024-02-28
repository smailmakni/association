package tn.dksoft.association.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Intervention;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {

	Page<Intervention> findAllByEquipmentId(Long id, Pageable pageable);

	Page<Optional<List<Intervention>>> findByEquipmentId(Long id, Pageable pageable);
}

package tn.dksoft.association.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Center;

public interface CenterRepository extends JpaRepository<Center, Long> {

	Page<Center> findAllByAgencyId(Long id, Pageable pageable);

	Page<Center> findByNomContains(String nom, Pageable pageable);

}

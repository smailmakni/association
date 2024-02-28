package tn.dksoft.association.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

	Page<Agency> findByNom(String nom, Pageable pageable);

}

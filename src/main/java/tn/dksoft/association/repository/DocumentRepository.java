package tn.dksoft.association.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	Page<Optional<Document>> findAllByNom(String nom, Pageable pageable);

	Page<Document> findByEquipmentId(Long id, Pageable pageable);

	Page<Document> findByInterventionId(Long id, Pageable pageable);

	Page<Document> findByAttributionId(Long id, Pageable pageable);

	Document findByNom(String nom);

}

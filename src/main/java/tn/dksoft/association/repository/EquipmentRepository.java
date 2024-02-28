package tn.dksoft.association.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.dksoft.association.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

	Page<Equipment> findByRoomId(Long id, Pageable pageable);

	Page<Equipment> findAllByRoomId(Long id, Pageable pageable);

	Page<Equipment> findAllByAttributionIsNotNullAndRoomId(Long id, Pageable pageable);

	Page<Equipment> findAllByAttributionIsNotNull(Pageable pageable);

	Page<Equipment> findAllByAttributionIsNull(Pageable pageable);

	Page<Equipment> findAllByAttributionIsNullAndRoomId(Long id, Pageable pageable);

	Page<Equipment> findAllByEtatLikeIgnoreCase(String repar, Pageable pageable);

	Page<Equipment> findAllByEtatLikeIgnoreCaseAndRoom_Id(String repar, Long id, Pageable pageable);

}

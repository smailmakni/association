package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.AttributionDTO;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.dto.EquipmentDTO;
import tn.dksoft.association.dto.InterventionDTO;
import tn.dksoft.association.entity.Attribution;
import tn.dksoft.association.entity.Document;
import tn.dksoft.association.entity.Equipment;
import tn.dksoft.association.entity.Intervention;
import tn.dksoft.association.entity.Room;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.mappers.AttributionMapper;
import tn.dksoft.association.mappers.DocumentMapper;
import tn.dksoft.association.mappers.EquipmentMapper;
import tn.dksoft.association.mappers.InterventionMapper;
import tn.dksoft.association.repository.AttributionRepository;
import tn.dksoft.association.repository.DocumentRepository;
import tn.dksoft.association.repository.EquipmentRepository;
import tn.dksoft.association.repository.InterventionRepository;
import tn.dksoft.association.repository.RoomRepository;
import tn.dksoft.association.validator.AttributionValidator;
import tn.dksoft.association.validator.DocumentValidator;
import tn.dksoft.association.validator.EquipmentValidator;
import tn.dksoft.association.validator.InterventionValidator;

@Service
@AllArgsConstructor
@Transactional
public class EquipmentService {

	private EquipmentRepository equipmentRepository;
	private RoomRepository roomRepository;
	private DocumentRepository documentRepository;
	private AttributionRepository attributionRepository;
	private InterventionRepository interventionRepository;
	private InterventionMapper interventionMapper;
	private AttributionMapper attributionMapper;
	private EquipmentMapper equipmentMapper;
	private DocumentMapper documentMapper;

	public EquipmentDTO addEquipment(EquipmentDTO equipmentDto) {
		List<String> errors = EquipmentValidator.validate(equipmentDto);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("L'equipement' n'est pas valide", ErrorCodes.EQUIPMENT_NOT_VALID, errors);
		}
		Optional<Room> room = roomRepository.findById(equipmentDto.getRoom().getId());
		if (room.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucune room avec l'ID" + equipmentDto.getRoom().getId() + " n'a ete trouve dans la BDD",
					ErrorCodes.ROOM_NOT_FOUND);
		}
		addDocmentToEquipment(equipmentDto.getDocuments(), equipmentDto.getId());
		return equipmentMapper.fromEntityToDto(equipmentRepository.save(equipmentMapper.fromDtoToEntity(equipmentDto)));
	}

	public EquipmentDTO addDocmentToEquipment(List<DocumentDTO> documents, Long idEquipment) {
		Optional<Equipment> equipment = equipmentRepository.findById(idEquipment);
		if (equipment.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucune equipment avec l'ID" + equipment.get().getId() + " n'a ete trouve dans la BDD",
					ErrorCodes.EQUIPMENT_NOT_FOUND);
		}
		List<DocumentDTO> docs = equipmentMapper.fromEntityToDto(equipment.get()).getDocuments();
		documents.forEach(doc -> {
			List<String> errorsDocs = DocumentValidator.validate(doc);
			if (!errorsDocs.isEmpty()) {
				throw new InvalidEntityException("documents n'est pas valide", ErrorCodes.DOCUMENT_NOT_VALID,
						errorsDocs);
			}
			docs.add(doc);
			documentRepository.save(documentMapper.fromDtoToEntity(doc));
		});
		equipmentMapper.fromEntityToDto(equipment.get()).setDocuments(docs);
		equipmentRepository.save(equipment.get());
		return equipmentMapper.fromEntityToDto(equipment.get());
	}

	public EquipmentDTO addAttribution(Long idEquipment, AttributionDTO attributionDTO) {
		List<String> errors = AttributionValidator.validate(attributionDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("L'attribution n'est pas valide", ErrorCodes.ATTRIBUTION_NOT_VALID,
					errors);
		}
		Optional<Equipment> equipment = equipmentRepository.findById(idEquipment);
		if (equipment.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucun equipement avec l'ID" + idEquipment + " n'a ete trouve dans la BDD",
					ErrorCodes.EQUIPMENT_NOT_FOUND);
		}

		if (attributionDTO.getDocument() == null) {
			throw new EntityNotFoundException("ajouter le document svp", ErrorCodes.DOCUMENT_NOT_FOUND);
		}

		DocumentDTO documentDTO = attributionDTO.getDocument();
		attributionDTO.setDocument(documentDTO);
		List<String> errorsDocs = DocumentValidator.validate(documentDTO);
		if (!errorsDocs.isEmpty()) {
			throw new InvalidEntityException("documents n'est pas valide", ErrorCodes.DOCUMENT_NOT_VALID, errorsDocs);
		}
		documentRepository.save(documentMapper.fromDtoToEntity(documentDTO));
		attributionRepository.save(attributionMapper.fromDtoToEntity(attributionDTO));
		equipment.get().setAttribution(attributionMapper.fromDtoToEntity(attributionDTO));
		equipmentRepository.save(equipment.get());
		return equipmentMapper.fromEntityToDto(equipment.get());
	}

	public EquipmentDTO addIntervention(Long idEquipment, InterventionDTO interventionDTO, List<DocumentDTO> docs) {
		List<String> errors = InterventionValidator.validate(interventionDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("L'intervention n'est pas valide", ErrorCodes.INTERVENTION_NOT_VALID,
					errors);
		}
		Optional<Equipment> equipment = equipmentRepository.findById(idEquipment);
		if (equipment.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucun equipement avec l'ID" + idEquipment + " n'a ete trouve dans la BDD",
					ErrorCodes.EQUIPMENT_NOT_FOUND);
		}
		if (interventionDTO.getDocuments() == null) {
			throw new EntityNotFoundException("ajouter le document svp", ErrorCodes.DOCUMENT_NOT_FOUND);
		}
		List<DocumentDTO> documentsDTO = interventionDTO.getDocuments();
		docs.forEach(doc -> {
			List<String> errorsDocs = DocumentValidator.validate(doc);
			if (!errorsDocs.isEmpty()) {
				throw new InvalidEntityException("documents n'est pas valide", ErrorCodes.DOCUMENT_NOT_VALID,
						errorsDocs);
			}
			documentsDTO.add(doc);
			documentRepository.save(documentMapper.fromDtoToEntity(doc));
		});
		interventionDTO.setDocuments(documentsDTO);
		interventionRepository.save(interventionMapper.fromDtoToEntity(interventionDTO));
		equipment.get().getInterventions().add(interventionMapper.fromDtoToEntity(interventionDTO));
		equipmentRepository.save(equipment.get());
		return equipmentMapper.fromEntityToDto(equipment.get());
	}

	public List<EquipmentDTO> findAll(Pageable pageable) {
		try {
			List<EquipmentDTO> equipmentDtos = equipmentRepository.findAll(pageable).getContent().stream()
					.map(equipmentMapper::fromEntityToDto).collect(Collectors.toList());
			return equipmentDtos;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipement data", e);
		}
	}

	public List<EquipmentDTO> findByRoomId(Long id, Pageable pageable) {
		try {
			if (id == null) {
				throw new EntityNotFoundException("Aucune Room avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.ROOM_NOT_FOUND);
			}
			return equipmentRepository.findAllByRoomId(id, pageable).stream().map(equipmentMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}

	}

	public String delete(Long id, Pageable pageable) {
		try {
			Optional<Equipment> equipment = equipmentRepository.findById(id);
			if (equipment.isPresent()) {
				Page<Document> documents = documentRepository.findByEquipmentId(id, pageable);
				if (documents != null) {
					for (Document doc : documents) {
						doc.setEquipment(null);
						documentRepository.save(doc);
						documentRepository.delete(doc);
					}
					equipment.get().setDocuments(null);
				}
				Page<Optional<List<Intervention>>> interventions = interventionRepository.findByEquipmentId(id, pageable);
				if (interventions != null) {
					for (Intervention intervention : interventions.getContent().get(0).get()) {
						if (intervention.getDocuments() != null) {
							for (Document doc : intervention.getDocuments()) {
								doc.setIntervention(null);
								documentRepository.save(doc);
								documentRepository.delete(doc);
							}
							intervention.setDocuments(null);
							interventionRepository.save(intervention);
						}
						intervention.setEquipment(null);
						interventionRepository.save(intervention);
						interventionRepository.delete(intervention);
					}
					equipment.get().setInterventions(null);
				}
				Page<Optional<Attribution>> attribution = attributionRepository.findByEquipmentId(id, pageable);
				if (equipment.get().getAttribution() != null) {
					equipment.get().getAttribution().setDocument(null);
					attributionRepository.save(equipment.get().getAttribution());
					equipment.get().setAttribution(null);
					equipmentRepository.save(equipment.get());
					attributionRepository.delete(attribution.getContent().get(0).get());
				}
				equipmentRepository.save(equipment.get());
				equipmentRepository.delete(equipment.get());
				return "Equipment with id: " + id + " deleted successfully!";
			} else {
				throw new EntityNotFoundException("Equipment with ID " + id + " not found");
			}
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public EquipmentDTO updateEquipment(@RequestBody EquipmentDTO equipmentDTO) {
		List<String> errors = EquipmentValidator.validate(equipmentDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("equipment n'est pas valide", ErrorCodes.EQUIPMENT_NOT_VALID, errors);
		}
		return equipmentMapper.fromEntityToDto(equipmentRepository.save(equipmentMapper.fromDtoToEntity(equipmentDTO)));
	}

	public List<EquipmentDTO> findByAttribution(Pageable pageable) {
		try {
			List<EquipmentDTO> equipments = equipmentRepository.findAllByAttributionIsNotNull(pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
			return equipments;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public List<EquipmentDTO> findByAttributionAndRoomID(Long RoomId, Pageable pageable) {
		try {
			return equipmentRepository.findAllByAttributionIsNotNullAndRoomId(RoomId, pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public List<EquipmentDTO> findByNonAttribution(Pageable pageable) {
		try {
			return equipmentRepository.findAllByAttributionIsNull(pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public List<EquipmentDTO> findByNonAttributionAndRoomId(Long roomId, Pageable pageable) {
		try {
			return equipmentRepository.findAllByAttributionIsNullAndRoomId(roomId, pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public List<EquipmentDTO> findAllByEtatReparation(String reparation, Pageable pageable) {
		try {
			return equipmentRepository.findAllByEtatLikeIgnoreCase(reparation, pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

	public List<EquipmentDTO> findAllByEtatReparationAndRoomID(String reparation, Long id, Pageable pageable) {
		try {
			return equipmentRepository.findAllByEtatLikeIgnoreCaseAndRoom_Id(reparation, id, pageable).stream()
					.map(equipmentMapper::fromEntityToDto).toList();
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

}

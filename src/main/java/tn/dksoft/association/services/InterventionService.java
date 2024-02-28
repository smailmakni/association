package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.dto.InterventionDTO;
import tn.dksoft.association.entity.Intervention;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.mappers.DocumentMapper;
import tn.dksoft.association.mappers.InterventionMapper;
import tn.dksoft.association.repository.DocumentRepository;
import tn.dksoft.association.repository.InterventionRepository;
import tn.dksoft.association.validator.DocumentValidator;
import tn.dksoft.association.validator.InterventionValidator;

@Service
@AllArgsConstructor
@Transactional
public class InterventionService {

	private InterventionRepository interventionRepository;
	private DocumentRepository documentRepository;
	private DocumentMapper documentMapper;
	private InterventionMapper interventionMapper;

	public List<InterventionDTO> findAll(Pageable pageable) {
		try {
			List<InterventionDTO> interventions = interventionRepository.findAll(pageable).stream()
					.map(interventionMapper::fromEntityToDto).collect(Collectors.toList());
			return interventions;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching intervention data", e);
		}
	}

	public List<InterventionDTO> findByEquipmentId(Long id, Pageable pageable) {
		try {
			if (id == null) {
				throw new EntityNotFoundException(
						"Aucune Intervention avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.INTERVENTION_NOT_FOUND);
			}
			return interventionRepository.findAllByEquipmentId(id, pageable).stream()
					.map(interventionMapper::fromEntityToDto).collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching intervention data", e);
		}
	}

	public InterventionDTO addDocmentToIntervention(List<DocumentDTO> documents, Long idIntervention) {
		Optional<Intervention> intervention = interventionRepository.findById(idIntervention);
		if (intervention.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucune intervention avec l'ID" + intervention.get().getId() + " n'a ete trouve dans la BDD",
					ErrorCodes.INTERVENTION_NOT_FOUND);
		}
		List<DocumentDTO> docs = interventionMapper.fromEntityToDto(intervention.get()).getDocuments();
		documents.forEach(doc -> {
			List<String> errorsDocs = DocumentValidator.validate(doc);
			if (!errorsDocs.isEmpty()) {
				throw new InvalidEntityException("documents n'est pas valide", ErrorCodes.DOCUMENT_NOT_VALID,
						errorsDocs);
			}
			docs.add(doc);
			documentRepository.save(documentMapper.fromDtoToEntity(doc));
		});
		interventionMapper.fromEntityToDto(intervention.get()).setDocuments(docs);
		interventionRepository.save(intervention.get());
		return interventionMapper.fromEntityToDto(intervention.get());
	}

	public InterventionDTO updateIntervention(@RequestBody InterventionDTO interventionDTO) {
		List<String> errors = InterventionValidator.validate(interventionDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("intervention n'est pas valide", ErrorCodes.INTERVENTION_NOT_VALID,
					errors);
		}
		return interventionMapper
				.fromEntityToDto(interventionRepository.save(interventionMapper.fromDtoToEntity(interventionDTO)));
	}

	public String delete(Long id) {
		Optional<Intervention> intervention = interventionRepository.findById(id);
		if (intervention.isPresent()) {

			interventionRepository.deleteById(intervention.get().getId());
			return "Intervention with number: " + id + " deleted successfully!";
		} else {
			throw new InvalidEntityException("Intervention with ID " + id + " not found",
					ErrorCodes.INTERVENTION_NOT_FOUND);
		}

	}

}

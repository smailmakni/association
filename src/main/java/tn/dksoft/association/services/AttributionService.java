package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.AttributionDTO;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.entity.Attribution;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.mappers.AttributionMapper;
import tn.dksoft.association.mappers.DocumentMapper;
import tn.dksoft.association.repository.AttributionRepository;
import tn.dksoft.association.repository.DocumentRepository;
import tn.dksoft.association.validator.AttributionValidator;
import tn.dksoft.association.validator.DocumentValidator;

@Service
@AllArgsConstructor
@Transactional
public class AttributionService {

	private AttributionRepository attributionRepository;
	private AttributionMapper attributionMapper;
	private DocumentRepository documentRepository;
	private DocumentMapper documentMapper;

	public List<AttributionDTO> findAll(Pageable pageable) {
		try {
			List<AttributionDTO> attributions = attributionRepository.findAll(pageable).stream()
					.map(attributionMapper::fromEntityToDto).collect(Collectors.toList());
			return attributions;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching attribution data", e);
		}
	}

	public AttributionDTO updateAttribution(@RequestBody AttributionDTO attributionDTO, DocumentDTO documentDTO) {
		List<String> errors = AttributionValidator.validate(attributionDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("attribution n'est pas valide", ErrorCodes.ATTRIBUTION_NOT_VALID, errors);
		}
		List<String> errorsDocs = DocumentValidator.validate(documentDTO);
		if (!errorsDocs.isEmpty()) {
			throw new InvalidEntityException("documents n'est pas valide", ErrorCodes.DOCUMENT_NOT_VALID, errors);
		}
		attributionDTO.setDocument(documentDTO);
		documentRepository.save(documentMapper.fromDtoToEntity(documentDTO));
		return attributionMapper
				.fromEntityToDto(attributionRepository.save(attributionMapper.fromDtoToEntity(attributionDTO)));
	}

	public String delete(Long id) {
		Optional<Attribution> attribution = attributionRepository.findById(id);
		if (attribution.isPresent()) {
			attributionRepository.deleteById(attribution.get().getId());
			return "Attribution with Id: " + id + " deleted successfully!";
		} else {
			throw new InvalidEntityException("Attribution with ID " + id + " not found",
					ErrorCodes.ATTRIBUTION_NOT_FOUND);
		}

	}

}

package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.CenterDTO;
import tn.dksoft.association.entity.Agency;
import tn.dksoft.association.entity.Room;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.exception.InvalidOperationException;
import tn.dksoft.association.mappers.CenterMapper;
import tn.dksoft.association.repository.AgencyRepository;
import tn.dksoft.association.repository.CenterRepository;
import tn.dksoft.association.repository.RoomRepository;
import tn.dksoft.association.validator.CenterValidator;

@Service
@AllArgsConstructor
public class AgencyService {

	private AgencyRepository agencyRepository;
	private CenterRepository centerRepository;
	private RoomRepository roomRepository;
	private CenterMapper centerMapper;

	public List<CenterDTO> findAllCenters(Pageable pageable) {
		try {
			List<CenterDTO> centerDTOs = centerRepository.findAll(pageable).getContent().stream()
					.map(centerMapper::fromEntityToDto).collect(Collectors.toList());
			return centerDTOs;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching center data", e);
		}
	}

	public List<CenterDTO> findAllCentersByAgencyId(Long id, Pageable pageable) {
		try {
			if (id == null) {
				throw new EntityNotFoundException("Aucune agence avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.AGENCY_NOT_FOUND);
			}
			return centerRepository.findAllByAgencyId(id, pageable).stream().map(centerMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching center data", e);
		}
	}

	public CenterDTO addCenter(CenterDTO centerDTO) {
		List<String> errors = CenterValidator.validate(centerDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("centre n'est pas valide", ErrorCodes.CENTER_NOT_VALID, errors);
		}
		Optional<Agency> agency = agencyRepository.findById(centerDTO.getAgency().getId());
		if (agency.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucune agence avec l'ID" + centerDTO.getAgency().getId() + " n'a ete trouve dans la BDD",
					ErrorCodes.AGENCY_NOT_FOUND);
		}
		return centerMapper.fromEntityToDto(centerRepository.save(centerMapper.fromDtoToEntity(centerDTO)));
	}

	public void deleteCenters(Long id, Pageable pageable) {
		try {
			Page<Room> rooms = roomRepository.findAllByCenterId(id, pageable);
			if (!rooms.isEmpty()) {
				throw new InvalidOperationException("Impossible de supprimer un centre deja utilisé",
						ErrorCodes.CENTER_ALREADY_IN_USE);
			}
			centerRepository.deleteById(id);
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching center data", e);
		}
	}

	public CenterDTO updateCenter(@RequestBody CenterDTO center) {
		List<String> errors = CenterValidator.validate(center);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("centre n'est pas valide", ErrorCodes.CENTER_NOT_VALID, errors);
		}
		return centerMapper.fromEntityToDto(centerRepository.save(centerMapper.fromDtoToEntity(center)));
	}

	public List<CenterDTO> searchCenter(String search, Pageable pageable) {
		try {
			return centerRepository.findByNomContains(search, pageable).stream().map(centerMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching equipment data", e);
		}
	}

}

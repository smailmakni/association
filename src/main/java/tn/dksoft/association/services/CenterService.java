package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.RoomDTO;
import tn.dksoft.association.entity.Center;
import tn.dksoft.association.entity.Equipment;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.exception.InvalidOperationException;
import tn.dksoft.association.mappers.RoomMapper;
import tn.dksoft.association.repository.CenterRepository;
import tn.dksoft.association.repository.EquipmentRepository;
import tn.dksoft.association.repository.RoomRepository;
import tn.dksoft.association.validator.RoomValidator;

@Service
@AllArgsConstructor
public class CenterService {

	private CenterRepository centerRepository;
	private RoomRepository roomRepository;
	private EquipmentRepository equipmentRepository;
	private RoomMapper roomMapper;

	public List<RoomDTO> findAllRooms(Pageable pageable) {
		try {
			List<RoomDTO> roomDTOs = roomRepository.findAll(pageable).getContent().stream()
					.map(roomMapper::fromEntityToDto).collect(Collectors.toList());
			return roomDTOs;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching room data", e);
		}
	}

	public List<RoomDTO> findAllRoomsByCenterId(Long id, Pageable pageable) {
		try {
			if (id == null) {
				throw new EntityNotFoundException("Aucun center avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.CENTER_NOT_FOUND);
			}
			return roomRepository.findAllByCenterId(id, pageable).stream().map(roomMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching room data", e);
		}
	}

	public RoomDTO addRoom(RoomDTO roomDTO) {
		List<String> errors = RoomValidator.validate(roomDTO);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("centre n'est pas valide", ErrorCodes.CENTER_NOT_VALID, errors);
		}
		Optional<Center> center = centerRepository.findById(roomDTO.getCenter().getId());
		if (center.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucun centre avec l'ID" + roomDTO.getCenter().getId() + " n'a ete trouve dans la BDD",
					ErrorCodes.CENTER_NOT_FOUND);
		}
		return roomMapper.fromEntityToDto(roomRepository.save(roomMapper.fromDtoToEntity(roomDTO)));
	}

	public void deleteRoom(Long id, Pageable pageable) {
		try {
			Page<Equipment> equipments = equipmentRepository.findAllByRoomId(id, pageable);
			if (!equipments.isEmpty()) {
				throw new InvalidOperationException("Impossible de supprimer une salle deja utilisée",
						ErrorCodes.ROOM_ALREADY_IN_USE);
			}
			roomRepository.deleteById(id);
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching room data", e);
		}
	}

	public RoomDTO updateRoom(@RequestBody RoomDTO room) {
		List<String> errors = RoomValidator.validate(room);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("room n'est pas valide", ErrorCodes.ROOM_NOT_VALID, errors);
		}
		return roomMapper.fromEntityToDto(roomRepository.save(roomMapper.fromDtoToEntity(room)));
	}

	public List<RoomDTO> searchRoom(String search, Pageable pageable) {
		try {
			return roomRepository.findByActiviteContains(search, pageable).stream().map(roomMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching room data", e);
		}
	}

}

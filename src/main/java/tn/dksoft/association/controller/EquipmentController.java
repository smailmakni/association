package tn.dksoft.association.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.AttributionDTO;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.dto.EquipmentDTO;
import tn.dksoft.association.dto.InterventionDTO;
import tn.dksoft.association.services.EquipmentService;

@RestController
@RequestMapping(value = "/api/equipment", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class EquipmentController {

	private EquipmentService equipmentService;

	@GetMapping("/equipmentList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<EquipmentDTO>> findEquipments(Pageable pageable) {
		List<EquipmentDTO> equipmentDTOs = equipmentService.findAll(pageable);
		return new ResponseEntity<>(equipmentDTOs, HttpStatus.OK);
	}

	@GetMapping(path = "/room/{idRoom}", produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<EquipmentDTO>> findByIdRoom(@PathVariable(value = "idRoom") Long id, Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findByRoomId(id, pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@GetMapping(path = "/intervention/{reparation}", produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<EquipmentDTO>> findAllByEtatReparation(
			@PathVariable(value = "reparation") String reparation, Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findAllByEtatReparation(reparation, pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@GetMapping(path = "/intervention/{reparation}/{idRoom}", produces = "application/json")
	public ResponseEntity<List<EquipmentDTO>> findAllByEtatReparationAndRoomId(
			@PathVariable(value = "reparation") String reparation, @PathVariable(value = "idRoom") Long id,
			Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findAllByEtatReparationAndRoomID(reparation, id, pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@GetMapping(path = "/attribution", produces = "application/json")
	public ResponseEntity<List<EquipmentDTO>> findByAttribution(Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findByAttribution(pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@GetMapping(path = "/attribution/{idRoom}", produces = "application/json")
	public ResponseEntity<List<EquipmentDTO>> findByAttributionAndRoomID(@PathVariable(value = "idRoom") Long id,
			Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findByAttributionAndRoomID(id, pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@GetMapping(path = "/nonAttribution", produces = "application/json")
	public ResponseEntity<List<EquipmentDTO>> findByNonAttribution(Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findByNonAttribution(pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@GetMapping(path = "/nonAttribution/{idRoom}", produces = "application/json")
	public ResponseEntity<List<EquipmentDTO>> findByNonAttributionAndRoomID(@PathVariable(value = "idRoom") Long id,
			Pageable pageable) {
		List<EquipmentDTO> equipments = equipmentService.findByNonAttributionAndRoomId(id, pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@PostMapping("/addEquipment")
	public ResponseEntity<EquipmentDTO> save(@RequestBody EquipmentDTO dto) {
		EquipmentDTO equipment = equipmentService.addEquipment(dto);
		return new ResponseEntity<>(equipment, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@PostMapping("/attribution/{equipmentId}")
	public ResponseEntity<EquipmentDTO> addAttribution(@RequestBody AttributionDTO attributionDTO,
			@PathVariable Long equipmentId) {
		EquipmentDTO equipment = equipmentService.addAttribution(equipmentId, attributionDTO);
		return new ResponseEntity<>(equipment, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@PostMapping("/intervention/{equipmentId}")
	public ResponseEntity<EquipmentDTO> addIntervention(@RequestBody InterventionDTO interventionDTO,
			@RequestBody List<DocumentDTO> documentDTOs, @PathVariable Long equipmentId) {
		EquipmentDTO equipment = equipmentService.addIntervention(equipmentId, interventionDTO, documentDTOs);
		return new ResponseEntity<>(equipment, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@PostMapping("/document/{equipmentId}")
	public ResponseEntity<EquipmentDTO> addDocument(@RequestBody List<DocumentDTO> documentsDTO,
			@PathVariable Long idEquipment) {
		EquipmentDTO equipment = equipmentService.addDocmentToEquipment(documentsDTO, idEquipment);
		return new ResponseEntity<>(equipment, HttpStatus.OK);
	}

	@PutMapping("/updateEquipment")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<EquipmentDTO> updateEquipment(@RequestBody EquipmentDTO equipmentDTO) {
		return new ResponseEntity<>(equipmentService.updateEquipment(equipmentDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@DeleteMapping("/delete/{idEquipment}")
	public ResponseEntity<List<EquipmentDTO>> delete(@PathVariable(name = "idEquipment") Long id, Pageable pageable) {
		equipmentService.delete(id, pageable);
		List<EquipmentDTO> equipments = equipmentService.findAll(pageable);
		return new ResponseEntity<>(equipments, HttpStatus.OK);

	}

}

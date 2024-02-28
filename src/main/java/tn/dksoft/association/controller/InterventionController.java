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
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.dto.InterventionDTO;
import tn.dksoft.association.services.InterventionService;

@RestController
@RequestMapping(value = "/api/intervention", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class InterventionController {

	private InterventionService interventionService;

	@GetMapping("/interventionList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<InterventionDTO>> findInterventions(Pageable pageable) {
		List<InterventionDTO> interventions = interventionService.findAll(pageable);
		return new ResponseEntity<>(interventions, HttpStatus.OK);
	}

	@GetMapping(path = "/equipment/{idEquipment}", produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<InterventionDTO>> findByIdEquipment(@PathVariable(value = "idEquipment") Long id,
			Pageable pageable) {
		List<InterventionDTO> interventions = interventionService.findByEquipmentId(id, pageable);
		return new ResponseEntity<>(interventions, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@PostMapping("/document/{idIntervention}")
	public ResponseEntity<InterventionDTO> addDocument(@RequestBody List<DocumentDTO> documentsDTO,
			@PathVariable Long idIntervention) {
		InterventionDTO interventionDTO = interventionService.addDocmentToIntervention(documentsDTO, idIntervention);
		return new ResponseEntity<>(interventionDTO, HttpStatus.OK);
	}

	@PutMapping("/updateIntervention")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<InterventionDTO> updateIntervention(@RequestBody InterventionDTO interventionDTO) {
		return new ResponseEntity<>(interventionService.updateIntervention(interventionDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@DeleteMapping("/delete/{idIntervention}")
	public ResponseEntity<List<InterventionDTO>> delete(@PathVariable(name = "idIntervention") Long id,
			Pageable pageable) {
		interventionService.delete(id);
		List<InterventionDTO> interventions = interventionService.findAll(pageable);
		return new ResponseEntity<>(interventions, HttpStatus.OK);

	}

}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.CenterDTO;
import tn.dksoft.association.services.AgencyService;

@RestController
@RequestMapping(value = "/api/agency", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class AgencyController {

	private AgencyService agencyService;

	@GetMapping("/centersList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<List<CenterDTO>> findCenters(Pageable pageable) {
		List<CenterDTO> centers = agencyService.findAllCenters(pageable);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}

	@GetMapping(path = "/centerListByAgency/{idAgency}", produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<List<CenterDTO>> findAllByAgencyId(@PathVariable(value = "idAgency") Long id,
			Pageable pageable) {
		List<CenterDTO> centers = agencyService.findAllCentersByAgencyId(id, pageable);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}

	@PostMapping("/addCenter")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<CenterDTO> save(@RequestBody CenterDTO center) {
		return new ResponseEntity<>(agencyService.addCenter(center), HttpStatus.OK);
	}

	@DeleteMapping("/deleteCneter/{idCentre}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<List<CenterDTO>> delete(@PathVariable(name = "idCentre") Long id, Pageable pageable) {
		agencyService.deleteCenters(id, pageable);
		List<CenterDTO> centers = agencyService.findAllCenters(pageable);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}

	@GetMapping("/searchCenter")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<List<CenterDTO>> findCenters(@RequestParam(name = "search", defaultValue = "") String search,
			Pageable pageable) {
		List<CenterDTO> centers = agencyService.searchCenter(search, pageable);
		return new ResponseEntity<>(centers, HttpStatus.OK);
	}

	@PutMapping("/updateCenter")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY')")
	public ResponseEntity<CenterDTO> updateCenter(@RequestBody CenterDTO center) {
		return new ResponseEntity<>(agencyService.updateCenter(center), HttpStatus.OK);
	}

}

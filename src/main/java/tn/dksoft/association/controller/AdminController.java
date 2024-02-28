package tn.dksoft.association.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
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
import tn.dksoft.association.dto.AgencyDTO;
import tn.dksoft.association.dto.UserDTO;
import tn.dksoft.association.services.AdminService;
import tn.dksoft.association.services.UserService;

@RestController
@RequestMapping(value = "/api/admin", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class AdminController {

	private AdminService adminService;

	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(adminService.register(userDTO), HttpStatus.CREATED);
	}

	@GetMapping("/usersList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<UserDTO>> findUsers(Pageable pageable) {
		List<UserDTO> users = adminService.findUsers(pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable("id") Long id, Pageable pageable) {
		adminService.deleteById(id);
		List<UserDTO> users = adminService.findUsers(pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(name = "search", defaultValue = "") String search,
			Pageable pageable) {
		List<UserDTO> users = adminService.searchUsers(search, pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PutMapping("/updateUser")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
		return new ResponseEntity<>(adminService.updateUser(user), HttpStatus.OK);
	}

	@PostMapping("/addAgency")
	public ResponseEntity<AgencyDTO> addAgency(@RequestBody AgencyDTO agencyDTO) {
		return new ResponseEntity<>(adminService.addAgency(agencyDTO), HttpStatus.CREATED);
	}

	@GetMapping("/agenciesList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<AgencyDTO>> findAgencies(Pageable pageable) {
		List<AgencyDTO> agencies = adminService.findAgencies(pageable);
		return new ResponseEntity<>(agencies, HttpStatus.OK);
	}

	@PutMapping("/updateAgency")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<AgencyDTO> updateAgency(@RequestBody AgencyDTO agency) {
		return new ResponseEntity<>(adminService.updateAgency(agency), HttpStatus.OK);
	}

	@PostMapping("/deleteAgency/{id}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<AgencyDTO>> deleteAgency(@PathVariable("id") Long id, Pageable pageable) {
		adminService.deleteAgency(id, pageable);
		List<AgencyDTO> agencies = adminService.findAgencies(pageable);
		return new ResponseEntity<>(agencies, HttpStatus.OK);
	}

	@GetMapping("/searchAgencies")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public ResponseEntity<List<AgencyDTO>> findAgencies(@RequestParam(name = "search", defaultValue = "") String search,
			Pageable pageable) {
		List<AgencyDTO> users = adminService.searchAgencies(search, pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

}

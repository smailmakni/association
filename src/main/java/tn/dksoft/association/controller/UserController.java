package tn.dksoft.association.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.UserDTO;
import tn.dksoft.association.services.UserService;

@RestController
@RequestMapping(value = "/api/user", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> profile() {
		return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<UserDTO> changePassword(String password, String nouveauPassword, String confirmePassword) {
		userService.changePassword(password, nouveauPassword, confirmePassword);
		UserDTO userDTO = userService.getCurrentUser();
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
}

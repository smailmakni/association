package tn.dksoft.association.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tn.dksoft.association.firebase.FirebaseStorageService;
import tn.dksoft.association.repository.DocumentRepository;
import tn.dksoft.association.services.DocumentService;

@RestController
@RequestMapping(value = "/api/document", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class DocumentController {

	private DocumentService documentService;
	private DocumentRepository documentRepository;

	@Autowired
	private FirebaseStorageService storageService;

	@PostMapping("/upload")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			storageService.storeFile(file);
			documentService.addDocument(file);
			return "File uploaded successfully!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file.";
		}
	}

	@DeleteMapping("/delete/{filename}")
	public String deleteFile(@PathVariable String filename) throws ExecutionException, InterruptedException {
		storageService.deleteFile(filename);
		documentService.deleteDocument(documentRepository.findByNom(filename).getId());
		return "File deleted successfully!";
	}
}
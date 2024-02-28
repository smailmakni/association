package tn.dksoft.association.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.firebase.FirebaseStorageService;
import tn.dksoft.association.mappers.DocumentMapper;
import tn.dksoft.association.repository.DocumentRepository;

@Service
@Transactional
@AllArgsConstructor
public class DocumentService {

	private DocumentRepository documentRepository;
	private DocumentMapper documentMapper;

	@Autowired
	private FirebaseStorageService firebaseStorageService;

	public void addDocument(@RequestParam("file") MultipartFile multipartFile) {
		DocumentDTO document = new DocumentDTO();
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timestampStr = localDateTime.format(formatter);
		String filename = timestampStr.concat(multipartFile.getOriginalFilename()).replaceAll("[:\\s]", "")
				.replaceAll(" ", "-");
		document.setNom(filename);
		String urlFile = firebaseStorageService.getDownloadUrl(filename);
		document.setUrl(urlFile);
		documentRepository.save(documentMapper.fromDtoToEntity(document));
	}

	public void deleteDocument(Long documentId) throws ExecutionException, InterruptedException {
		documentRepository.deleteById(documentId);
	}

}

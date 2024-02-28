package tn.dksoft.association.firebase;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseStorageService {

	private static final String BUCKET_NAME = "association";
	private final Storage storage = StorageOptions.getDefaultInstance().getService();

	public void storeFile(MultipartFile file) throws IOException {
		BlobId blobId = BlobId.of(BUCKET_NAME, file.getOriginalFilename());
		Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), file.getBytes());
	}

	public String getDownloadUrl(String filename) {
		try {
			URL url = storage.signUrl(BlobInfo.newBuilder(BUCKET_NAME, filename).build(), 1, TimeUnit.HOURS);
			return url.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] getFile(String filename) throws IOException {
		BlobId blobId = BlobId.of(BUCKET_NAME, filename);
		Blob blob = storage.get(blobId);
		return blob.getContent();
	}

	public void deleteFile(String filename) {
		BlobId blobId = BlobId.of(BUCKET_NAME, filename);
		storage.delete(blobId);
	}
}
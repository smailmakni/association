package tn.dksoft.association.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

	@Bean
	FirebaseApp initializeFirebase() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		return FirebaseApp.initializeApp(options);
	}
}

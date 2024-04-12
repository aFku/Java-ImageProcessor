package org.rcbg.afku.ImageAdjusterApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:websockets.xml")
public class ImageAdjusterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageAdjusterAppApplication.class, args);
	}

}

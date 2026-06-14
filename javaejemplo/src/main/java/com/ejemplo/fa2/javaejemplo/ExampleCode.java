package com.ejemplo.fa2.javaejemplo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ejemplo.fa2.javaejemplo.utils.GeneratedQr;

@Configuration
public class ExampleCode {


    @Bean
    CommandLineRunner commandLineRunner(GeneratedQr qr) {
        return args -> {
            String message = "https://www.youtube.com/watch?v=qzYpgbP8RHA";
            String urlqr = qr.generateQrCode(message);
            System.out.println(urlqr);
        };
    }
}

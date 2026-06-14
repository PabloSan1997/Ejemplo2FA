package com.ejemplo.fa2.javaejemplo;

import com.ejemplo.fa2.javaejemplo.utils.TwoFAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ejemplo.fa2.javaejemplo.utils.GeneratedQr;

@Configuration
public class ExampleCode {

    @Autowired
    private TwoFAService twoFAService;

    @Bean
    CommandLineRunner commandLineRunner(GeneratedQr qr) {
        return args -> {
//           String mira = twoFAService.secretnumber();
//            System.out.println(mira);
//            String url = twoFAService.generateQr();
//            System.out.println(url);
//            String code = twoFAService.viewcode();
//            System.out.println("code:"+code);
            String code = "867453";
            Boolean res = twoFAService.verifyCode(code);
            System.out.println("res = " + res);
        };
    }
}

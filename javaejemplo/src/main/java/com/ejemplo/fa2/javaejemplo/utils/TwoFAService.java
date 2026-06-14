package com.ejemplo.fa2.javaejemplo.utils;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TwoFAService {
    private final SecretGenerator secretGenerator = new DefaultSecretGenerator();
    private final QrGenerator qrGenerator = new ZxingPngQrGenerator();
    private final TimeProvider timeProvider = new SystemTimeProvider();
    private final String mysecret = "JWK7NFELV2TGTYGWUJKPMI35WGKUH45R";
    public String secretnumber(){
        return secretGenerator.generate();
    }

    public String generateQr() throws QrGenerationException {
        QrData qrData = new QrData.Builder()
                .label("usuario@usuario.com")
                .secret(mysecret)
                .issuer("Mi ejemplo")
                .build();
        byte[] imageDta = qrGenerator.generate(qrData);
        return Utils.getDataUriForImage(imageDta, qrGenerator.getImageMimeType());
    }

    public String viewcode() throws CodeGenerationException {
        DefaultCodeGenerator defaultSecretGenerator = new DefaultCodeGenerator();
        return defaultSecretGenerator.generate(mysecret, timeProvider.getTime()/30);
    }

    public Boolean verifyCode(String code) throws QrGenerationException {
        DefaultCodeGenerator defaultSecretGenerator = new DefaultCodeGenerator();
        DefaultCodeVerifier codeVerifier = new DefaultCodeVerifier(defaultSecretGenerator, timeProvider);
        codeVerifier.setAllowedTimePeriodDiscrepancy(0);
        return codeVerifier.isValidCode(mysecret, code);
    }
}

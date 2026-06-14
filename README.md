# 2FA - Apuntes de Implementación

Ejemplos de implementación de autenticación en dos factores (2FA) usando TOTP (Time-based One-Time Password).

---

## Contenido

| Implementación | Tecnología | Librería |
|----------------|------------|----------|
| `nodejs/` | TypeScript + Express | `otplib` |
| `javaejemplo/` | Java 21 + Spring Boot | `dev.samstevens.totp` |

---

## Node.js (TypeScript)

**Ruta:** `nodejs/`

### Instalación

```bash
cd nodejs
npm install
```

### Dependencias

- `otplib` - Generación y verificación de tokens TOTP
- `qrcode` - Generación de códigos QR
- `express` - Framework web

### Comandos

```bash
npm run dev     # Ejecutar en desarrollo
npm run build   # Compilar TypeScript
npm run start   # Ejecutar en producción
```

### Flujo básico

```typescript
import { generateSecret, generateURI, generate, verify } from 'otplib';
import { generateQR } from './utils/generatedQr';

// 1. Generar secreto
const secret = generateSecret();

// 2. Crear URI para el QR
const uri = generateURI({
    issuer: "MiApp",
    label: 'usuario@email.com',
    secret
});

// 3. Generar imagen QR
const qrDataUrl = await generateQR(uri);

// 4. Generar token (para testing)
const token = await generate({ secret });

// 5. Verificar token
const result = await verify({ secret, token });
```

### Archivos clave

- `src/index.ts` - Ejemplo de uso de otplib
- `src/utils/generatedQr.ts` - Generador de QR como Data URL

---

## Java (Spring Boot)

**Ruta:** `javaejemplo/`

### Instalación

```bash
cd javaejemplo
./mvnw compile   # Linux/Mac
mvnw.cmd compile # Windows
```

### Dependencias

- `dev.samstevens.totp:totp:1.7.1` - Librería TOTP
- `com.google.zxing` - Generación de QR (incluida en totp)

### Ejecución

```bash
./mvnw spring-boot:run
```

### Flujo básico

```java
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.util.Utils;

// 1. Generar secreto
SecretGenerator secretGenerator = new DefaultSecretGenerator();
String secret = secretGenerator.generate();

// 2. Generar QR
QrData qrData = new QrData.Builder()
    .label("usuario@email.com")
    .secret(secret)
    .issuer("Mi App")
    .build();

QrGenerator qrGenerator = new ZxingPngQrGenerator();
byte[] imageData = qrGenerator.generate(qrData);
String dataUri = Utils.getDataUriForImage(imageData, qrGenerator.getImageMimeType());

// 3. Generar código
DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator();
String code = codeGenerator.generate(secret, timeProvider.getTime() / 30);

// 4. Verificar código
DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
boolean isValid = verifier.isValidCode(secret, userCode);
```

### Archivos clave

- `src/main/java/.../utils/TwoFAService.java` - Servicio principal de 2FA
- `src/main/java/.../utils/GeneratedQr.java` - Generador QR con ZXing
- `src/main/java/.../ExampleCode.java` - Ejemplo de uso

---

## Conceptos TOTP

- **Secreto:** Clave compartida entre servidor y app authenticator
- **Período:** Código válido por 30 segundos (estándar)
- **Issuer:** Nombre de la app que aparece en el authenticator
- **Label:** Identificador del usuario (usualmente email)

### Apps compatibles

- Google Authenticator
- Microsoft Authenticator
- Authy
- 1Password

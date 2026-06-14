import { generate, generateSecret, generateURI, verify } from 'otplib';
import { generateQR } from './utils/generatedQr';




 const secret = generateSecret();


// console.log(secret);

async function proceso() {
    const uri = generateURI({
        issuer: "MyApp",
        label: 'el titulo',
        secret
    });

    // const generatedQr = await generateQR(uri);
    // console.log(generatedQr);

    // const token = await generate({secret});
    // console.log(token);

    const token = '078082'
    const view = await verify({secret, token});
    console.log(view.valid);

}

proceso();
import Qrcode from 'qrcode';



export const generateQR = (prop:string) => new Promise<string>((resolve, reject) => {
    Qrcode.toDataURL(prop, (err, url) => {
        if (err)
            reject(err);
        else
            resolve(url);
    });
});
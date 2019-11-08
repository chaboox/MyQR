package com.deboosere.myqr;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeGenerator {
    private Bitmap QRcode;

    public Bitmap getQRCode(String code){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,1000,1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
           // image_view.setImageBitmap(bitmap);
            return bitmap;
        }
        catch (WriterException e) {
           // Toast.makeText( getActivity().getApplicationContext(), e.toString(),  Toast.LENGTH_SHORT).show();
            Log.d("TAG", "getQRCode: " + e.toString());
        }
        return null;
    }
}

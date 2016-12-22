package com.example.christopher.rangers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static android.R.attr.width;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class ShareScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Share your planner");


        boolean displayAlert = true;
        if(displayAlert)
            alert();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain();
            }
        });
        fab.setImageResource(R.drawable.ic_check);
        String qrData = getIntent().getStringExtra("PLANNER");
        int qrCodeDimention = 500;
        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }private void alert()
    {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("To give your friends a copy of your planner, have them scan this symbol with the " +
                "\"read\" option on the home screen. It will always share the top left planer in your home screen" +
                " so ensure that's the correct one.");
        dlgAlert.setTitle("Sharing Your Schedule");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //write to never show again
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    private void returnToMain()
    {
        Intent i = new Intent(this, HomeScreen.class);
        startActivity(i);
    }
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        int size = 250;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, size, size, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

}

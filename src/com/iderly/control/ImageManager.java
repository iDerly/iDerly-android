package com.iderly.control;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.DisplayMetrics;

public class ImageManager {
	private static ImageManager instance = new ImageManager();
	private static float MAX_DIMENS;
	
	public static ImageManager getInstance() {
		return instance;
	}
	
	public static void init (Context context) {
		MAX_DIMENS = convertDpToPixel(180f, context);
	}
	
	public static float convertDpToPixel (float dp, Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (dp * displayMetrics.density);
	}
	
	public String encodeImageBase64 (Bitmap image) {
		Bitmap rescaledImage = rescaleImage(image);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		if(rescaledImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                || rescaledImage.compress(Bitmap.CompressFormat.PNG, 100, baos));
		
		byte[] b = baos.toByteArray();
		
		// Sent this encoded STRING to the PHP server
		return Base64.encodeToString(b, Base64.DEFAULT)
                .replace("\n", "").replace("\r", "");
	}
	
	public Bitmap decodeImageBase64 (String encodedImage) {
		byte[] imageByte = Base64.decode(encodedImage, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
	}
	
	public Bitmap rescaleImage (Bitmap image) {
		float oriWidth = image.getWidth(),
                oriHeight = image.getHeight();
        float scaling = Math.min(MAX_DIMENS / oriWidth, MAX_DIMENS / oriHeight);

        float scaledWidth = scaling * oriWidth,
                scaledHeight = scaling * oriHeight;

        return Bitmap.createScaledBitmap(image, (int) scaledWidth, (int) scaledHeight, true);
	}
}

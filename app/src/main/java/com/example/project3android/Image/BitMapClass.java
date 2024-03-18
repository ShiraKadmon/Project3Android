package com.example.project3android.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class BitMapClass {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int width, int height, int pixels) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.BLACK); // Set border color if needed
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint); // Draw the bitmap into the specified rectangle

        return output;
    }
    public static Bitmap compressBitmap(Bitmap originalBitmap, int quality) {
        // Compress the original bitmap to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        // Decode the byte array back into a bitmap
        byte[] bitmapData = out.toByteArray();
        return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, float maxWidth, float maxHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = maxWidth / originalWidth;
        float scaleHeight = maxHeight / originalHeight;
        float scaleFactor = Math.min(scaleWidth, scaleHeight); // Maintains the aspect ratio
        return Bitmap.createScaledBitmap(bitmap, (int)(originalWidth * scaleFactor),
                (int)(originalHeight * scaleFactor), true);
    }
    /*
    public static Bitmap resizeBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        // Get the current dimensions of the bitmap
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Calculate the scale factors
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Create a matrix for the manipulation
        android.graphics.Matrix matrix = new android.graphics.Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // Recreate the new bitmap with the resized dimensions
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

     */

    // Convert Bitmap to Base64-encoded String
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // Get the Base64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Compress Bitmap
        byte[] byteArray = outputStream.toByteArray();
        String base64Image = "";
        if (byteArray != null && byteArray.length > 0) {
            String base64EncodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            base64Image = "data:image/jpeg;base64," + base64EncodedImage;
        }
        return base64Image;
    }

    public static int bitmapToInt(Bitmap bitmap) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bitmapToByteArray(bitmap));
            int result = 0;
            for (int i = 0; i < 4; i++) {
                result += ((int) hash[i] & 0xFF) << (24 - i * 8);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Bitmap loadImageAsync(String imageUrl) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        Future<Bitmap> future = executorService.submit(() -> convertUrlToBitmap(imageUrl));
//
//        try {
//            return future.get();
//        } catch (Exception e) {
//            // Handle exceptions during image loading
//            return null;
//        } finally {
//            // Shutdown the executor to release resources
//            executorService.shutdown();
//        }
        return base64ToBitmap(imageUrl);
    }

    public static Bitmap convertUrlToBitmap(String image) {
        Bitmap bitmap;
        try {
            URL url = new URL(image);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            bitmap = null;
        }
        return bitmap;
    }

    public static Bitmap base64ToBitmap(String base64) {
        if (base64.startsWith("data:image/png;base64,")) {
            base64 = base64.substring("data:image/png;base64,".length());
        } else if (base64.startsWith("data:image/jpeg;base64,")) {
            base64 = base64.substring("data:image/jpeg;base64,".length());
        }
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}

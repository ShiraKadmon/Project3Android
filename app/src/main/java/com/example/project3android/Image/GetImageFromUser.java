package com.example.project3android.Image;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* Class to get an image from the user, either from the gallery or the camera */
public class GetImageFromUser {

    // Request code for permissions
    public static final int PERMISSION_REQUEST_CODE = 100;

    // Method to handle permission results
    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull
    String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                            grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(activity, "Need permission to access photos to " +
                                "continue", Toast.LENGTH_SHORT).show();

                    }
                    if (permissions[i].equals(Manifest.permission.CAMERA) && grantResults[i] ==
                            PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(activity, "Need permission to access camera to " +
                                "continue", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    }

    // Method to check and request permissions
    public static void checkReadExternalStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readStoragePermission = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA);

            List<String> permissionsToRequest = new ArrayList<>();
            if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.CAMERA);
            }

            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(
                        new String[0]), PERMISSION_REQUEST_CODE);
            }
        }
    }

    // Method to save the image to the gallery
    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + "ImagesFolder");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, fos);
                Objects.requireNonNull(fos);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Image Not Saved \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method to launch image picker
    public static void pickImage(Activity activity, int requestCode) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");

        // Create an Intent to capture an image using the camera
        Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a chooser Intent that includes both gallery and camera options
        Intent chooserIntent = Intent.createChooser(pickImageIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureImageIntent});

        activity.startActivityForResult(chooserIntent, requestCode);
    }

    // Method to handle image selection result
    public static Bitmap handleImageResult(Activity activity, Intent data, int cornerRadius) {
        Bitmap selectedBitmap = null;
        if (data != null && data.getData() != null) {
            // The user has successfully picked an image from the gallery
            Uri selectedImageUri = data.getData();
            try {
                // Convert the Uri to a Bitmap
                selectedBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                        selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            // The image is from the camera
            // Use the thumbnail directly
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Use the thumbnail as a Bitmap
                selectedBitmap = (Bitmap) extras.get("data");
            } else {
                return null;
            }
        }

        selectedBitmap = BitMapClass.getRoundedCornerBitmap(selectedBitmap,
                selectedBitmap.getWidth(), selectedBitmap.getHeight(), cornerRadius);
        selectedBitmap = BitMapClass.compressBitmap(selectedBitmap, 70);
        selectedBitmap = BitMapClass.resizeBitmap(selectedBitmap, 400, 400);
        return selectedBitmap;
    }

}


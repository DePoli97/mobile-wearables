package com.example.inventorymapper;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.net.Uri;

public final class Storage {
    private static final StorageReference mStorage = MyApplication.getStorageReference();

    private Storage() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    /**
     * Uploads the given image data to Firebase Storage at the specified file path.
     *
     * @param imageData The image data as a byte array.
     * @param filePath  The path in the storage bucket where the image will be stored (e.g. "images/myItemImage.jpg").
     * @param listener  An OnCompleteListener to handle success or failure of the upload task.
     */
    public static void uploadImage(byte[] imageData, String filePath, OnCompleteListener<UploadTask.TaskSnapshot> listener) {
        StorageReference fileRef = mStorage.child(filePath);
        UploadTask uploadTask = fileRef.putBytes(imageData);
        uploadTask.addOnCompleteListener(listener);
    }

    /**
     * Retrieves the download URL of the specified file from Firebase Storage.
     *
     * @param filePath          The path in the storage bucket where the image is stored.
     * @param successListener   A listener that will be called with the download URL on success.
     * @param failureListener   A listener that will be called if there's an error retrieving the download URL.
     */
    public static void getDownloadUrl(String filePath, OnSuccessListener<Uri> successListener, OnFailureListener failureListener) {
        StorageReference fileRef = mStorage.child(filePath);
        fileRef.getDownloadUrl()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }
}
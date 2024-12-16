package com.example.inventorymapper;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.net.Uri;
import android.util.Log;

public final class Storage {
    private static final StorageReference mStorage = MyApplication.getStorageReference();

    private Storage() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static void uploadImage(byte[] imageData, String filePath, OnCompleteListener<UploadTask.TaskSnapshot> listener) {
        StorageReference fileRef = mStorage.child(filePath);
        UploadTask uploadTask = fileRef.putBytes(imageData);
        uploadTask.addOnCompleteListener(listener);
    }

    public static void getDownloadUrl(String filePath, OnSuccessListener<Uri> successListener, OnFailureListener failureListener) {
        StorageReference fileRef = mStorage.child(filePath);
        Log.d("Storage", "Getting download URL for: " + filePath);
        Log.d("Storage", "FileRef: " + fileRef);
        fileRef.getDownloadUrl()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public static void deleteImage(String fileName) {
        StorageReference fileRef = mStorage.child("images/" + fileName);
        fileRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Storage", "Deleted file: " + fileName);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Storage", "Failed to delete file: " + fileName, e);
            }
        });
}
}
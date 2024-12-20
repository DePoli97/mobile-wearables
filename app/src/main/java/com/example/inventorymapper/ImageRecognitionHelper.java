package com.example.inventorymapper;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.ArrayList;
import java.util.List;

public class ImageRecognitionHelper {
    private final ImageLabeler labeler;
    private static final float CONFIDENCE_THRESHOLD = 0.7f;

    public ImageRecognitionHelper() {
        labeler = ImageLabeling.getClient(
            new ImageLabelerOptions.Builder()
                .setConfidenceThreshold(CONFIDENCE_THRESHOLD)
                .build()
        );
    }

    public void recognizeImage(Bitmap bitmap, RecognitionCallback callback) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    List<String> results = new ArrayList<>();
                    for (ImageLabel label : labels) {
                        String text = label.getText();
                        float confidence = label.getConfidence();
                        results.add(text + " : " + confidence);
                    }
                    callback.onSuccess(results);
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public void recognizeMainSubject(Bitmap bitmap, MainSubjectCallback callback) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        
        labeler.process(image)
            .addOnSuccessListener(labels -> {
                if (!labels.isEmpty()) {
                    // Get the label with highest confidence
                    ImageLabel topLabel = labels.get(0);
                    callback.onSuccess(topLabel.getText(), topLabel.getConfidence());
                } else {
                    callback.onError("No subjects detected");
                }
            })
            .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    public interface RecognitionCallback {
        void onSuccess(List<String> labels);
        void onError(String error);
    }

    public interface MainSubjectCallback {
        void onSuccess(String label, float confidence);
        void onError(String error);
    }
}
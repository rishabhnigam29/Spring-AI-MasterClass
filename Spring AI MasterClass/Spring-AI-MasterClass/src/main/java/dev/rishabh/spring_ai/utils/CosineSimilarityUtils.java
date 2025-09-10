package dev.rishabh.spring_ai.utils;

public class CosineSimilarityUtils {
    public static double cosineSimilarity(float[] a, float[] b){
        if (a.length!=b.length){
            throw new IllegalArgumentException("Vector must be of similar length");
        }

        double dotProduct=0.0, magnitudeA=0.0, magnitudeB=0.0;

        for (int i = 0; i < a.length; i++) {
            dotProduct += a[i]*b[i];
            magnitudeA += a[i]*a[i];
            magnitudeB += b[i]*b[i];
        }

        if(magnitudeA == 0.0 || magnitudeB==0.0){
            return 0.0;
        }

        return dotProduct/ (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
    }
}

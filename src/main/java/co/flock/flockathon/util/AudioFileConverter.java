package co.flock.flockathon.util;

import java.io.IOException;

/**
 * Created by chetan on 25/9/16.
 */
public class AudioFileConverter {
    public static String convertWavToFlac(String wavFilePath) throws IOException {
        String flacFilePath = wavFilePath.replace(".wav",".flac");
        Runtime.getRuntime().exec("sox "+ wavFilePath+" "+flacFilePath+" rate 16k");
        return flacFilePath;
    }
}

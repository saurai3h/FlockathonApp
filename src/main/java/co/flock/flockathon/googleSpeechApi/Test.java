package co.flock.flockathon.googleSpeechApi;

import co.flock.flockathon.util.AudioFileConverter;

/**
 * Created by chetan on 25/9/16.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        /*String [] fileNames = {"/home/chetan/test.flac", "/home/chetan/Downloads/test.flac"};
        for(String fileName : fileNames){
            try {
                System.out.println(SpeechToText.convert(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        String flacFile = AudioFileConverter.convertWavToFlac("/home/chetan/test2.wav");
        System.out.println(flacFile);
        System.out.println(SpeechToText.convert(flacFile));
    }
}

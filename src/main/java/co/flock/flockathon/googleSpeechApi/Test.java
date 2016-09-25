package co.flock.flockathon.googleSpeechApi;

/**
 * Created by chetan on 25/9/16.
 */
public class Test {
    public static void main(String[] args) {
        String [] fileNames = {"/home/chetan/test.flac", "/home/chetan/test.flac"};
        for(String fileName : fileNames){
            try {
                System.out.println(SpeechToText.convert(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

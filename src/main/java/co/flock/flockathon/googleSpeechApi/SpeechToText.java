package co.flock.flockathon.googleSpeechApi;

import io.grpc.ManagedChannel;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;

/**
 * Created by chetan on 25/9/16.
 */
public class SpeechToText {
    private static Logger logger = Logger.getLogger(SpeechToText.class);
    private static String host = "speech.googleapis.com";
    private static Integer port = 443;
    private static Integer sampling = 16000;
    private static ManagedChannel channel;
    static {
        try {
            channel = AsyncRecognizeClient.createChannel(host, port);
        } catch (IOException e) {
            logger.error("Could not create channel, Reason :: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
    public static String convert(String audioFile) throws Exception {
        SyncRecognizeClient client = new SyncRecognizeClient(channel, URI.create(audioFile), sampling);
        String text = text = client.recognize();;
        /*try {
            text = client.recognize();
        } finally {
            client.shutdown();
        }*/
        return text;
    }
}

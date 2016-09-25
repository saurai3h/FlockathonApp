package co.flock.flockathon.Controller.APIControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class SpeechInputs {

    @RequestMapping(value = "/recording/stop", method = RequestMethod.GET)
    @ResponseBody
    public String stopSpeech(String voice) {
        return "blah blah blah!";
    }
}

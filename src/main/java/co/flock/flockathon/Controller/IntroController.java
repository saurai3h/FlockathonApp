package co.flock.flockathon.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/intro", method = RequestMethod.GET)
public class IntroController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("intro");
        return mav;
    }
}

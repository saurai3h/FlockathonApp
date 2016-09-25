package co.flock.flockathon.Controller.ViewControllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/intro")
public class IntroController {

    private static final Logger LOG = Logger.getLogger(IntroController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView intro(ModelAndView mav) {
        mav.setViewName("intro");
        return mav;
    }
}

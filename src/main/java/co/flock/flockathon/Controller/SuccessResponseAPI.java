package co.flock.flockathon.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/run", method = RequestMethod.GET)
public class SuccessResponseAPI {

    @RequestMapping(value = "/")
    public ArrayList<String> getDemoResponse(HttpServletRequest request)    {
        System.out.println(request.getQueryString());
        System.out.println(request.getHeaderNames().nextElement());
        return new ArrayList();
    }
}
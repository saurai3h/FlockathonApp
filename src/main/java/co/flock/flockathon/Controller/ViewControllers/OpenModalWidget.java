package co.flock.flockathon.Controller.ViewControllers;

import co.flock.flockathon.util.QueryParams;
import co.flock.flockathon.util.QueryParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/run")
public class OpenModalWidget {

    private static final Logger LOG = Logger.getLogger(OpenModalWidget.class);
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getModalView(ModelAndView mav, HttpServletRequest request)    {
        try {
            QueryParams params = new QueryParams(QueryParser.parseQueryString(request.getQueryString()));
            JsonObject element = new Gson().fromJson(params.getParameter("flockEvent"), JsonElement.class).getAsJsonObject();
            mav.getModelMap().addAttribute("sending-entity", element.get("userId").getAsString());
            mav.getModelMap().addAttribute("receiving-entity", element.get("chat").getAsString());
        }catch (Exception e){
            LOG.error(e.toString());
        }
        mav.setViewName("run");
        return mav;
    }
}
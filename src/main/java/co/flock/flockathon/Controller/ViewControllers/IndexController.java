package co.flock.flockathon.Controller.ViewControllers;

import co.flock.flockathon.redis_cluster.RandomReadRedisCluster;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger LOG = Logger.getLogger(IntroController.class);

//    Controller which gets hit for installs/uninstalls
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView index(ModelAndView mav, HttpServletRequest request) {
        try {
            JsonObject element = new Gson().fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), JsonElement.class).getAsJsonObject();
            if(element.has("userToken"))    {
                Jedis jedis = RandomReadRedisCluster.getInstance("c8-data-store-5.srv.media.net:6379").getRandomJedisPool().getResource();
                jedis.hmset(element.get("userId").getAsString(), new HashMap<String, String>(){{
                    put("name", element.get("name").getAsString());
                    put("userToken", element.get("userToken").getAsString());
                    put("userId", element.get("userId").getAsString());
                }});
            }
        }catch (Exception e)    {
            LOG.info(e.toString());
        }
        mav.setViewName("index");
        return mav;
    }
}

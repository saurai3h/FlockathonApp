package co.flock.flockathon.Controller.APIControllers;

import co.flock.flockathon.redis_cluster.RandomReadRedisCluster;
import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping(value = "/api")
public class MessageSenderController {

    private static final Logger LOG = Logger.getLogger(MessageSenderController.class);

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public void sendMessage(String sender, String receiver, String toSend) {
        try {
            Jedis jedis = RandomReadRedisCluster.getInstance("c8-data-store-5.srv.media.net:6379").getRandomJedisPool().getResource();
            LOG.info(jedis.hget(sender, "userToken"));
            LOG.info(toSend);
            // second parameter is true in case you are using in the PROD
            FlockApiClient flockApiClient = new FlockApiClient(jedis.hget(sender, "userToken"), true);
            Message message = new Message(receiver, toSend);
            FlockMessage flockMessage = new FlockMessage(message);
            String messageId = flockApiClient.chatSendMessage(flockMessage);
            LOG.info("sent message with id " + messageId);
        }catch (Exception e)    {
            LOG.error(e.toString());
        }
    }
}

package ir.pi.project.logic.explore;

import ir.pi.project.*;
import ir.pi.project.logic.TimeLine;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    static private final Logger logger= LogManager.getLogger(World.class);
    public static void worldLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered World");


            User user=User.getByID(id);
            List<Integer> tweets = new ArrayList<>();
            File directory = new File("./src/main/resources/Info/Tweets");
            for (File file :
                    directory.listFiles()) {
                Tweet tweet=Tweet.getByID(ID.getIdFromFileName(file.getName()));
                User user1=User.getByID(tweet.getWriter());
                System.out.println(user1.getUserName()+" "+user1.isMutedBy(id));
                if(user1.isActive() && !user1.isMutedBy(id)) {
                    if(user1.isPublic() || user.isFollowing(user1.getId())) {
                        tweets.add(ID.getIdFromFileName(file.getName()));
                    }
                }
            }

            if(!tweets.isEmpty()) {
                Collections.shuffle(tweets);
                TimeLine.showTweets(tweets, id);
            }else {
                Cli.print("\nWorld is empty! =)", ConsoleColors.RED_BOLD);
            }

    }
}

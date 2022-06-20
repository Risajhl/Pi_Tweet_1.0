package ir.pi.project.logic.myPage;

import ir.pi.project.*;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewTweet {
    static private final Logger logger= LogManager.getLogger(NewTweet.class);
    public static void newTweetLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered NewTweet");
        Cli.print("\nWrite Your Tweet:", ConsoleColors.WHITE_BRIGHT);
        User user=User.getByID(id);
        String tweet= Cli.get();
        Tweet newTwt=new Tweet(user,tweet);
        newTwt.saveIntoDB();
        user.getTweets().add(newTwt.getId());
        logger.info(user.getUserName()+" added a new tweet");
        Cli.print("\nYou Tweeted!",ConsoleColors.GREEN_BRIGHT);
        user.saveIntoDB();

    }


}

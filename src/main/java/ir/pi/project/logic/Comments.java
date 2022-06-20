package ir.pi.project.logic;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Comments {
    static private final Logger logger= LogManager.getLogger(Comments.class);
    public static void newCommentLogic(int userid,int tweetId){
        User user=User.getByID(userid);
            Cli.print("\nType your comment:", ConsoleColors.WHITE_BRIGHT);
        String ans= Cli.get();
        Tweet comment=new Tweet(user,ans);
        Tweet tweet=Tweet.getByID(tweetId);
        tweet.getComments().add(comment.getId());
        tweet.saveIntoDB();
        comment.saveIntoDB();
        user.saveIntoDB();
        logger.info(user.getUserName()+" commented on tweet with id: "+tweetId);

    }

    public static void commentsHelp(int tweetId,int userId){
        Tweet tweet=Tweet.getByID(tweetId);
        User user=User.getByID(userId);
        List<Integer> comments=new ArrayList<>();
        for(int i=0;i<tweet.getComments().size();i++){
            Tweet comment=Tweet.getByID(tweet.getComments().get(i));
            User writer=User.getByID(comment.getWriter());
            if(writer.isPublic() || user.isFollowing(writer.getId()) || writer.getId()==userId ){
                if(writer.getId()==userId){
                    comments.add(comment.getId());

                }
                else if(writer.isActive() && !writer.isMutedBy(userId)){
                    comments.add(comment.getId());
                }
            }
        }

        if(comments.isEmpty()){
            Cli.print("\nNO comments to show!",ConsoleColors.RED_BOLD);
        }
        else {
            TimeLine.showTweets(comments,userId);
        }
    }
}

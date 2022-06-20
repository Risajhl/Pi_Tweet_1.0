package ir.pi.project.logic;

import ir.pi.project.*;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.messaging.Messages;
import ir.pi.project.logic.messaging.SavedMessages;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeLine {
    static private final Logger logger= LogManager.getLogger(TimeLine.class);

    public static void myTimeLineLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered TimeLine");
            List<Integer> followingTweets = new ArrayList<>();
            User user = User.getByID(id);
            if (!user.getFollowings().isEmpty()) {
                for (int i = 0; i < user.getFollowings().size(); i++) {
                    User user1 = User.getByID(user.getFollowings().get(i));
                    if (user1.isActive()) {
                        if (!user1.isMutedBy(id) && !user1.isBlockedBy(id)) {
                            if (!user1.getTweets().isEmpty()) {
                                for (int j = 0; j < user1.getTweets().size(); j++) {
                                    if(user1.getRetweets().contains(user1.getTweets().get(j))){
                                        Tweet tweet=Tweet.getByID(user1.getTweets().get(j));
                                        User writer =User.getByID(tweet.getWriter());
                                        if(writer.isPublic() || user.isFollowing(writer.getId())){
                                            if(!writer.isBlockedBy(user.getId()) && !writer.isMutedBy(id) && writer.isActive() && writer.getId()!=id){
                                                followingTweets.add(user1.getTweets().get(j));
                                            }
                                        }
                                    }
                                    else {
                                        followingTweets.add(user1.getTweets().get(j));
                                    }
                                }
                            }
                            if (!user1.getLikedTweets().isEmpty()) {
                                for (int j = 0; j < user1.getLikedTweets().size(); j++) {
                                    Tweet tweet = Tweet.getByID(user1.getLikedTweets().get(j));
                                    User user2=User.getByID(tweet.getWriter());
                                    if(user2.isActive() && !user2.isMutedBy(id) && user2.getId()!=id){
                                    if (user2.isPublic() || user.isFollowing(user2.getId())) {
                                        followingTweets.add(user1.getLikedTweets().get(j));
                                    }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!followingTweets.isEmpty()){
                    Collections.shuffle(followingTweets);
                    showTweets(followingTweets,id);
                }else {
                    Cli.print("\nNo tweets to show in TimeLine! :(",ConsoleColors.RED_BOLD);
                }
            }

            else {
                while (true) {
                    Cli.print("\nNo tweets to show in TimeLine! Follow some people;)",ConsoleColors.RED_BOLD);
                    Cli.print("\nType back to go to MainMenu",ConsoleColors.WHITE_BOLD);
                    String ans = Cli.get();
                    if (ans.equals("Back")) {
                        break;
                    }
                    else if(ans.equals("Quit")){
                        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
                        user.setLastSeen("LastSeen: "+date);
                        user.setOnline(false);
                        user.saveIntoDB();
                        logger.info(user.getUserName()+" logged out");
                        Welcome.welcomeLogic();
                    }
                    else {
                        Cli.invalid();
                    }

                }
            }


    }

    public static void showTweets(List<Integer> tweets,int id){
        int i=tweets.size()-1;
        while (true) {
            if(!tweets.isEmpty()) {
                User user = User.getByID(id);
                    Tweet tweet = Tweet.getByID(tweets.get(i));
                    User writer=User.getByID(tweet.getWriter());

                            tlShowTweet(tweet.getId());

                    String ans = Cli.get();
                    if (ans.equals("<") && i != tweets.size() - 1) {
                        i++;
                    } else if (ans.equals("<") && i == tweets.size() - 1) {
                        i = tweets.size() - 1;
                    } else if (ans.equals(">") && i != 0) {
                        i--;
                    } else if (ans.equals(">") && i == 0) {
                        i = 0;
                    } else if (ans.equals("Like")) {
                        boolean isLikedBefore = false;
                        if (!user.getLikedTweets().isEmpty()) {
                            for (int j = 0; j < user.getLikedTweets().size(); j++) {
                                if (tweets.get(i).equals(user.getLikedTweets().get(j))) {
                                    isLikedBefore = true;
                                    Tweet tweet1=Tweet.getByID(tweets.get(i));
                                    tweet1.addToLikeNums(-1);
                                    tweet1.saveIntoDB();
                                    user.getLikedTweets().add(tweets.get(i));
                                    user.saveIntoDB();
                                }
                            }
                        }
                        if (!isLikedBefore) {
                            user.getLikedTweets().add(tweets.get(i));
                            tweet.addToLikeNums(1);
                            user.getLikedTweets().remove((Object) tweets.get(i));
                            user.saveIntoDB();
                            tweet.saveIntoDB();

                        }
                    } else if (ans.equals("Comment")) {
                        Comments.newCommentLogic(id, tweets.get(i));
                        Cli.print("\nYou commented on this tweet !",ConsoleColors.GREEN_BRIGHT);

                    } else if (ans.equals("CommentsList")) {
                        Comments.commentsHelp(tweets.get(i),id);
                    } else if (ans.equals("ReTweet")) {
                        Boolean reTweetedBefore = false;
                        for (int j = 0; j < user.getRetweets().size(); j++) {
                            if (tweets.get(i).equals(user.getRetweets().get(j))) {
                                reTweetedBefore = true;

                                Cli.print("\nYou have reTweeted this Before!",ConsoleColors.RED_BOLD);
                            }

                        }
                        if (!reTweetedBefore) {
                            user.getRetweets().add(tweets.get(i));
                            user.getTweets().add(tweets.get(i));
                            System.out.println("\nReTweeted!");
                            user.saveIntoDB();
                        }

                    } else if (ans.equals("Save")) {
                        SavedMessages.addToSavedMessages(tweet.getId(),id);
                        Cli.print("\nsaved into your Messages!",ConsoleColors.GREEN_BRIGHT);

                    } else if (ans.equals("Forward")) {
                        Messages.forward(id,tweet.getId());
                        Cli.print("\nForwarded!",ConsoleColors.GREEN_BRIGHT);

                    } else if (ans.equals("UserProfile")) {
                        if (tweet.getWriter() == id) {
                            ShowUser.showMe(id);
                        } else {
                            ShowUser.showUserLogic(id, tweet.getWriter());
                        }
                    } else if (ans.equals("Back")) {

                        break;
                    }
                    else if(ans.equals("Quit")){
                        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
                        user.setLastSeen("LastSeen: "+date);
                        user.setOnline(false);
                        user.saveIntoDB();
                        logger.info(user.getUserName()+" logged out");

                        Welcome.welcomeLogic();
                    }
                    else if(ans.equals("Exit")){
                        System.exit(1);
                    }
                    else {
                        Cli.invalid();
                    }

            }
            else {
                Cli.print("\nNo tweets to be shown! :(",ConsoleColors.RED);
                break;
            }

        }
    }


    public static void tlShowTweet(int id){
        Tweet tweet=Tweet.getByID(id);
        User writer=User.getByID(tweet.getWriter());
        Cli.print("\n"+writer.getUserName()+": ", ConsoleColors.CYAN_BRIGHT);
        Cli.print(tweet.getText(),ConsoleColors.CYAN);
        Cli.print("♥: "+tweet.getLikesNum(),ConsoleColors.RED_BRIGHT);
        String date=tweet.getTime().getYear()+" "+tweet.getTime().getMonth()+" "+tweet.getTime().getDayOfMonth()+
                "  "+tweet.getTime().getHour()+":"+tweet.getTime().getMinute();
        Cli.print(date,ConsoleColors.BLUE);
        Cli.print("\n✦ Like       ✦ Comment     ✦ CommentsList  \n✦ ReTweet    ✦ Save        ✦ Forward\n✦ UserProfile ",ConsoleColors.WHITE_BRIGHT);
        Cli.print("\n             <         >       ",ConsoleColors.CYAN_BRIGHT);
    }



}

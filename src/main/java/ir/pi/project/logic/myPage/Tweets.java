package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.Comments;
import ir.pi.project.logic.ShowUser;
import ir.pi.project.logic.TimeLine;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.messaging.Messages;
import ir.pi.project.logic.messaging.SavedMessages;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Tweets {
    static private final Logger logger= LogManager.getLogger(Tweet.class);
    public static void tweetsLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Tweets");
        Cli.print("\n~ Your Tweets ~",ConsoleColors.BLUE_BRIGHT);
        User userr=User.getByID(id);
        int i=userr.getTweets().size()-1;
        while (true) {
            User user = User.getByID(id);
            if (!user.getTweets().isEmpty()) {
                Tweet tweeet=Tweet.getByID(user.getTweets().get(i));
                User writer=User.getByID(tweeet.getWriter());

                if(tweeet.getWriter()!=id){
                        Cli.print("\n[ReTweeted]", ConsoleColors.RED);
                    TimeLine.tlShowTweet(user.getTweets().get(i));
                }

                else {
                    showTweet(user.getTweets().get(i));
                }
                    String ans = Cli.get();
                    if (ans.equals("<") && i != user.getTweets().size() - 1) {
                        i++;

                    } else if (ans.equals("<") && i == user.getTweets().size() - 1) {
                        i = user.getTweets().size() - 1;
                    } else if (ans.equals(">") && i != 0) {
                        i--;
                    } else if (ans.equals(">") && i == 0) {
                        i = 0;
                    } else if (ans.equals("Comment")) {
                        Comments.newCommentLogic(id, user.getTweets().get(i));
                        Cli.print("\nYou commented on this tweet !",ConsoleColors.GREEN_BRIGHT);

                    } else if (ans.equals("CommentsList")) {
                        Comments.commentsHelp(user.getTweets().get(i),id);
                    } else if (ans.equals("Like")) {
                        boolean isLikedBefore = false;
                        if (!user.getLikedTweets().isEmpty()) {
                            for (int j = 0; j < user.getLikedTweets().size(); j++) {
                                if (user.getTweets().get(i).equals(user.getLikedTweets().get(j))) {
                                    isLikedBefore = true;
                                    user.getLikedTweets().remove(j);
                                    user.saveIntoDB();
                                    Tweet tweet=Tweet.getByID(user.getTweets().get(i));
                                    tweet.addToLikeNums(-1);
                                    tweet.saveIntoDB();
                                }
                            }
                        }
                        if (!isLikedBefore) {
                            user.getLikedTweets().add(user.getTweets().get(i));
                            Tweet tweet = Tweet.getByID(user.getTweets().get(i));
                            tweet.addToLikeNums(1);
                            user.saveIntoDB();
                            tweet.saveIntoDB();

                        }
                    }
                    else if(ans.equals("ReTweet")){
                        if(tweeet.getWriter()!=id) {
                            Boolean reTweetedBefore = false;
                            for (int j = 0; j < user.getRetweets().size(); j++) {
                                if (user.getTweets().get(i).equals(user.getRetweets().get(j))) {
                                    reTweetedBefore = true;
                                    Cli.print("\nYou have reTweeted this Before!", ConsoleColors.RED_BOLD);
                                }

                            }
                            if (!reTweetedBefore) {
                                user.getRetweets().add(user.getTweets().get(i));
                                user.getTweets().add(user.getTweets().get(i));
                                Cli.print("\nReTweeted!", ConsoleColors.GREEN_BRIGHT);
                                user.saveIntoDB();
                            }
                        }
                        else {
                            Cli.print("\nYou can not reTweet your tweet!", ConsoleColors.RED_BOLD);
                        }
                    }
                    else if(ans.equals("Save")){
                        SavedMessages.addToSavedMessages(user.getTweets().get(i),id);
                        Cli.print("\nsaved into your Messages!",ConsoleColors.GREEN_BRIGHT);
                    }
                    else if(ans.equals("Forward")){
                        Messages.forward(id,user.getTweets().get(i));
                        Cli.print("\nForwarded!",ConsoleColors.GREEN_BRIGHT);
                    }
                    else if(ans.equals("UserProfile")){
                        if (tweeet.getWriter() == id) {
                            ShowUser.showMe(id);
                        } else {
                            ShowUser.showUserLogic(id, tweeet.getWriter());
                        }
                    }
                    else if (ans.equals("Back")) {
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



            } else {
                Cli.print("\nNo Tweets! :(",ConsoleColors.RED);
                Cli.print("\nType Back to go to MyPage",ConsoleColors.WHITE_BRIGHT);
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

    public static void showTweet(int id){
        Tweet tweet=Tweet.getByID(id);
        User writer=User.getByID(tweet.getWriter());
        Cli.print("\n"+writer.getUserName()+": ",ConsoleColors.CYAN_BRIGHT);
        Cli.print(tweet.getText(),ConsoleColors.CYAN);
        Cli.print("♥: "+tweet.getLikesNum(),ConsoleColors.RED_BRIGHT);
        String date=tweet.getTime().getYear()+" "+tweet.getTime().getMonth()+" "+tweet.getTime().getDayOfMonth()+
                "  "+tweet.getTime().getHour()+":"+tweet.getTime().getMinute();
        Cli.print(date,ConsoleColors.BLUE);
        Cli.print("\n✦ Like       ✦ Comment     ✦ CommentsList  \n✦ Forward    ✦ Save        ",ConsoleColors.WHITE_BRIGHT);
        Cli.print("\n             <         >       ",ConsoleColors.CYAN_BRIGHT);
    }
}

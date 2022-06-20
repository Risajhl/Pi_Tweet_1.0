package ir.pi.project.logic.settings;

import ir.pi.project.*;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.messaging.SavedMessages;
import ir.pi.project.model.Group;
import ir.pi.project.model.Message;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

public class DeleteAccount {
    static private final Logger logger= LogManager.getLogger(DeleteAccount.class);

    public static void askForDeleteAccount(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered DeleteAccount");
        while (true) {
            String ans=Cli.getter("\nAre you sure you want to delete your account? (Yes/No)", ConsoleColors.WHITE_BRIGHT);
            User user=User.getByID(id);
            if (ans.equals("Yes")) {
                while (true) {
                    String ans1=Cli.getter("\nEnter your password: ",ConsoleColors.WHITE_BOLD);
                    if (ans1.equals(user.getPassword())) {
                        deleteAccount(id);
                        logger.info(user.getUserName()+" deleted account");
                        Welcome.welcomeLogic();
                    } else if (ans1.equals("Back")) {
                        break;
                    } else {
//                        System.out.println("\n!!! Invalid !!!");
                        Cli.invalid();
                    }
                }
            } else if (ans.equals("No") || ans.equals("Back")) {
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
//                System.out.println("\n!!! Invalid !!!");
                Cli.invalid();
            }
        }
    }

    public static void deleteAccount(int id) {
        User user = User.getByID(id);


        File usersDirectory = new File("./src/main/resources/Info/Users");
        File tweetsDirectory = new File("./src/main/resources/Info/Tweets");
        File messagesDirectory = new File("./src/main/resources/Info/Messages");
        File groupsDirectory = new File("./src/main/resources/Info/Groups");


        for (File file :
                usersDirectory.listFiles()) {
                User user1 = User.getByID(ID.getIdFromFileName(file.getName()));
                if (user1.getBlackList().contains(id)) {
                    user1.getBlackList().remove((Object) id);
                    user1.saveIntoDB();
                    logger.info("user "+ id+ " deleted from "+user1.getUserName()+" BlackList");
                }
                if (user1.getFollowings().contains(id)) {
                    user1.getFollowings().remove((Object) id);  user1.saveIntoDB();
                    logger.info("user "+ id+ " deleted from "+user1.getUserName()+" Followings"); }
                if (user1.getFollowers().contains(id)) {
                    user1.getFollowers().remove((Object) id);  user1.saveIntoDB();
                    logger.info("user "+ id+ " deleted from "+user1.getUserName()+" Followers");
                   }

                for (int i = 0; i < user1.getGroups().size(); i++) {
                    Group group = Group.getByID(user1.getGroups().get(i));
                    if (group.getMembers().contains(id)) {
                        group.getMembers().remove((Object) id);
                        logger.info("user "+id+" deleted from group "+group.getId());
                        group.saveIntoDB();
                    }
                }
                for (int i = 0; i < user1.getSavedMessages().size(); i++) {
                    if (SavedMessages.isTweet(user1.getSavedMessages().get(i))) {
                        Tweet tweet = Tweet.getByID(user1.getSavedMessages().get(i));
                        if (tweet.getWriter() == id) {
                            user1.getSavedMessages().remove(i);
                            user1.saveIntoDB();
                            logger.info("tweet "+tweet.getId()+" deleted from "+user1.getUserName()+" savedMessages");
                            i--;
                        }
                    } else {
                        Message message = Message.getByID(user1.getSavedMessages().get(i));
                        if (message.getSenderId() == id) {
                            user1.getSavedMessages().remove(i);
                            user1.saveIntoDB();
                            logger.info("message "+message.getId()+" deleted from "+user1.getUserName()+" SavedMessages");
                            i--;
                        }
                    }
                }
                for (int i = 0; i < user1.getLikedTweets().size(); i++) {
                    Tweet tweet = Tweet.getByID(user1.getLikedTweets().get(i));
                    if (tweet.getWriter() == id) {
                        user1.getLikedTweets().remove(i);
                        user1.saveIntoDB();
                        logger.info("tweet "+tweet.getId()+" deleted from "+user1.getUserName()+" LikedTweets");
                        i--;
                    }
                }

                if(user1.getUnRead().contains(user.getUserName())){
                    user1.getUnRead().remove(user.getUserName()); user1.saveIntoDB();
                    logger.info("user "+id+" deleted from "+user1.getUserName()+" unReads");
                }

                if(user1.getUnReadUsernames().containsKey(user.getUserName())){
                    user1.getUnReadUsernames().remove(user.getUserName()); user1.saveIntoDB();
                    logger.info("user "+id+" deleted from "+user1.getUserName()+" unReadUserNames");
                }

                if(user1.getRequests().contains(user.getUserName())){
                    user.getRequests().remove(user.getUserName());user1.saveIntoDB();
                    logger.info("user "+id+" deleted from "+user1.getUserName()+" requests");

                }

                for (int i = 0; i < user1.getChats().size(); i++) {
                    Message message = Message.getByID(user1.getChats().get(i).get(0));
                    if (message.getSenderId() == id || message.getReceiverId() == id) {
                        user1.getChats().remove(i);
                        user1.saveIntoDB();
                        logger.info("a chat with user "+id+ " deleted from "+user1.getUserName()+" chats");
                        i--;
                    }
                }

                for(int i=0;i<user1.getTweets().size();i++){
                    Tweet tweet=Tweet.getByID(user1.getTweets().get(i));
                    if(tweet.getWriter()== id){
                        user1.getTweets().remove(i);
                        user1.saveIntoDB();
                        logger.info("a retweet of user "+id+" deleted from "+user1.getUserName()+" tweets");
                        i--;
                    }
                }



            for(int i=0;i<user1.getNotifications().size();i++){
                String followName = user.getUserName() + " started following you!";
                String unfollowName = user.getUserName() + " stopped following you!";
                if (user1.getNotifications().get(i).equals(followName) || user1.getNotifications().get(i).equals(unfollowName)) {
                    user1.getNotifications().remove(i);
                    user1.saveIntoDB();
                    logger.info("a notification of user " + id + " deleted from " + user1.getUserName() + " notifications");
                    i--;
                }
            }


        }

        for (Integer tweetId:
             user.getLikedTweets()) {
            Tweet tweet=Tweet.getByID(tweetId);
            tweet.addToLikeNums(-1);
            tweet.saveIntoDB();
        }

        for (File file :
                tweetsDirectory.listFiles()) {
            Tweet tweet = Tweet.getByID(ID.getIdFromFileName(file.getName()));
            if (tweet.getWriter() == id) {
                file.delete();
                logger.info("Tweet "+tweet.getId()+" deleted");
            }
        }

        for (File file :
                messagesDirectory.listFiles()) {
            Message message = Message.getByID(ID.getIdFromFileName(file.getName()));
            if (message.getSenderId() == id) {file.delete();
                logger.info("Message "+message.getId()+" deleted");
            }

        }


        for (File file :
                usersDirectory.listFiles()) {
            User thisUser = User.getByID(ID.getIdFromFileName(file.getName()));
            if (thisUser.getId() == id) {
                file.delete();
                logger.info("User "+thisUser.getId()+" deleted");

            }
        }
    }



}








package ir.pi.project.logic.messaging;

import ir.pi.project.*;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.Message;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

public class Messages {
    static private final Logger logger= LogManager.getLogger(Message.class);
    public static void messagesLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Messages");
        while (true){
            Cli.print("\n~ Messages ~", ConsoleColors.PURPLE_BRIGHT);
            Cli.print("\n✦ SavedMessages\n✦ Chats\n✦ GroupChat",ConsoleColors.PURPLE);
            User user=User.getByID(id);
            String ans= Cli.get();
            if(ans.equals("SavedMessages")){
                SavedMessages.showSavedMessages(id);
            }
            else if(ans.equals("Chats")){
                Chats.chatsLogic(id);
            }
            else if(ans.equals("GroupChat")){
                GroupChat.groupChatLogic(id);
            }
            else if(ans.equals("Back")){
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

    public static void forward(int userId,int tweetId){

        Cli.print("\nEnter username: ",ConsoleColors.WHITE_BOLD);
        User user=User.getByID(userId);
            String ans=Cli.get();
            if(ans.equals("Quit")){
                String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                        " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
                user.setLastSeen("LastSeen: "+date);
                user.setOnline(false);
                user.saveIntoDB();
                logger.info(user.getUserName()+" logged out");
                Welcome.welcomeLogic();
            }
            else if(!ans.equals("Back")){
                Tweet tweet = Tweet.getByID(tweetId);
                File directory=new File("./src/main/resources/Info/Users");
                for (   File file:
                     directory.listFiles()) {
                    User user1=User.getByID(ID.getIdFromFileName(file.getName()));
                    if(user1.getUserName().equals(ans)){
                        if(user1.isFollowing(userId) || user.isFollowing(user1.getId())){
                            User user2=User.getByID(tweet.getWriter());
                            String text="(Forwarded from: "+user2.getUserName()+") "+tweet.getText();
                            NewMessage.send(userId,user1.getId(),text);

                        }
                    }
                }


            }
        }
}

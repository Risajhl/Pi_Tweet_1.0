package ir.pi.project.logic.messaging;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.Message;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

public class SavedMessages {
    static private final Logger logger= LogManager.getLogger(SavedMessages.class);
    public static void showSavedMessages(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered SavedMessages");
        while (true) {
            Cli.print("\n~ SavedMessages ~",ConsoleColors.CYAN_BRIGHT);
            User user = User.getByID(id);
            for (int i = 0; i < user.getSavedMessages().size(); i++) {
                if (SavedMessages.isTweet(user.getSavedMessages().get(i))) {
                    Tweet tweet = Tweet.getByID(user.getSavedMessages().get(i));
                    User writer=User.getByID(tweet.getWriter());
                    Cli.print("\n(Tweet) ",ConsoleColors.CYAN);
                    Cli.print(writer.getUserName() + ": ",ConsoleColors.BLUE);
                    Cli.print(tweet.getText(),ConsoleColors.WHITE_BRIGHT);
                    String date=tweet.getTime().getYear() + " " + tweet.getTime().getMonth() + " " + tweet.getTime().getDayOfMonth() +
                            "  " + tweet.getTime().getHour() + ":" + tweet.getTime().getMinute();
                    Cli.print(date,ConsoleColors.YELLOW);
                } else {
                    Message message = Message.getByID(user.getSavedMessages().get(i));
                    User sender = User.getByID(message.getSenderId());
                    Cli.print("\n(message) ",ConsoleColors.BLUE);
                    Cli.print(sender.getUserName() + ": ",ConsoleColors.CYAN);
                    Cli.print(message.getText(),ConsoleColors.WHITE_BRIGHT);
                    String date=message.getTime().getYear() + " " + message.getTime().getMonth() + " " + message.getTime().getDayOfMonth() +
                            "  " + message.getTime().getHour() + ":" + message.getTime().getMinute();
                    Cli.print(date,ConsoleColors.YELLOW);
                }
                Cli.print("________________________________________",ConsoleColors.RED_BRIGHT);
            }
            Cli.print("\n✦ AddNewMessages\n✦ Back", ConsoleColors.WHITE_BRIGHT);
            String ans= Cli.get();
            if(ans.equals("AddNewMessages")){
                addNewMessage(id);
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

    public static void addToSavedMessages(int thing,int id){
        User user=User.getByID(id);
        user.getSavedMessages().add(thing);
        user.saveIntoDB();

    }
    public static void addNewMessage(int id){
        Cli.print("\nEnter message: ", ConsoleColors.WHITE_BRIGHT);
            String ans=Cli.get();
            Message message = new Message(id,id,ans);
            message.saveIntoDB();
            User user=User.getByID(id);
            user.getSavedMessages().add(message.getId());
            user.saveIntoDB();
    }


    public static boolean isTweet(int id){
        File directory=new File("./src/main/resources/Info/Tweets");
        for (File file:
             directory.listFiles()) {
            if(file.getName().equals(id+".json"))return true;
        }
        return false;
    }
}

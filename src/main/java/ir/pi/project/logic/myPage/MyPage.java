package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class MyPage {
    static private final Logger logger= LogManager.getLogger(MyPage.class);
    public static void myPageLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered MyPage");
        while (true) {
            User user=User.getByID(id);
            user.saveIntoDB();
            Cli.print("\n~ " + user.getUserName() + " ~", ConsoleColors.PURPLE_BRIGHT);
            Cli.print("\n✦ NewTweet\n✦ Tweets\n✦ Info\n✦ EditInfo\n✦ Lists\n✦ Notifications",ConsoleColors.PURPLE);
            String ans = Cli.get();
            if (ans.equals("NewTweet")) {
                NewTweet.newTweetLogic(id);

            } else if (ans.equals("Tweets")) {
                Tweets.tweetsLogic(id);
            } else if (ans.equals("Info")) {
                Info.infoLogic(id);
            } else if (ans.equals("EditInfo")) {
                EditInfo.editInfoLogic(id);
            } else if (ans.equals("Lists")) {
                Lists.listsLogic(id);
            } else if (ans.equals("Notifications")) {
                Notifications.notificationsLogic(id);
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
            else {
                Cli.invalid();
            }
        }
    }
}

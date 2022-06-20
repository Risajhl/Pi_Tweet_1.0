package ir.pi.project.logic.settings;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Settings {
    static private final Logger logger= LogManager.getLogger(Settings.class);
    public static void settingLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Settings");
        while (true) {
            Cli.print("\n~ Settings ~", ConsoleColors.YELLOW_BRIGHT);
            Cli.print("\n✦ Privacy\n✦ LogOut\n✦ DeleteAccount",ConsoleColors.YELLOW);
            User user=User.getByID(id);
            String ans = Cli.get();
            if (ans.equals("Privacy")) {
                Privacy.privacyLogic(id);
            } else if (ans.equals("LogOut")) {
                String date=LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                        " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
                user.setLastSeen("LastSeen: "+date);
                user.setOnline(false);
                user.saveIntoDB();
                logger.info(user.getUserName()+" logged out");
                Welcome.welcomeLogic();
            } else if (ans.equals("DeleteAccount")) {
                DeleteAccount.askForDeleteAccount(id);
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

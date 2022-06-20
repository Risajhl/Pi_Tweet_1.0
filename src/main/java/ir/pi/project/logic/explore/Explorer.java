package ir.pi.project.logic.explore;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Explorer {
    static private final Logger logger= LogManager.getLogger(Explorer.class);

    public static void explorerLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Explorer");
        while (true) {
            Cli.print("\n~ Explorer ~", ConsoleColors.CYAN_BRIGHT);
            Cli.print("\n✦ Search\n✦ World",ConsoleColors.CYAN);
            User user=User.getByID(id);
            String ans = Cli.get();
            if (ans.equals("Search")) {
                Search.searchLogic(id);
            } else if (ans.equals("World")) {
                World.worldLogic(id);
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

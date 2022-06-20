package ir.pi.project.logic;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.explore.Explorer;
import ir.pi.project.logic.messaging.Messages;
import ir.pi.project.logic.myPage.MyPage;
import ir.pi.project.logic.settings.Settings;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class MainMenu {
    static private final Logger logger= LogManager.getLogger(MainMenu.class);
    public static void mainMenuLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered MainMenu");

        while (true) {
            User user = User.getByID(id);
            user.saveIntoDB();
            Cli.print("\n~ MainMenu ~", ConsoleColors.CYAN_BRIGHT);
            Cli.print("\n✦ MyPage\n✦ TimeLine\n✦ Explorer\n✦ Messages\n✦ Settings", ConsoleColors.CYAN);
            Cli.print("\n<< You can use 'Exit' and 'Quit' in any parts of the app >>\n",ConsoleColors.WHITE_BOLD);
            String ans = Cli.get();
            if (ans.equals("MyPage")) {
                MyPage.myPageLogic(id);
            } else if (ans.equals("TimeLine")) {
                TimeLine.myTimeLineLogic(id);
            } else if (ans.equals("Explorer")) {
                Explorer.explorerLogic(id);
            } else if (ans.equals("Messages")) {
                Messages.messagesLogic(id);
            } else if (ans.equals("Settings")) {
                Settings.settingLogic(id);
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

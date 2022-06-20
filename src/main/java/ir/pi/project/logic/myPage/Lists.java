package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.lists.BlackList;
import ir.pi.project.logic.lists.Followers;
import ir.pi.project.logic.lists.Followings;
import ir.pi.project.logic.lists.Groups;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;


public class Lists {
    static private final Logger logger= LogManager.getLogger(Lists.class);
    public static void listsLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Lists");

        while (true){
            Cli.print("\n~ Lists ~", ConsoleColors.GREEN_BRIGHT);
            Cli.print("\n✦ Followers\n✦ Followings\n✦ BlackList\n✦ Groups",ConsoleColors.GREEN);
            User user=User.getByID(id);
            String ans=Cli.get();
            if(ans.equals("Followers")){
                Followers.followersLogic(id);
            }
            else if(ans.equals("Followings")){
                Followings.followingsLogic(id);
            }
            else if(ans.equals("BlackList")){
                BlackList.blackListLogic(id);
            }
            else if(ans.equals("Groups")){
                Groups.showGroupsLogic(id);
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
            else if(ans.equals("Exit")){
                System.exit(1);
            }
            else {
                Cli.invalid();
            }

        }
    }
}

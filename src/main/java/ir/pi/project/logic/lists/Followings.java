package ir.pi.project.logic.lists;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.ShowUser;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Followings {
    static private final Logger logger= LogManager.getLogger(Followings.class);
    public static void followingsLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Followings");
        while (true){
            Cli.print("\n~ Followings ~", ConsoleColors.BLUE_BRIGHT);
            User user=User.getByID(id);
            List<Integer> activeFollowings = new ArrayList<>();
            if(!user.getFollowings().isEmpty()) {

                for (int i = 0; i < user.getFollowings().size(); i++) {
                    User user1 = User.getByID(user.getFollowings().get(i));
                    if (user1.isActive()) activeFollowings.add(user1.getId());
                }
            }
            if(!activeFollowings.isEmpty()){
                for (int i = 0; i < activeFollowings.size(); i++) {
                    User user1=User.getByID(activeFollowings.get(i));
                    Cli.print("• "+user1.getUserName(),ConsoleColors.BLUE);
                }
                String ans=Cli.getter("\nType The UserName To See The Profile: ",ConsoleColors.WHITE_BOLD);
                for(int i=0;i<activeFollowings.size();i++){
                    User user1=User.getByID(activeFollowings.get(i));
                    if(user1.getUserName().equals(ans)){
                        ShowUser.showUserLogic(id, user1.getId());
                    }
                }
                if(ans.equals("Back")){
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

            else {
                Cli.print("\nNo Followings:(",ConsoleColors.RED);
                Cli.print("\nType Back to go to Lists",ConsoleColors.WHITE_BOLD);
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

}


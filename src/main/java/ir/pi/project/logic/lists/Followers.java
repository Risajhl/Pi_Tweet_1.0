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

public class Followers {
    static private final Logger logger= LogManager.getLogger(Followers.class);
    public static void followersLogic(int id){
        User thisUser=User.getByID(id);
       logger.info(thisUser.getUserName()+" entered Followers");
        while (true){
            Cli.print("\n~ Followers ~", ConsoleColors.BLUE_BRIGHT);
        User user=User.getByID(id);
            List<Integer> activeFollowers = new ArrayList<>();
        if(!user.getFollowers().isEmpty()) {

            for (int i = 0; i < user.getFollowers().size(); i++) {
                User user1 = User.getByID(user.getFollowers().get(i));
                if (user1.isActive()) activeFollowers.add(user1.getId());
            }
        }
            if(!activeFollowers.isEmpty()){
                for (int i = 0; i < activeFollowers.size(); i++) {
                    User user1=User.getByID(activeFollowers.get(i));
                    Cli.print("• "+user1.getUserName(),ConsoleColors.BLUE);
                }
                String ans=Cli.getter("\nType The UserName To See The Profile: ",ConsoleColors.WHITE_BOLD);
                for(int i=0;i<activeFollowers.size();i++){
                    User user1=User.getByID(activeFollowers.get(i));
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
                Cli.print("\nNo Followers:(",ConsoleColors.RED);
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

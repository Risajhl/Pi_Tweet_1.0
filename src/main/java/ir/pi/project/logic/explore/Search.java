package ir.pi.project.logic.explore;

import ir.pi.project.*;
import ir.pi.project.logic.ShowUser;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

public class Search {
    static private final Logger logger= LogManager.getLogger(Search.class);
    public static void searchLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Search");
        while (true) {
            Cli.print("\n~ Search ~", ConsoleColors.WHITE_BRIGHT);
            Cli.print("\nEnter userName:",ConsoleColors.WHITE_BOLD);
            User user=User.getByID(id);
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
            searchFor(id, ans);
        }




    }

    public static void searchFor(int id , String ans){

        File usersDirectory=new File("./src/main/resources/Info/Users");
        boolean exists=false;
        for (File file:
                usersDirectory.listFiles()) {
            User user1= User.getByID(ID.getIdFromFileName(file.getName()));
            if(user1.getUserName().equals(ans) && user1.isActive()){
                exists=true;
                if(user1.getId()==id){
                    ShowUser.showMe(id);}
                else {
                    ShowUser.showUserLogic(id, user1.getId());
                }
            }
        }
        if(!exists){
            Cli.print("\nUser can not be found!",ConsoleColors.RED_BOLD);
        }
    }
}

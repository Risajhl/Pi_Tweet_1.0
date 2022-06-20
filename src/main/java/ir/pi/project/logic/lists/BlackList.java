package ir.pi.project.logic.lists;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlackList {
    static private final Logger logger= LogManager.getLogger(BlackList.class);
    public static void blackListLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered BlackList");
        while (true) {
            Cli.print("\n~ BlackList ~", ConsoleColors.BLUE_BRIGHT);
            User user = User.getByID(id);
            List<Integer> activeBlackList=new ArrayList<>();
            if (!user.getBlackList().isEmpty()) {
                for (int i = 0; i < user.getBlackList().size(); i++) {
                    User user1 = User.getByID(user.getBlackList().get(i));
                    if (user1.isActive()) activeBlackList.add(user1.getId());
                }
            }
            if(!activeBlackList.isEmpty()){
                    for (int i = 0; i < activeBlackList.size(); i++) {
                        User user1=User.getByID(activeBlackList.get(i));
                        Cli.print("• "+user1.getUserName(),ConsoleColors.BLUE);

                    }
                String ans=Cli.getter("\nType The UserName To UnBlock: ",ConsoleColors.WHITE_BOLD);
                    for (int i = 0; i < activeBlackList.size(); i++) {
                        User user1 = User.getByID(activeBlackList.get(i));
                        if (user1.getUserName().equals(ans)) {
                            user.getBlackList().remove((Object)activeBlackList.get(i));
                            user.saveIntoDB();
                            Cli.print("\nYou UnBlocked " + user1.getUserName() + "!",ConsoleColors.GREEN_BRIGHT);
                        }
                    }
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

            } else {
                Cli.print("\nBlackList is empty!",ConsoleColors.RED);
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


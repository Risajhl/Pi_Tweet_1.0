package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Notifications {
    static private final Logger logger= LogManager.getLogger(Notifications.class);
    public static void notificationsLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Notifications");
        User user=User.getByID(id);
        while (true) {
            Cli.print("\n~ Notifications ~",ConsoleColors.RED_BRIGHT);
            Cli.print("\n✦ Requests\n✦ SystemNotifications",ConsoleColors.RED);
            String ans = Cli.get();
            if (ans.equals("Requests")) {
//                Notifications.requestsLogic(id);
                requestLists(id);
            } else if (ans.equals("SystemNotifications")) {
                Notifications.systemNotificationsLogic(id);
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

    public static void requestLists(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered requestsLists");
        while (true){
            User user=User.getByID(id);

            Cli.print("\n✦ MyRequests\n✦ Others",ConsoleColors.YELLOW_BRIGHT);
            String ans=Cli.get();
            if(ans.equals("MyRequests")){
                myRequests(id);
            }
            else if(ans.equals("Others")){
                requestsLogic(id);
            }
         else if (ans.equals("Back")) {
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

    public static void myRequests(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered SystemNotifications");
        while (true) {
            Cli.print("\n~ MyRequests ~",ConsoleColors.PURPLE_BRIGHT);

            User user=User.getByID(id);
            if(!user.getMyRequests().isEmpty()) {
                Cli.print("",ConsoleColors.RESET);
                for (int i = 0; i < user.getMyRequests().size(); i++) {
                    Cli.print(user.getMyRequests().get(i),ConsoleColors.PURPLE);
                }
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
                else if(ans.equals("Exit")){
                    System.exit(1);
                }
                else {
                    Cli.invalid();
                }


            }
            Cli.print("\nEmpty!",ConsoleColors.RED);
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


    public static void systemNotificationsLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered SystemNotifications");
        while (true) {
            Cli.print("\n~ SystemNotifications ~ ",ConsoleColors.YELLOW_BRIGHT);
            User user = User.getByID(id);
            System.out.println();
            if (!user.getNotifications().isEmpty()) {
                    for (int i = user.getNotifications().size() - 1; i >= 0; i--) {
                        Cli.print("• "+user.getNotifications().get(i),ConsoleColors.YELLOW);
                    }
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
                    else if(ans.equals("Exit")){
                        System.exit(1);
                    }
                    else {
                        Cli.invalid();
                    }

            } else {
                Cli.print("\nNo Notifications!",ConsoleColors.RED);
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


    public static void requestsLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Requests");
        User user = User.getByID(id);
        int i=user.getRequests().size()-1;
        while (true) {
            Cli.print("\n~ Requests ~", ConsoleColors.CYAN_BRIGHT);
            if (!user.getRequests().isEmpty()) {
                    showUser(user.getRequests().get(i));
                    String ans = Cli.get();
                    if (ans.equals("Accept")) {
                        User user1 = User.getByID(user.getRequests().get(i));
                        user.getFollowers().add(user.getRequests().get(i));
                        user.getNotifications().add(user1.getUserName() + " started following you!");
                        user1.getMyRequests().add(user.getUserName()+" accepted your follow request!");
                        user.getRequests().remove(i);
                        user1.getFollowings().add(id);
                        Cli.print("\nYou accepted " + user1.getUserName(),ConsoleColors.GREEN_BRIGHT);
                        user.saveIntoDB();
                        user1.saveIntoDB();
                    }
                    else if (ans.equals("DeleteA")) {
                        User user1 = User.getByID(user.getRequests().get(i));
                        user1.getNotifications().add(user.getUserName() + " didn't accept your request!");
                        user1.getMyRequests().add(user.getUserName()+" did not accept your follow request!");
                        user.getRequests().remove(i);
                        user.saveIntoDB();
                        user1.saveIntoDB();
                    }
                    else if (ans.equals("DeleteB")) {
                        User user1 = User.getByID(user.getRequests().get(i));
                        user.getRequests().remove(i);
                        user.saveIntoDB();
                        user1.saveIntoDB();

                    }
                    else if (ans.equals("<") && i != user.getTweets().size() - 1) {
                        i++;

                    }
                    else if (ans.equals(">") && i != 0) {
                        i--;
                    }
                    else if (ans.equals("Back")) {
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
                Cli.print("\nNo Request!",ConsoleColors.RED);
                    String ans = Cli.get();

                   if(ans.equals("Quit")){
                        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
                        user.setLastSeen("LastSeen: "+date);
                        user.setOnline(false);
                        user.saveIntoDB();
                       logger.info(user.getUserName()+" logged out");
                        Welcome.welcomeLogic();
                    }
                    else if (ans.equals("Back")) {
                    break;

                    }
                    else {
                       Cli.invalid();
                    }
                }

            }
        }



    public static void showUser(int id){
        User user=User.getByID(id);
        Cli.print("\n"+user.getUserName()+" wants to follow you!",ConsoleColors.CYAN);
        Cli.print("\n✦ Accept\n✦ DeleteA (will be informed)\n✦ DeleteB (won't be informed)",ConsoleColors.WHITE_BRIGHT);
        Cli.print("\n       <        >       ",ConsoleColors.CYAN_BRIGHT);
    }

}

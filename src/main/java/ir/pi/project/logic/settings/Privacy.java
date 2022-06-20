package ir.pi.project.logic.settings;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Privacy {
    static private final Logger logger= LogManager.getLogger(Privacy.class);
    public static void privacyLogic(int id){
        User thisUser=User.getByID(id);
        logger.info (thisUser.getUserName()+" entered Privacy");
        while (true) {
            User user=User.getByID(id);
            Cli.print("\n~ Privacy ~", ConsoleColors.WHITE_BRIGHT);
            Cli.print("\n✦ AccountPrivacy\n✦ LastSeenSetting\n✦ Activity\n✦ ChangePassword",ConsoleColors.WHITE_BOLD);
            String ans = Cli.get();
            if (ans.equals("AccountPrivacy")) {
                changePrivacy(id);
            } else if (ans.equals("LastSeenSetting")) {
                lastSeenSetting(id);
            } else if (ans.equals("Activity")) {
                changeActivity(id);
            } else if (ans.equals("ChangePassword")) {
                changePassword(id);
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
            else if (ans.equals("Back")) {
                break;
            } else {
                Cli.invalid();
            }
        }
    }

    public static void lastSeenSetting(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered LastSeenSetting");
        while (true){
            Cli.print("\n~ LastSeenSetting ~",ConsoleColors.CYAN_BRIGHT);
            User user=User.getByID(id);
            String state=user.getLastSeenState();
            if(state.equals("EveryOne")){
                Cli.print("\nEveryOne can see",ConsoleColors.CYAN);

            }
            else if(state.equals("NoOne")){
                Cli.print("\nNoOne can see",ConsoleColors.CYAN);

            }
            else if(state.equals("Followers")){
                Cli.print("\nOnly your Followers can see",ConsoleColors.CYAN);

            }
            String ans=Cli.getter("\nEnter who you want to see: ",ConsoleColors.WHITE_BOLD);
            if(ans.equals("EveryOne")){
                user.setLastSeenState("EveryOne");
                user.saveIntoDB();
                logger.info(user.getUserName()+" changed lastSeen to EveryOne");
                Cli.print("\nChanges updated!",ConsoleColors.GREEN_BRIGHT);
            }
            else if(ans.equals("NoOne")){
                user.setLastSeenState("NoOne");
                user.saveIntoDB();
                logger.info(user.getUserName()+" changed lastSeen to NoOne");
                Cli.print("\nChanges updated!",ConsoleColors.GREEN_BRIGHT);

            }
            else if(ans.equals("Followers")){
                user.setLastSeenState("Followers");
                user.saveIntoDB();
                logger.info(user.getUserName()+" changed lastSeen to Followers");
                Cli.print("\nChanges updated!",ConsoleColors.GREEN_BRIGHT);

            }
            else if(ans.equals("Back")){
                break;
            }
            else {
                Cli.invalid();
            }
        }
    }

    public static void changePrivacy(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered ChangePrivacy");
        while (true) {
            Cli.print("\n~ ChangePrivacy ~",ConsoleColors.RED_BRIGHT);
            User user = User.getByID(id);
            if (user.isPublic()) {
                Cli.print("\nYour account is Public, type Private to make it private",ConsoleColors.RED);

                String ans = Cli.get();
                if (ans.equals("Private")) {
                    user.setPublic(false);
                    user.saveIntoDB();
                    logger.info(user.getUserName()+" changed Privacy to Private");
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
            } else {
                Cli.print("\nYour account is Private, type Public to make it public",ConsoleColors.RED);
                String ans = Cli.get();
                if (ans.equals("Public")) {
                    user.setPublic(true);
                    user.saveIntoDB();
                    logger.info(user.getUserName()+" changed Privacy to Public");

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

    public static void changeActivity(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered ChangeActivity");
        while (true) {
            Cli.print("\n~ ChangeActivity ~",ConsoleColors.BLUE_BRIGHT);
            User user = User.getByID(id);
            if (user.isActive()) {
                Cli.print("\nYour account is active, type Inactive to inactive",ConsoleColors.BLUE);
                String ans = Cli.get();
                if (ans.equals("Inactive")) {
                    user.setActive(false);
                    user.saveIntoDB();
                    logger.info(user.getUserName()+" changed Activity to Inactive");

                } else if (ans.equals("Back")) {
                    break;
                } else {
                    System.out.println("\n!!! Invalid !!!");
                }
            } else {
                Cli.print("\nYour account is Inactive, type Active to active",ConsoleColors.BLUE);
                String ans = Cli.get();
                if (ans.equals("Active")) {
                    user.setActive(true);
                    user.saveIntoDB();
                    logger.info(user.getUserName()+" changed Activity to Active");

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


    public static void changePassword(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered ChangePassword");
        while (true) {
            User user = User.getByID(id);
            String ans =Cli.getter("\nEnter your current password: ",ConsoleColors.WHITE_BOLD);
            if (ans.equals("Back")) {
                break;
            } else if (ans.equals(user.getPassword())) {
                String ans1=Cli.getter("Enter new password: ",ConsoleColors.WHITE_BOLD);
                user.setPassword(ans1);
                user.saveIntoDB();
                logger.info(user.getUserName()+ " changed password");
                Cli.print("\nYour password has been successfully changed!",ConsoleColors.GREEN_BRIGHT);
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
                Cli.print("\n!!! Wrong password !!!",ConsoleColors.RED_BOLD);

            }
        }


    }

}

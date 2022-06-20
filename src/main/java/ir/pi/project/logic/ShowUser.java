package ir.pi.project.logic;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.logic.messaging.NewMessage;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class ShowUser {
    static private final Logger logger= LogManager.getLogger(ShowUser.class);
    public static void showUserLogic(int userId,int user1Id){
        User thisUser=User.getByID(userId);
        User otherUser=User.getByID(user1Id);
        logger.info(thisUser.getUserName()+" entered "+otherUser.getUserName()+"'s profile");
        while (true) {
            User user = User.getByID(userId);
            User user1 = User.getByID(user1Id);

            Cli.print("\n"+user1.getFirstName() + " " + user1.getLastName(), ConsoleColors.RED_BRIGHT);
            Cli.print(user1.getUserName(),ConsoleColors.YELLOW_BRIGHT);

            if(user1.isEPBCanSee()){
                Cli.print("Email: "+user1.getEmail(),ConsoleColors.YELLOW);
                Cli.print("PhoneNumber: "+user1.getPhoneNumber(),ConsoleColors.YELLOW);
                Cli.print("BirthDate: "+user1.getBirthDate(),ConsoleColors.YELLOW);
            }

            if(user1.getBiography()!=null){
                Cli.print("{"+user1.getBiography()+"}",ConsoleColors.YELLOW_BRIGHT);
            }

            if (user.isBlockedBy(user1Id)) {
                Cli.print("\nYou are blocked by this user!",ConsoleColors.RED);
                Cli.print("\nType Back to go back",ConsoleColors.WHITE_BOLD);

                String ans = Cli.get();
                if (ans.equals("Back")) {
                    MainMenu.mainMenuLogic(userId);
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
            else if (user1.isBlockedBy(userId)) {
                Cli.print("\nThis user is blocked by you!",ConsoleColors.RED);
                Cli.print("✦ UnBlock ",ConsoleColors.WHITE_BRIGHT);
                Cli.print("\nType Back to go back",ConsoleColors.WHITE_BOLD);
                String ans = Cli.get();
                if (ans.equals("UnBlock")) {
                    user.unBlock(user1Id);
                } else if (ans.equals("Back")) {
                    MainMenu.mainMenuLogic(userId);
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

            else if (user.isFollowing(user1Id)) {
                if(user1.getLastSeenState().equals("EveryOne")||user1.getLastSeenState().equals("Followers")){Cli.print(user1.getLastSeen(),ConsoleColors.WHITE_BRIGHT);
                }
                Cli.print("'following'",ConsoleColors.RED);
                Cli.print("✦ UnFollow    ✦ Block      ✦ Report\n✦ Tweets      ✦ Message   ✦ Mute ",ConsoleColors.WHITE_BRIGHT);
                String ans = Cli.get();
                if (ans.equals("Tweets")) {
                    TimeLine.showTweets(user1.getTweets(), userId);
                } else if (ans.equals("UnFollow")) {
                    user.unFollow(user1Id);
                } else if (ans.equals("Block")) {
                    user.block(user1Id);
                    Cli.print("You Blocked this user!",ConsoleColors.RED_BOLD);
                } else if (ans.equals("Back")) {
                    MainMenu.mainMenuLogic(userId);

                }
                else if(ans.equals("Message")){
                    NewMessage.newMessageLogic(userId,user1Id);
                }
                else if(ans.equals("Mute")){
                    user.mute(user1Id);
                    Cli.print("\nYou muted "+user1.getUserName(),ConsoleColors.RED_BOLD);
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

            else if (user1.isPublic()) {
                if (!user.isFollowing(user1Id)) {
                    if(user1.getLastSeenState().equals("EveryOne"))Cli.print(user1.getLastSeen(),ConsoleColors.WHITE_BRIGHT);
                    Cli.print("✦ Follow      ✦ Block      ✦ Report\n✦ Tweets      ✦ Message     ✦ Mute",ConsoleColors.WHITE_BRIGHT);
                    String ans = Cli.get();
                    if (ans.equals("Follow")) {
                        user.follow(user1Id);
                        Cli.print("\nYou started following "+ user1.getUserName(),ConsoleColors.GREEN_BRIGHT);
                    } else if (ans.equals("Block")) {
                        user.block(user1Id);
                        Cli.print("You Blocked this user!",ConsoleColors.RED_BOLD);

                    } else if (ans.equals("Report")) {
                        Cli.print("\nYou reported this user! we will try to figure out the problem!",ConsoleColors.WHITE_BRIGHT);
                    }
                    else if(ans.equals("Message")){
                        NewMessage.newMessageLogic(userId,user1Id);

                    }
                    else if(ans.equals("Mute")){
                        user.mute(user1Id);
                        Cli.print("\nYou muted "+user1.getUserName(),ConsoleColors.RED_BOLD);

                    }

                    else if (ans.equals("Back")) {
                        MainMenu.mainMenuLogic(userId);
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
                    else if(ans.equals("Tweets")){

                        TimeLine.showTweets(user1.getTweets(), userId);
                    }
                    else {
                        Cli.invalid();
                    }


                }


            }


            else if (!user1.isPublic()) {

                if (user.hasRequestedToFollow(user1Id)) {

                    Cli.print("'requested'",ConsoleColors.RED);
                    Cli.print("✦ DeleteRequest      ✦ Report\n✦ Block             ✦ Mute     ",ConsoleColors.WHITE_BRIGHT);

                    String ans = Cli.get();
                    if (ans.equals("Block")) {
                        Cli.print("You Blocked this user!",ConsoleColors.RED_BOLD);
                        user.block(user1Id);
                    } else if (ans.equals("Report")) {
                        Cli.print("\nYou reported this user! we will try to figure out the problem!",ConsoleColors.WHITE_BRIGHT);
                    }
                    else if(ans.equals("Mute")){
                        user.mute(user1Id);
                        Cli.print("\nYou muted "+user1.getUserName(),ConsoleColors.RED_BOLD);
                    }
                    else if(ans.equals("DeleteRequest")){
                        user1.getRequests().remove((Object)user.getId());
                        user1.saveIntoDB();
                    }
                    else if (ans.equals("Back")) {
                        MainMenu.mainMenuLogic(userId);
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

                else if (!user.isFollowing(user1Id)) {

                    if(user1.getLastSeenState().equals("EveryOne"))Cli.print(user1.getLastSeen(),ConsoleColors.WHITE_BRIGHT);
                    Cli.print("'Private'",ConsoleColors.RED);
                    Cli.print("✦ Follow      ✦ Report\n✦ Block       ✦ Mute     ",ConsoleColors.WHITE_BRIGHT);
                    String ans = Cli.get();
                    if (ans.equals("Follow")) {
                        user.follow(user1Id);
                    } else if (ans.equals("Block")) {
                        Cli.print("You Blocked this user!",ConsoleColors.RED_BOLD);
                        user.block(user1Id);
                    } else if (ans.equals("Mute")) {
                        Cli.print("\nYou muted "+user1.getUserName(),ConsoleColors.RED_BOLD);
                        user.mute(user1Id);
                    } else if (ans.equals("Report")) {
                        Cli.print("\nYou reported this user! we will try to figure out the problem!",ConsoleColors.WHITE_BRIGHT);
                    } else if (ans.equals("Back")) {
                        MainMenu.mainMenuLogic(userId);
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

    public static void showMe(int id){
        Cli.print("\nFor checking your profile go to MyPage -> Info",ConsoleColors.YELLOW_BRIGHT);
    }
}

package ir.pi.project.logic.messaging;

import ir.pi.project.*;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class GroupChat {
    static private final Logger logger= LogManager.getLogger(GroupChat.class);
    public static void groupChatLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered GroupChat");
        while (true){
            Cli.print("\n✦ ToUsers\n✦ ToGroups", ConsoleColors.PURPLE_BRIGHT);
            User user=User.getByID(id);
            String ans= Cli.get();
            if(ans.equals("ToUsers")){
                GroupChat.toUsers(id);
            }
            else if(ans.equals("ToGroups")){
                GroupChat.toGroups(id);
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


    public static void toUsers(int id){
        while (true){
            User user=User.getByID(id);
            String ans=Cli.getter("\nEnter text: ",ConsoleColors.WHITE_BRIGHT);
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
                sendToUsers(id,ans);

            }
        }
    }

    public static void sendToUsers(int id,String text){
        List<Integer> receivers=new ArrayList<>();
        boolean wannaSend=false;
        while (true) {
            String ans=Cli.getter("\nEnter username: (Enter Back to cancel/Enter Send to send)",ConsoleColors.WHITE_BRIGHT);
            User user = User.getByID(id);
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
            else if (ans.equals("Send")) {
                wannaSend = true;
                break;
            }
            else {
                File directory = new File("./src/main/resources/Info/Users");
                boolean exists = false;
                for (File file :
                        directory.listFiles()) {
                    User user1 = User.getByID(ID.getIdFromFileName(file.getName()));
                    if (user1.getUserName().equals(ans)) {
                        exists = true;
                        if (user1.isFollowing(id) || user.isFollowing(user1.getId())) {
                            receivers.add(user1.getId());

                        } else {
                            Cli.print("\nAt least one of you need to follow anotherOne to send message!", ConsoleColors.RED);
                        }
                    }
                }

                if (!exists) {
                    Cli.print("\nUser can't be found!",ConsoleColors.RED_BOLD);
                }
            }
        }

        if(wannaSend){
            if(!receivers.isEmpty()) {
                for (int i = 0; i < receivers.size(); i++) {
                    NewMessage.send(id, receivers.get(i), text);
                }
                Cli.print("\nMessage has been sent!",ConsoleColors.GREEN_BRIGHT);
            }else {
                Cli.print("\nYou did not choose anyone!",ConsoleColors.RED_BOLD);
            }
        }

    }


    public static void toGroups(int id){
        while (true){
            String ans=Cli.getter("\nEnter text: ",ConsoleColors.WHITE_BRIGHT);
            User user=User.getByID(id);
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
                sendToGroups(id,ans);

            }
        }
    }

    public static void sendToGroups(int id,String text){
        List<Integer> receivers=new ArrayList<>();
        boolean wannaSend=false;
        while (true){
            String ans=Cli.getter("\nEnter groupName: (Enter Back to cancel/Enter Send to send)",ConsoleColors.WHITE_BRIGHT);
            User user = User.getByID(id);
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
            else if (ans.equals("Send")) {
                wannaSend = true;
                break;
            }
            else {
                File directory=new File("./src/main/resources/Info/Groups");
                boolean exists = false;
                for (File file:
                     directory.listFiles()) {
                    Group group=Group.getByID(ID.getIdFromFileName(file.getName()));
                    if(group.getName().equals(ans) && group.getOwner()==id){
                        exists = true;

                            if (!group.getMembers().isEmpty()) {
                                for(int i=0;i<group.getMembers().size();i++){
                                    User member=User.getByID(group.getMembers().get(i));
                                    if(member.isActive()){
                                        receivers.add(group.getMembers().get(i));
                                    }
                                    else {
                                        Cli.print("\nUser "+member.getUserName()+" in group "+group.getName()+" is not active!",ConsoleColors.RED);
                                    }
                                }
                            } else {
                                Cli.print("\nThis group is empty!",ConsoleColors.RED);
                            }

                    }

                }
                if(!exists){
                    Cli.print("\nThis group can't be found!",ConsoleColors.RED_BOLD);
                }


            }
        }


        if(wannaSend){
            Set<Integer> realReceivers=new HashSet<>();
            realReceivers.addAll(receivers);
            if(!receivers.isEmpty()) {
                for (Integer receiverId:
                     realReceivers) {
                    NewMessage.send(id, receiverId, text);
                }


                Cli.print("\nMessage has been sent!",ConsoleColors.GREEN_BRIGHT);
            }else {
                Cli.print("\nYou did not choose anyone!",ConsoleColors.RED_BOLD);
            }
        }
    }
}

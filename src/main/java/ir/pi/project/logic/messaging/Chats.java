package ir.pi.project.logic.messaging;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.ShowUser;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.Message;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chats {

    static private final Logger logger= LogManager.getLogger(Chats.class);
    public static void chatsLogic(int id){

        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Chats");
        while (true) {
            Cli.print("\n~ Chats ~\n", ConsoleColors.CYAN_BRIGHT);
            User user = User.getByID(id);
            boolean usernameExists=false;
            if(!user.getUnReadUsernames().isEmpty()){
                Cli.print("\nUnRead chats:",ConsoleColors.BLUE_BRIGHT);
                for (HashMap.Entry<String,Integer> entry : user.getUnReadUsernames().entrySet()) {
                    Cli.print("[ " + entry.getKey() +
                            " ] ~ ( " + entry.getValue()+" )",ConsoleColors.BLUE);

                    user.getUnRead().add(entry.getKey());
                    user.saveIntoDB();
                }
                Cli.print("................................",ConsoleColors.WHITE_BRIGHT);

            }


            if (!user.getChats().isEmpty()) {
                for (List<Integer> list :
                        user.getChats()) {
                    Message message = Message.getByID(list.get(0));


                    User user1 = User.getByID(message.getSenderId());
                    User user2 = User.getByID(message.getReceiverId());
                    if (user1.isActive() && user2.isActive()) {
                        if (user2.getId() == id && !isInList(user.getUnRead(), user1.getUserName()))
                            Cli.print("[ " + user1.getUserName() + " ]", ConsoleColors.CYAN);
                        if (user1.getId() == id && !isInList(user.getUnRead(), user2.getUserName()))
                            Cli.print("[ " + user2.getUserName() + " ]", ConsoleColors.CYAN);

                    }
                }

                String ans=Cli.getter("\n✦ Enter username to see the profile\n✦ Enter Open_username to open chats",ConsoleColors.WHITE_BRIGHT);
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
                else if(ans.length()>5 && ans.substring(0,5).equals("Open_")){

                    for (List<Integer> list :
                            user.getChats()){
                        Message message=Message.getByID(list.get(0));
                        User user1=User.getByID(message.getSenderId());
                        User user2=User.getByID(message.getReceiverId());
                        if(user1.getUserName().equals(ans.substring(5)) && user1.getId()!=id || user2.getUserName().equals(ans.substring(5)) && user2.getId()!=id){
                            if(user1.isActive() && user2.isActive()) {
                                usernameExists = true;

                                if (message.getReceiverId() == id) {
                                    showChatLogic(list, id, message.getSenderId());
                                    user=User.getByID(id);
                                    user.saveIntoDB();
                                    User user3 = User.getByID(message.getSenderId());
                                    if (isInList(user.getUnRead(), user3.getUserName())) {
                                        removeFromUnread(user.getUnRead(), user3.getUserName(), user.getUnReadUsernames());

                                        user.saveIntoDB();
                                    }
                                } else if (message.getSenderId() == id) {
                                    showChatLogic(list, id, message.getReceiverId());
                                    user=User.getByID(id);
                                    user.saveIntoDB();
                                    User user3 = User.getByID(message.getReceiverId());
                                    if (isInList(user.getUnRead(), user3.getUserName())) {
                                        removeFromUnread(user.getUnRead(), user3.getUserName(), user.getUnReadUsernames());
                                        user.saveIntoDB();
                                    }
                                }
                            }


                        }
                    }
                }
                else {
                    for (int i = 0; i < user.getChats().size(); i++) {
                        Message message = Message.getByID(user.getChats().get(i).get(0));
                        User user1 = User.getByID(message.getSenderId());
                        User user2 = User.getByID(message.getReceiverId());
                        if (user2.getId() != id && user2.getUserName().equals(ans)){
                            usernameExists=true;
                            ShowUser.showUserLogic(id, user2.getId());}
                        else if (user1.getId() != id && user1.getUserName().equals(ans)){
                            usernameExists=true;
                            ShowUser.showUserLogic(id, user1.getId());
                    }

                    }

                }

                if(!usernameExists){
                    Cli.print("\nNo chats with this user!",ConsoleColors.RED);
                }

            } else {
                Cli.print("\nNo chats!",ConsoleColors.RED);

                String ans=Cli.getter("\nType Back to go to Messages",ConsoleColors.WHITE_BOLD);

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
        }
    }


    public static void showChatLogic(List<Integer> chat,int userId,int user1Id) {
        if(!chat.isEmpty()) {
            while (true) {
                List<Integer> chatt = new ArrayList<>();
                User user = User.getByID(userId);
                for (List<Integer> list :
                        user.getChats()) {
                    Message message = Message.getByID(list.get(0));
                    if (message.getSenderId() == user1Id || message.getReceiverId() == user1Id) {
                        for (int i = 0; i < list.size(); i++) {
                            chatt.add(list.get(i));
                        }
                    }
                }
                Message firstMessage = Message.getByID(chatt.get(0));
                System.out.println();
                for (int i = 0; i < chatt.size(); i++) {
                    Message message = Message.getByID(chatt.get(i));
                    User sender = User.getByID(message.getSenderId());
                    String time = message.getTime().getYear() + " " + message.getTime().getMonth() + " " + message.getTime().getDayOfMonth() +
                            "  " + message.getTime().getHour() + ":" + message.getTime().getMinute();
                    Cli.print("< " + i + " > [" + time + "] " + sender.getUserName() + ": " + message.getText(), ConsoleColors.WHITE_BRIGHT);
                }
                Cli.print("\n✦ NewMessage\n✦ Back\n✦ SaveMessage", ConsoleColors.CYAN_BRIGHT);
                String ans = Cli.get();
                if (ans.equals("NewMessage")) {
                    if (userId == firstMessage.getReceiverId()) {
                        NewMessage.newMessageLogic(firstMessage.getReceiverId(), firstMessage.getSenderId());
                        user=User.getByID(userId);
                        user.saveIntoDB();

                    } else if (userId == firstMessage.getSenderId()) {
                        NewMessage.newMessageLogic(firstMessage.getSenderId(), firstMessage.getReceiverId());
                        user=User.getByID(userId);
                        user.saveIntoDB();

                    }

                } else if (ans.equals("SaveMessage")) {
                    String number = Cli.getter("\nEnterNumberOfMessage", ConsoleColors.WHITE_BOLD);
                    int q = Integer.parseInt(number);
                    user.getSavedMessages().add(chatt.get(q));
                    user.saveIntoDB();

                    Cli.print("\nMessage is saved!", ConsoleColors.GREEN_BRIGHT);

                } else if (ans.equals("Back")) {
                    break;
                } else if (ans.equals("Quit")) {
                    String date = LocalDateTime.now().getYear() + " " + LocalDateTime.now().getMonth() + " " + LocalDateTime.now().getDayOfMonth() +
                            " - " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
                    user.setLastSeen("LastSeen: " + date);
                    user.setOnline(false);
                    user.saveIntoDB();
                    logger.info(user.getUserName()+" logged out");
                    Welcome.welcomeLogic();
                } else {
                    Cli.invalid();
                }
            }
        }
        else {
            Cli.print("No Messages here!",ConsoleColors.RED_BOLD);
        }
    }


    public static boolean isInList(List<String> list, String userName){
        for (int i=0;i<list.size();i++){
            if(list.get(i).equals(userName))return true;
        }
        return false;
    }

    public static void removeFromUnread(List<String> list, String userName, Map<String, Integer> unRead){
        for (int i=0;i<list.size();i++){
            if(list.get(i).equals(userName)){
                list.remove(i);
                unRead.remove(userName);
            }
        }
    }



}

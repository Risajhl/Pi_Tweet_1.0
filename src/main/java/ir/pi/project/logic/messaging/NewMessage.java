package ir.pi.project.logic.messaging;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.model.Message;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewMessage {
    static private final Logger logger= LogManager.getLogger(NewMessage.class);
    public static void newMessageLogic(int userId, int user1Id) {
        User user = User.getByID(userId);//sender
        User user1 = User.getByID(user1Id);//receiver
        if (user.isFollowing(user1Id) || user1.isFollowing(userId)) {
            if (user.isBlockedBy(user1Id)) {
                Cli.print("\n" + user1.getUserName() + " has blocked you:(", ConsoleColors.RED);
            } else if (user1.isBlockedBy(userId)) {
                Cli.print("\nYou have blocked " + user1.getUserName() + " !", ConsoleColors.RED);

            } else if (!user1.isActive()) {
                Cli.print("\nThis user is not active!", ConsoleColors.RED);


            } else {

                String ans = Cli.getter("\nEnter your message:", ConsoleColors.WHITE_BOLD);
                NewMessage.send(userId,user1Id,ans);
            }
        }
    }

    public static void send(int userId, int user1Id, String ans) {
        User user = User.getByID(userId);
        User user1 = User.getByID(user1Id);
        if (user.isFollowing(user1Id) || user1.isFollowing(userId)) {
            boolean b = false;
            for (Map.Entry<String, Integer> entry : user1.getUnReadUsernames().entrySet()) {
                if (entry.getKey().equals(user.getUserName())) b = true;
            }
            if (!b) user1.getUnReadUsernames().put(user.getUserName(), 0);

            Message message = new Message(userId, user1Id, ans);
            message.saveIntoDB();
            boolean chatExists = false;
            for (List<Integer> chat :
                    user.getChats()) {
                Message message1 = Message.getByID(chat.get(0));
                if ((message1.getReceiverId() == user1Id && message1.getSenderId() == userId) || (message1.getSenderId() == user1Id && message1.getReceiverId() == userId)) {
                    chatExists = true;
                }
            }
            if (chatExists) {
                for (List<Integer> chat :
                        user.getChats()) {
                    Message message1 = Message.getByID(chat.get(0));
                    if ((message1.getReceiverId() == user1Id && message1.getSenderId() == userId) || (message1.getSenderId() == user1Id && message1.getReceiverId() == userId)) {
                        chat.add(message.getId());
                    }


                    user1.getUnReadUsernames().put(user.getUserName(), user1.getUnReadUsernames().get(user.getUserName()) + 1);
                }
                for (List<Integer> chat :
                        user1.getChats()) {
                    Message message1 = Message.getByID(chat.get(0));
                    if ((message1.getSenderId() == user1Id && message1.getReceiverId() == userId) || (message1.getReceiverId() == user1Id && message1.getSenderId() == userId)) {
                        chat.add(message.getId());
                    }
                }
            } else {
                List<Integer> newChat = new ArrayList<>();
                newChat.add(message.getId());
                user.getChats().add(newChat);
                user1.getChats().add(newChat);
                user1.getUnReadUsernames().put(user.getUserName(), user1.getUnReadUsernames().get(user.getUserName()) + 1);
            }

            Cli.print("\nMessage sent!",ConsoleColors.GREEN_BRIGHT);
            message.saveIntoDB();
            user.saveIntoDB();
            user1.saveIntoDB();
            logger.info(user.getUserName()+" sent message to "+ user1.getUserName());

        } else {
            Cli.print("\nAt least one of you need to follow the other one to send message", ConsoleColors.RED_BOLD);
        }
    }
}
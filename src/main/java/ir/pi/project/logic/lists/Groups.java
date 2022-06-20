package ir.pi.project.logic.lists;

import ir.pi.project.*;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

public class Groups {
    static private final Logger logger= LogManager.getLogger(Groups.class);
    public static void showGroupsLogic(int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Groups");
        while (true) {
            Cli.print("\n~ Groups ~", ConsoleColors.YELLOW_BRIGHT);
            User user = User.getByID(id);
            if(!user.getGroups().isEmpty()) {

                for (int i = 0; i < user.getGroups().size(); i++) {
                    Group group = Group.getByID(user.getGroups().get(i));
                    Cli.print("[ " + group.getName() + " ]",ConsoleColors.YELLOW);
                }
                Cli.print("\n✦ EditGroups\n✦ NewGroup",ConsoleColors.YELLOW_BOLD_BRIGHT);
                String ans=Cli.get();
                if(ans.equals("EditGroups")){

                    editGroupLogic(id);
                }
                else if(ans.equals("NewGroup")){
                    newGroupLogic(id);
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
                else {
                    Cli.invalid();
                }


            }else {
                Cli.print("\nNo groups!",ConsoleColors.RED_BOLD);
                Cli.print("\n✦ NewGroup",ConsoleColors.YELLOW_BOLD_BRIGHT);
                String ans= Cli.get();
                if(ans.equals("NewGroup")){
                    newGroupLogic(id);
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
                else {
                    Cli.invalid();
                }
            }
        }
    }

    public static void newGroupLogic(int id){
        Cli.print("\nEnter group name: ",ConsoleColors.WHITE_BRIGHT);
        String ans=Cli.get();
        Group group=new Group(ans,id);
        Cli.print("\nNew group has been created!",ConsoleColors.GREEN_BRIGHT);
        User user=User.getByID(id);
        user.getGroups().add(group.getId());
        group.saveIntoDB();
        user.saveIntoDB();
    }

    public static void editGroupLogic(int id) {
        while (true) {
            Cli.print("\nEnter group name: ",ConsoleColors.WHITE_BRIGHT);
            String ans = Cli.get();
            User user=User.getByID(id);
            if (ans.equals("Back")) {
                break;
            } else {
                File directory = new File("./src/main/resources/Info/Groups");
                boolean exists = false;
                for (File file : directory.listFiles()) {
                    Group group = Group.getByID(ID.getIdFromFileName(file.getName()));
                    if (group.getName().equals(ans) && group.getOwner()==id) {
                        exists = true;
                        Cli.print("\n~ "+group.getName()+" ~",ConsoleColors.CYAN_BRIGHT);

                        if (group.getMembers().isEmpty()) {
                            Cli.print("\nEMPTY!",ConsoleColors.RED_BOLD);
                        } else {

                            for (int i = 0; i < group.getMembers().size(); i++) {
                                User member = User.getByID(group.getMembers().get(i));
                                Cli.print("~" + member.getUserName(),ConsoleColors.CYAN);
                            }
                        }
                        Cli.print("\n✦ AddMember\n✦ DeleteMember",ConsoleColors.CYAN_BRIGHT);
                        String ans1 = Cli.get();
                        if (ans1.equals("AddMember")) {
                            Groups.addMember(group.getId());
                        } else if (ans1.equals("DeleteMember")) {
                            deleteMember(group.getId());
                        } else if (ans1.equals("Back")) {
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
                if (!exists) {
                    Cli.print("\nGroup can't be found!",ConsoleColors.RED_BOLD);
                    break;
                }

            }
        }
    }

    public static void addMember(int groupId) {
        Cli.print("\nEnter username: ",ConsoleColors.WHITE_BRIGHT);
            String ans = Cli.get();
                Group group = Group.getByID(groupId);
                File usersDirectory = new File("./src/main/resources/Info/Users");
                boolean exists = false;
                for (File file : usersDirectory.listFiles()) {
                    User user1 = User.getByID(ID.getIdFromFileName(file.getName()));
                    if (user1.getUserName().equals(ans)) {
                        exists = true;
                        User member = User.getByID(user1.getId());
                        if (group.getMembers().contains(member.getId())) {
                            Cli.print("\nThis user is already a member!",ConsoleColors.RED_BOLD);
                        } else {
                            User owner = User.getByID(group.getOwner());
                            if (owner.isFollowing(member.getId())) {
                                group.getMembers().add(member.getId());
                                group.saveIntoDB();
                                owner.saveIntoDB();
                            } else {
                                Cli.print("\nYou can only add your followings to the groups",ConsoleColors.RED);
                            }
                        }
                    }
                }
                if (!exists) {
                    Cli.print("\nUser can't be found!",ConsoleColors.RED_BOLD);
                }
    }

    public static void deleteMember(int groupId){
        Cli.print("\nEnter username: ",ConsoleColors.WHITE_BRIGHT);
            String ans = Cli.get();
                Group group = Group.getByID(groupId);
                File usersDirectory = new File("./src/main/resources/Info/Users");
                boolean exists = false;
                for (File file : usersDirectory.listFiles()){
                    exists=true;
                    User user1=User.getByID(ID.getIdFromFileName(file.getName()));
                    if(user1.getUserName().equals(ans)) {
                        User member = User.getByID(user1.getId());
                        User owner = User.getByID(group.getOwner());

                        if (owner.getFollowings().contains(member.getId())) {


                            if (group.getMembers().contains(member.getId())) {
                                group.getMembers().remove((Object)member.getId());
                                group.saveIntoDB();

                            } else {
                                Cli.print("\nThis user is not in the group!",ConsoleColors.RED_BOLD);
                            }
                        }
                        else {
                            Cli.print("\nThis user is not in your followings:/",ConsoleColors.RED_BOLD);
                        }
                    }
                }
                if (!exists) {
            Cli.print("\nUser can't be found!",ConsoleColors.RED_BOLD);
                }

            }

}

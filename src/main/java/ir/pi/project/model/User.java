package ir.pi.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.*;
import java.util.*;

public class User {
    static private final Logger logger= LogManager.getLogger(User.class);

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private String biography;
    private String lastSeen;
    private String lastSeenState;
    private List<Integer> tweets;
    private List<Integer> retweets;
    private List<Integer> likedTweets;
    private List<Integer> followers;
    private List<Integer> followings;
    private List<Integer> blackList;
    private List<Integer> muted;
    private boolean isOnline;
    private boolean isActive;
    private boolean isPublic;
    private boolean EPBCanSee;
    private List<Integer> savedMessages;
    private List<Integer> requests;
    private List<String> myRequests;
    private List<String> notifications;
    private List<List<Integer>> chats;
    private List<List<Integer>> unReadChats;
    private HashMap<String, Integer> unReadUsernames;
    private List<String> unRead;
    private List<Integer> groups;



    public User(String firstName, String lastName, String userName, String password, String email) {
        try {
            File lastId = new File("./src/main/resources/lastId");
            Scanner sc = new Scanner(lastId);
            int q = sc.nextInt();
            this.id = q;
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.lastSeenState = "EveryOne";
            this.isActive = true;
            this.isPublic = true;
            this.tweets = new ArrayList<>();
            this.retweets = new ArrayList<>();
            this.likedTweets = new ArrayList<>();
            this.followers = new ArrayList<>();
            this.followings = new ArrayList<>();
            this.blackList = new ArrayList<>();
            this.muted = new ArrayList<>();
            this.notifications = new ArrayList<>();
            this.requests = new ArrayList<>();
            this.chats = new ArrayList<>();
            this.unReadChats = new ArrayList<>();
            this.unReadUsernames = new HashMap<>();
            this.unRead = new ArrayList<>();
            this.savedMessages = new ArrayList<>();
            this.groups = new ArrayList<>();
            this.myRequests=new ArrayList<>();

            FileOutputStream fout = new FileOutputStream(lastId, false);

            PrintStream out = new PrintStream(fout);
            q++;
            out.println(q);
            out.flush();
            out.close();


        } catch (FileNotFoundException e) {
            logger.warn("New user could not be made");
            e.printStackTrace();
        }
    }


    public boolean isOnline() {
        return isOnline;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public List<Integer> getBlackList() {
        return blackList;
    }

    public List<Integer> getMuted() {
        return muted;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public List<Integer> getTweets() {
        return tweets;
    }

    public List<Integer> getRequests() {
        return requests;
    }

    public List<String> getMyRequests() {
        return myRequests;
    }

    public List<Integer> getLikedTweets() {
        return likedTweets;
    }

    public List<Integer> getRetweets() {
        return retweets;
    }

    public List<List<Integer>> getChats() {
        return chats;
    }


    public Map<String, Integer> getUnReadUsernames() {
        return unReadUsernames;
    }

    public List<String> getUnRead() {
        return unRead;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public List<Integer> getSavedMessages() {
        return savedMessages;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public String getLastSeenState() {
        return lastSeenState;
    }

    public boolean isEPBCanSee() {
        return EPBCanSee;
    }

    public void setEPBCanSee(boolean EPBCanSee) {
        this.EPBCanSee = EPBCanSee;
    }

    public void setLastSeenState(String lastSeenState) {
        this.lastSeenState = lastSeenState;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.saveIntoDB();
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }


    public static User getByID(int id) {
        try {
            File directory = new File("./src/main/resources/Info/Users");
            File Data = new File(directory, id + ".json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            logger.info("file " + id + " opened");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            User user = gson.fromJson(bufferedReader, User.class);
            bufferedReader.close();
            return user;
        } catch (IOException e) {
            logger.warn("tweet with id: " + id + " could not be found in getByID");
            e.printStackTrace();
        }
        return null;
    }


    public void saveIntoDB() {
        try {
            File directory = new File("./src/main/resources/Info/Users");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            File Data = new File(directory, this.id + ".json");
            if (!Data.exists())
                Data.createNewFile();
            FileWriter writer = new FileWriter(Data);
            writer.write(gson.toJson(this));
            writer.flush();
            writer.close();
            logger.info("File " + this.id + " closed");

        } catch (IOException e) {
            logger.warn("user with id: " + id + " could not be saved in saveIntoDB");
            e.printStackTrace();
        }
    }


    public boolean isMutedBy(int id) {
        User user1 = User.getByID(id);
        for (int i = 0; i < user1.getMuted().size(); i++) {
            if (user1.getMuted().get(i) == this.id) {
                return true;
            }
        }

        return false;
    }

    public boolean isBlockedBy(int id) {
        User user1 = User.getByID(id);
        for (int i = 0; i < user1.getBlackList().size(); i++) {
            if (user1.getBlackList().get(i) == this.id) {
                return true;
            }
        }
        return false;
    }


    public boolean isFollowing(int id) {
        User otherUser = User.getByID(id);
        for (int i = 0; i < otherUser.getFollowers().size(); i++) {
            if (otherUser.getFollowers().get(i) == this.id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRequestedToFollow(int id) {
        User user1 = User.getByID(id);
        for (int i = 0; i < user1.getRequests().size(); i++) {
            if (user1.getRequests().get(i).equals(this.id)) {
                return true;
            }
        }
        return false;
    }


    public void mute(int id) {
        User user1 = User.getByID(id);
        if (!user1.isMutedBy(this.id)) {
            muted.add(user1.getId());

        }
        this.saveIntoDB();
        logger.info(this.getUserName() + " muted " + user1.getUserName());
    }

    public void follow(int id) {
        User user1 = User.getByID(id);
        if (user1.isPublic()) {
            user1.getFollowers().add(this.id);
            followings.add(id);
            user1.getNotifications().add(this.userName + " started following you!");

        } else {
            user1.getRequests().add(this.id);
        }

        user1.saveIntoDB();
        this.saveIntoDB();
        logger.info(this.getUserName() + " followed " + user1.getUserName());
    }

    public void unFollow(int id) {
        User user1 = User.getByID(id);
        for (int i = 0; i < user1.getFollowers().size(); i++) {
            if (user1.getFollowers().get(i) == this.id) {
                user1.getFollowers().remove(i);
                for (Integer groupId :
                        user1.getGroups()) {
                    Group group = Group.getByID(groupId);
                    if (group.getMembers().contains(user1.getId())) {
                        group.getMembers().remove((Object) user1.getId());
                    }
                }
                user1.getNotifications().add(this.userName + " stopped following you!");
            }
        }
        for (int i = 0; i < followings.size(); i++) {
            if (followings.get(i) == user1.id) {
                followings.remove(i);
            }
        }
        user1.saveIntoDB();
        this.saveIntoDB();
        logger.info(this.getUserName() + " unfollowed " + user1.getUserName());
    }

    public void block(int id) {
        blackList.add(id);
        if (this.isFollowing(id)) this.unFollow(id);
        User user1 = User.getByID(id);
        if (user1.isFollowing(this.id)) {
            user1.unFollow(this.id);
        }

        this.saveIntoDB();
        logger.info(this.getUserName() + " blocked " + user1.getUserName());
    }

    public void unBlock(int id) {
        User user1 = User.getByID(id);
        for (int i = 0; i < blackList.size(); i++) {
            if (id == blackList.get(i)) {
                blackList.remove(i);
            }
        }
        this.saveIntoDB();
        logger.info(this.getUserName() + " unfollowed " + user1.getUserName());
    }


}
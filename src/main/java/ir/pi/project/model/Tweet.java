package ir.pi.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tweet {

    static private final Logger logger= LogManager.getLogger(Tweet.class);

    int id;
    int writer;
    String text;
    List<Integer> comments;
    int likesNum;
    LocalDateTime time;

    public Tweet(User user, String text) {
        try {
            File lastId = new File("./src/main/resources/lastId");
            Scanner sc = new Scanner(lastId);
            int q = sc.nextInt();
            this.id = q;
            this.writer = user.getId();
            this.text = text;
            this.comments = new ArrayList<>();
            this.likesNum = 0;
            this.time = LocalDateTime.now();

            FileOutputStream fout = new FileOutputStream(lastId, false);

            PrintStream out = new PrintStream(fout);
            q++;
            out.println(q);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e){
            logger.warn("New tweet could not be made");
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public int getWriter() {
        return writer;
    }

    public String getText() {
        return text;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<Integer> getComments() {
        return comments;
    }

    public static Tweet getByID(int id)  {
        try {
            File directory=new File("./src/main/resources/Info/Tweets");
            File Data = new File(directory, id + ".json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            logger.info("file "+id+ " opened");

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Tweet tweet=gson.fromJson(bufferedReader, Tweet.class);
            bufferedReader.close();
            return tweet;
        }
        catch (Exception e) {
            System.out.println("haha");
            logger.warn("tweet with id: "+id+" could not be found in getByID");
            e.printStackTrace();
        }
        return null;
    }


    public void saveIntoDB() {
        try {
            File directory=new File("./src/main/resources/Info/Tweets");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            File Data = new File(directory, this.id + ".json");
            if (!Data.exists())
                Data.createNewFile();
            FileWriter writer = new FileWriter(Data);
            writer.write(gson.toJson(this));
            writer.flush();
            writer.close();
            logger.info("File "+this.id+" closed");

        } catch (IOException e) {
            logger.warn("tweet with id: "+id+" could not be saved in saveIntoDB");
            e.printStackTrace();
        }
    }


    public void addToLikeNums(int n){
     this.likesNum=likesNum+n;
    }


}




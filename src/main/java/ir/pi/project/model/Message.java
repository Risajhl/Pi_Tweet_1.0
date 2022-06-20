package ir.pi.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Message {

    static private final Logger logger= LogManager.getLogger(Message.class);

    private int id;
    private int senderId;
    private int receiverId;
    private String text;
    private LocalDateTime time;

    public Message(int senderId,int receiverId, String text){
        try {
            File lastId = new File("./src/main/resources/lastId");
            Scanner sc = new Scanner(lastId);
            int q = sc.nextInt();
            this.id = q;
            this.senderId=senderId;
            this.receiverId=receiverId;
            this.text = text;
            this.time = LocalDateTime.now();

            FileOutputStream fout = new FileOutputStream(lastId, false);

            PrintStream out = new PrintStream(fout);
            q++;
            out.println(q);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e){
            logger.warn("New message could not be made");
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public static Message getByID(int id)  {
        try {
            File directory=new File("./src/main/resources/Info/Messages");
            File Data = new File(directory, id + ".json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            logger.info("file "+id+ " opened");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Message message= gson.fromJson(bufferedReader, Message.class);
            bufferedReader.close();
            return message;
        }
        catch (IOException e) {
            logger.warn("message with id: "+id+" could not be found in getByID");
            e.printStackTrace();
        }
        return null;
    }


    public void saveIntoDB() {
        try {
            File directory=new File("./src/main/resources/Info/Messages");
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
            logger.warn("message with id: "+id+" could not be saved in saveIntoDB");
            e.printStackTrace();
        }
    }


}

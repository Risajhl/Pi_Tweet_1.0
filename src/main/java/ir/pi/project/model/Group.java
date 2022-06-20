package ir.pi.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Group {

    static private final Logger logger= LogManager.getLogger(Group.class);

    private int id;
    private int owner;
    private String name;
    private List<Integer> members;
    public Group(String name, int ownerId){
        try {
            File lastId = new File("./src/main/resources/lastId");
            Scanner sc = new Scanner(lastId);
            int q = sc.nextInt();
            this.id = q;
            this.name=name;
            this.owner=ownerId;
            this.members=new ArrayList<>();

            FileOutputStream fout = new FileOutputStream(lastId, false);

            PrintStream out = new PrintStream(fout);
            q++;
            out.println(q);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e){
            logger.warn("New group could not be made");
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMembers() {
        return members;
    }


    public static Group getByID(int id)  {
        try {
            File directory=new File("./src/main/resources/Info/Groups");
            File Data = new File(directory, id + ".json");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            logger.info("file "+id+ " opened");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Group group=gson.fromJson(bufferedReader, Group.class);
            bufferedReader.close();
            return group;
        }
        catch (IOException e) {

            logger.warn("group with id: "+id+" could not be found in getByID");
            e.printStackTrace();
        }
        return null;
    }


    public void saveIntoDB() {
        try {
            File directory=new File("./src/main/resources/Info/Groups");
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
            logger.warn("group with id: "+id+" could not be saved in saveIntoDB");
            e.printStackTrace();
        }
    }
}

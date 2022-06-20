package ir.pi.project.logic.entering;

import ir.pi.project.*;
import ir.pi.project.logic.MainMenu;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class LogIn {
    static private final Logger logger= LogManager.getLogger(LogIn.class);
    public static void logInLogic(){
        Cli.print("\n~ Log in ~\n", ConsoleColors.YELLOW_BRIGHT);
        String username=Cli.getter("Enter your username",ConsoleColors.YELLOW);
        if(username.equals("Back")){
            Welcome.welcomeLogic();
        }
        String password=Cli.getter("Enter your password",ConsoleColors.YELLOW);

        boolean found=false;
        File directory=new File("./src/main/resources/Info/Users");
        for (File file:
             directory.listFiles()) {
            int id= ID.getIdFromFileName(file.getName());
            User user=User.getByID(id);
            if(user.getUserName().equals(username) && user.getPassword().equals(password)){
                Cli.print("\nLogged in successfully!",ConsoleColors.GREEN_BRIGHT);
                user.setLastSeen("Online");
                user.setOnline(true);
                user.saveIntoDB();
                logger.info(user.getUserName()+" logged in");
                MainMenu.mainMenuLogic(id);
                found=true;
            }
        }


        if(!found){
            Cli.print("\n!!! User Can't Be Found !!!",ConsoleColors.RED_BOLD);
            logInLogic();
        }
    }
}

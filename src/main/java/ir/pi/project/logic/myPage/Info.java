package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class Info {
    static private final Logger logger= LogManager.getLogger(Info.class);
    public static void infoLogic (int id){
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered Info");
        while (true) {
            User user = User.getByID(id);
            Cli.print("\n~ Info ~\n", ConsoleColors.YELLOW_BRIGHT);
            Cli.print("✦ FirstName: ",ConsoleColors.BLUE);
            Cli.print(user.getFirstName()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ LastName: ",ConsoleColors.GREEN);
            Cli.print(user.getLastName()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ UserName: ",ConsoleColors.PURPLE);
            Cli.print(user.getUserName()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ Email: ",ConsoleColors.RED);
            Cli.print(user.getEmail()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ PhoneNumber: ",ConsoleColors.CYAN);
            Cli.print(user.getPhoneNumber()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ BirthDate: ",ConsoleColors.CYAN_BRIGHT);
            Cli.print(user.getBirthDate()+"\n",ConsoleColors.WHITE_BRIGHT);
            Cli.print("✦ Biography: ",ConsoleColors.YELLOW);
            Cli.print(user.getBiography()+"\n",ConsoleColors.WHITE_BRIGHT);

            Cli.print("\n<<Type 'Back' to go to MyPage>>",ConsoleColors.WHITE_BOLD);

            String ans = Cli.get();
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

            else {
                Cli.invalid();
            }


        }
    }
}

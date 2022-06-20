package ir.pi.project.logic.myPage;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;

import ir.pi.project.logic.entering.SignUp;
import ir.pi.project.logic.entering.Welcome;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class EditInfo {
    static private final Logger logger= LogManager.getLogger(EditInfo.class);
    public static void editInfoLogic(int id) {
        User thisUser=User.getByID(id);
        logger.info(thisUser.getUserName()+" entered EditInfo");
        while (true) {
            Cli.print("\n~ EditInfo ~", ConsoleColors.CYAN_BRIGHT);
            Cli.print("\nChoose which one to change:",ConsoleColors.BLUE);
            Cli.print("\n✦ FirstName\n✦ LastName\n✦ UserName\n✦ Email\n✦ PhoneNumber\n✦ Biography",ConsoleColors.CYAN_BOLD);

            String ans = Cli.get();
            User user = User.getByID(id);
            if (ans.equals("FirstName")) {
                Cli.print("\nCurrent FirstName: "+ user.getFirstName()+ "\nEnter firstName: ",ConsoleColors.WHITE_BRIGHT);
                String s=Cli.getter("Enter firstName: " ,ConsoleColors.WHITE);
                logger.info("user "+user.getUserName()+" changed firstName from "+user.getFirstName()+" to "+s);
                user.setFirstName(s);
                user.saveIntoDB();
                Cli.print("\nFirstName has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

            } else if (ans.equals("LastName")) {
                Cli.print("\nCurrent LastName: "+ user.getLastName()+ user.getLastName() + "\nEnter LastName: ",ConsoleColors.WHITE_BRIGHT);

                String s = Cli.get();
                logger.info("user "+user.getUserName()+" changed lastName from "+user.getLastName()+" to "+s);
                user.setLastName(s);
                user.saveIntoDB();
                Cli.print("\nLastName has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

            } else if (ans.equals("UserName")) {
                Cli.print("\nCurrent UserName: "+ user.getUserName() + "\nEnter UserName: ",ConsoleColors.WHITE_BRIGHT);

                String s =Cli.get();
                if(SignUp.isUserNameAvailable(s)) {
                    logger.info("user "+user.getUserName()+" changed username from to "+s);
                    user.setUserName(s);
                    user.saveIntoDB();

                    Cli.print("\nUserName has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

                }
                else {
                    Cli.print("\nUserName already taken!",ConsoleColors.RED_BOLD);
                }

            } else if (ans.equals("Email")) {
                Cli.print("\nCurrent Email: "+ user.getEmail()+ "\nEnter Email: ",ConsoleColors.WHITE_BRIGHT);

                String s = Cli.get();
                logger.info("user "+user.getUserName()+" changed email from "+user.getEmail()+" to "+s);
                user.setEmail(s);
                user.saveIntoDB();
                Cli.print("\nEmail has been successfully changed!",ConsoleColors.GREEN_BRIGHT);


            } else if (ans.equals("PhoneNumber")) {
                Cli.print("\nCurrent PhoneNumber: "+ user.getPhoneNumber() + "\nEnter PhoneNumber: ",ConsoleColors.WHITE_BRIGHT);
                String s = Cli.get();
                logger.info("user "+user.getUserName()+" changed phoneNumber from "+user.getPhoneNumber()+" to "+s);
                user.setPhoneNumber(s);
                user.saveIntoDB();
                Cli.print("\nPhoneNumber has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

            } else if (ans.equals("BirthDate")) {
                Cli.print("\nCurrent BirthDate: "+ user.getBirthDate()+ "\nEnter BirthDate ----/--/--: ",ConsoleColors.WHITE_BRIGHT);
                String s = Cli.get();
                logger.info("user "+user.getUserName()+" changed birthDate from "+user.getBirthDate()+" to "+s);
                user.setBirthDate(s);
                user.saveIntoDB();
                Cli.print("\nPhoneNumber has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

            } else if (ans.equals("Biography")) {
                Cli.print("\nCurrent Biography: "+ user.getBiography()+"\nEnter Biography (in one line)",ConsoleColors.WHITE_BRIGHT);

                String s = Cli.get();
                logger.info("user "+user.getUserName()+" changed phoneNumber from {"+user.getBiography()+"} to {"+s+"}");
                user.setBiography(s);
                user.saveIntoDB();
                Cli.print("\nBiography has been successfully changed!",ConsoleColors.GREEN_BRIGHT);

            } else if (ans.equals("Back")) {
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

package ir.pi.project.logic.entering;

import ir.pi.project.*;
import ir.pi.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;

public class SignUp {
    static private final Logger logger= LogManager.getLogger(SignUp.class);
    public static void signUpLogic(){

        Cli.print("\n~ Sign up ~\n", ConsoleColors.BLUE_BRIGHT);
        String firstName=Cli.getter("Enter your firstName:",ConsoleColors.BLUE);
        String lastName=Cli.getter("Enter your lastName:",ConsoleColors.BLUE);

        String userName="";

        while (true) {
            userName=Cli.getter("Enter your userName:",ConsoleColors.BLUE);
            if(isUserNameAvailable(userName)){
                break;
            }else {
                Cli.print("\nUserName already taken!\n",ConsoleColors.RED_BOLD);
            }
        }
        String password=Cli.getter("Enter your password:",ConsoleColors.BLUE);

        String email="";
        while (true) {
            email=Cli.getter("Enter your email:",ConsoleColors.BLUE);
            if(isEmailAvailable(email)){
                break;
            }else {
                Cli.print("\nThere is already an account with this email!\n",ConsoleColors.RED_BOLD);
            }
        }


        User user=new User(firstName,lastName,userName,password,email);

        String birthDate=Cli.getter("Enter your birthDate ----/--/--:",ConsoleColors.BLUE);
        user.setBirthDate(birthDate);

        String phoneNumber="";
        while (true) {
            phoneNumber=Cli.getter("Enter your phoneNumber:",ConsoleColors.BLUE);
            if(isPhoneNumberAvailable(phoneNumber)){
                break;
            }else {
                Cli.print("\nThere is already an account with this phoneNumber!\n",ConsoleColors.RED_BOLD);
            }
        }
        user.setPhoneNumber(phoneNumber);



        Cli.print("\nEvery one see birthDate/email/phoneNumber? (Yes/No)",ConsoleColors.WHITE_BRIGHT);
        while (true){
            String ans=Cli.get();
                if(ans.equals("Yes")){
                    user.setEPBCanSee(true);
                    break;
                }
                else if(ans.equals("No")){
                    user.setEPBCanSee(false);
                    break;

                }else {

                    Cli.invalid();
                }
            }


        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
        user.setLastSeen("LastSeen: "+date);
        user.setOnline(false);
        user.saveIntoDB();
        Cli.print("\nYour account has been created!",ConsoleColors.GREEN_BRIGHT);
        logger.info(user.getUserName()+" signed up");
        Welcome.welcomeLogic();

    }

    public static boolean isUserNameAvailable(String username){
        File directory=new File("./src/main/resources/Info/Users");
        for (File file:
             directory.listFiles()) {
            User user=User.getByID(ID.getIdFromFileName(file.getName()));
            if(user.getUserName().equals(username))return false;
        }
        return true;
    }
    public static boolean isEmailAvailable(String email){
        File directory=new File("./src/main/resources/Info/Users");
        for (File file:
             directory.listFiles()) {
            User user=User.getByID(ID.getIdFromFileName(file.getName()));
            if(user.getEmail().equals(email))return false;
        }
        return true;
    }
    public static boolean isPhoneNumberAvailable(String phoneNumber){
        File directory=new File("./src/main/resources/Info/Users");
        for (File file:
             directory.listFiles()) {
            User user=User.getByID(ID.getIdFromFileName(file.getName()));
            if(user.getPhoneNumber().equals(phoneNumber))return false;
        }
        return true;
    }
}

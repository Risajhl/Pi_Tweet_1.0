package ir.pi.project.logic.entering;

import ir.pi.project.Cli;
import ir.pi.project.ConsoleColors;

public class Welcome {
    public static void welcomeLogic(){
        while (true) {
            Cli.print("\n✦ Welcome to πTweet:) ✦", ConsoleColors.PURPLE_BOLD);
            Cli.print("\nAlready have an account?" ,ConsoleColors.PURPLE_BRIGHT);
            Cli.print("If yes, Enter ---yes\nIf no, Enter ---no", ConsoleColors.WHITE_BRIGHT);
            String ans = Cli.get();
            if (ans.equals("---no")) {
                SignUp.signUpLogic();
            } else if (ans.equals("---yes")) {
                LogIn.logInLogic();
            } else {
                Cli.invalid();
            }
        }
    }
}

package ir.pi.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Cli {
    static private final Logger logger= LogManager.getLogger(Cli.class);
    public static String getter(  String text , String color){
        System.out.println(color+text);
        Scanner sc=new Scanner(System.in);
        String ans=sc.nextLine();
        return ans;
    }

    public static String get(){
        Scanner sc=new Scanner(System.in);
        String s=sc.nextLine();
        if(s.equals("Exit")){
            logger.info("Program stopped");
            System.exit(1);
        }
        return s;
    }

    public static void print(String text, String color){
        System.out.println(color+text);
//        print("",ConsoleColors.RESET);
    }
    public static void invalid(){
        print("\n!!! Invalid !!!", ConsoleColors.RED_BOLD);
        print("",ConsoleColors.RESET);
    }

}
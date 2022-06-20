package ir.pi.project;
import ir.pi.project.logic.entering.Welcome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Main {

    static private final Logger logger= LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Program started");
        Welcome.welcomeLogic();

    }
}

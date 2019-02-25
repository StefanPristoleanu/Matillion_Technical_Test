package technical.matillion;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatillionApplication implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(MatillionApplication.class.getName());

    @Autowired
    private SQLConnect mySQLConnection;

    public static void main(String[] args) {
        SpringApplication.run(MatillionApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getOptionNames().size() < 3) {
            logger.info("Please include the required arguments in format: \n"
                    + "--department=5 --pay_type=Monthly --education_level=\"Graduate Degree\" \n"
                    + "The app will start with the default parameters from application.properties file.");
            mySQLConnection.selectQuery();
            return;
        }
        logger.info("Application started with command-line arguments: ");
        for (String name : args.getOptionNames()) {
            logger.info("arg: " + name + "=" + args.getOptionValues(name).get(0));
        }
        mySQLConnection.selectQuery();
    }

}

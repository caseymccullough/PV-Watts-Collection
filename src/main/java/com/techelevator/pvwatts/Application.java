package com.techelevator.pvwatts;

import com.techelevator.pvwatts.dao.GeneratorDao;
import com.techelevator.pvwatts.dao.JdbcGeneratorDao;
import com.techelevator.pvwatts.dao.JdbcUtilityDao;
import com.techelevator.pvwatts.dao.UtilityDao;
import com.techelevator.util.SystemInOutConsole;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Application is the class that launches the Solar System Geek Administrator by creating
 * the objects needed to interact with the user and file system and passing them to
 * the application's controller object.
 */

public class Application {

    public static void main(String[] args) {
        // Create the datasource used by all the DAOs
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/SolarProducer");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");



        // Controller expects the DAOs it needs to be "injected" in the constructor.
        // Create the DAOs needed by the controller.
        UtilityDao utilityDao = new JdbcUtilityDao(dataSource);
        GeneratorDao generatorDao = new JdbcGeneratorDao(dataSource);

        // Create the basic i/o mechanism (the console)
        SystemInOutConsole systemInOutConsole = new SystemInOutConsole();

        // The controller manages the program flow. Create a control and call its run() method to start the menu loop.
        SSGeekAdminController controller =
                new SSGeekAdminController(systemInOutConsole, utilityDao, generatorDao);
        controller.run();
    }
}

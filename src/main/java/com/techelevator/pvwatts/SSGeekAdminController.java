package com.techelevator.pvwatts;

import com.techelevator.pvwatts.dao.GeneratorDao;
import com.techelevator.pvwatts.dao.UtilityDao;
import com.techelevator.pvwatts.exception.DaoException;
import com.techelevator.pvwatts.model.Generator;
import com.techelevator.pvwatts.model.Utility;
import com.techelevator.util.BasicConsole;
import jdk.jshell.execution.Util;

import java.time.LocalDate;
import java.util.List;

/**
 * SSGeekAdminController orchestrates all of its operations through a series of menus. It relies
 * on other classes for the details of interacting with the user and with the database.
 */

public class SSGeekAdminController {

    // The view manages all the user interaction, inputs and outputs.
    private final SSGeekAdminView view;

    // The controller expects these DAO's to be "injected" into via its constructor
    private GeneratorDao generatorDao;
    private UtilityDao utilityDao;


    public SSGeekAdminController(BasicConsole console, UtilityDao utilityDao, GeneratorDao generatorDao) {
        view = new SSGeekAdminView(console);
        this.utilityDao = utilityDao;
        this.generatorDao = generatorDao;
    }

    /**
     * The run() method starts the program flow by running the main menu.
     */
    public void run() {
        displayMainMenu();
    }

    /**
     * A loop which displays the main program menu, and responds to the user's selection
     */
    private void displayMainMenu() {
        // Menu options
        final String UTILITY_MENU = "Utility admin menu";
        final String GENERATOR_MENU = "Generator admin menu";
        final String EXIT = "Exit the program";
        final String[] MENU_OPTIONS = {UTILITY_MENU, GENERATOR_MENU, EXIT};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection =
                    view.getMenuSelection("Welcome to Solar System Geek Administration", MENU_OPTIONS);
            switch (selection) {
                case UTILITY_MENU:
                    displayUtilityMenu();
                    break;
                case GENERATOR_MENU:
                    displayGeneratorMenu();
                    break;
                case EXIT:
                    // Set finished to true so the loop exits.
                    finished = true;
                    break;
            }
        }
    }

    /**
     * A loop which displays the utility sub-menu, and responds to the user's selection
     */
    private void displayUtilityMenu() {
        // Menu options
        final String UTILITY_LIST = "List all utilities";
        final String UTILITY_DETAILS = "View utility details";
        final String UTILITY_ADD = "Add utility";
        final String UTILITY_UPDATE = "Update utility details";
        final String UTILITY_DELETE = "Delete utility";
        final String DONE = "Main menu";
        final String[] MENU_OPTIONS = {UTILITY_LIST, UTILITY_DETAILS, UTILITY_ADD, UTILITY_UPDATE, UTILITY_DELETE, DONE};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection = view.getMenuSelection("Utility administration menu", MENU_OPTIONS);
            try {
                switch (selection) {
                    case UTILITY_LIST:
                        listUtilities();
                        break;
                    case UTILITY_DETAILS:
                        displayUtility();
                        break;
                    case UTILITY_ADD:
                        addUtility();
                        break;
                    case UTILITY_UPDATE:
                        updateUtility();
                        break;
                    case UTILITY_DELETE:
                        deleteUtility();
                        break;
                    case DONE:
                        // Set finished to true so the loop exits.
                        finished = true;
                        break;
                }
            }
            catch (DaoException e) {
                view.printErrorMessage("DAO error - " + e.getMessage());
            }
        }
    }

    /**
     * A loop which displays the product sub-menu, and responds to the user's selection
     */
    private void displayGeneratorMenu() {
         // Menu options
        final String GENERATOR_LIST = "List all generators";
        final String GENERATOR_DETAILS = "Show generator details";
        final String GENERATOR_POWER_FORECAST = "Get power forecast for generator";
        final String DONE = "Main menu";
        final String[] MENU_OPTIONS = {GENERATOR_LIST, GENERATOR_POWER_FORECAST, DONE};

        boolean finished = false;

        // The menu loop
        while (!finished) {
            String selection = view.getMenuSelection("Generator administration menu", MENU_OPTIONS);
            try {
                switch (selection) {
                    case GENERATOR_LIST:
                        listGenerators();
                        break;
                    case GENERATOR_DETAILS:
                        displayGenerator();
                        break;
                    case GENERATOR_POWER_FORECAST:
                        getPowerForecastForGenerator();
                        break;
                    case DONE:
                        // Set finished to true so the loop exits.
                        finished = true;
                        break;
                }
            }
            catch (DaoException e) {
                view.printErrorMessage("DAO error - " + e.getMessage());
            }
        }
    }



    //*******************************************************
    //region Utility menu actions
    //*******************************************************

    private void listUtilities() {

        // Use the DAO to get the list
        List<Utility> utilities = utilityDao.getUtilities();
        // Use the view to display to the user
        view.listUtilities(utilities);
    }

    private void displayUtility() {

        // Get the list of utilities so the user can choose one
        List<Utility> utilities = utilityDao.getUtilities();

        // Display the list of utilities and ask for selection
        Utility utility = view.selectUtility(utilities);
        if (utility == null) {
            // User cancelled
            return;
        }
        // Show details to the user
        view.printUtilityDetail(utility);
    }

    private void getPowerForecastForGenerator() {

        // Get the list of utilities so the user can choose one
        List<Utility> utilities = utilityDao.getUtilities();

        // Display the list of utilities and ask for selection
        Utility utility = view.selectUtility(utilities);
        if (utility == null) {
            // User cancelled
            return;
        }

        // Make call to NREL

    }

    private void addUtility() {

        // Prompt the user for utility information
        Utility newUtility= view.promptForUtilityInformation(null);

        // If utility is null, user cancelled
        if (newUtility == null) {
            return;
        }
        // Call the DAO to add the new utility
        newUtility = utilityDao.createUtility(newUtility);
        // Inform the user
        view.printMessage("Utility " + newUtility.getUtilityId() + " has been created.");
    }

    private void updateUtility() {

        view.printMessage("Under development");

//        // Get the list of products so the user can choose one
//        List<Product> products = productDao.getProducts();
//
//        // Display the list of products and ask for selection
//        Product product = view.selectProduct(products);
//        if (product == null) {
//            // User cancelled
//            return;
//        }
//        // Prompt the user for product information
//        product = view.promptForProductInformation(product);
//
//        // Call the DAO to update the product
//        productDao.updateProduct(product);
//        // Inform the user
//        view.printMessage("Product has been updated.");
    }

    private void deleteUtility() {

        //Get the list of utilities so the user can choose one
        List<Utility> utilities = utilityDao.getUtilities();

//        // Display the list of utilities and ask for selection
        if (utilities.size() == 0) {
            // Nothing can be deleted
            view.printErrorMessage("There are no utilities that may be deleted!");
        }
        Utility utility = view.selectUtility(utilities);
        if (utility == null) {
            // User cancelled
            return;
        }
        // Prompt the user for confirmation
        boolean isConfirmed = view.promptForYesNo("Are you sure you want to DELETE product '" + utility.getName() + "'?");
        if (!isConfirmed) {
            // User cancelled
            return;
        }

        // Call the DAO to delete the product
        utilityDao.deleteUtilityById(utility.getUtilityId());
        // Inform the user
        view.printMessage("Utility has been deleted.");
    }
    //*******************************************************
    //endregion Utility menu actions
    //*******************************************************

    //*******************************************************
    // region Generator menu actions
    //*******************************************************

    private void listGenerators() {

        // Use the DAO to get the list
        List<Generator> generators = generatorDao.getGenerators();
        // Use the view to display to the user
        view.listGenerators(generators);
    }
    private void displayGenerator() {

        List<Generator> generators = generatorDao.getGenerators();

        // Display the list and ask for selection
        Generator generator = view.selectGenerator(generators);
        if (generator == null){
            return;
        }
        view.printGeneratorDetail(generator);

    }

    //*******************************************************
    //endregion Generator menu actions
    //*******************************************************


}

package com.techelevator.pvwatts;

import com.techelevator.pvwatts.model.Generator;
import com.techelevator.pvwatts.model.Utility;
import com.techelevator.util.BasicConsole;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

/**
 * SSGeekAdminView is a class that the SSGeekAdminController uses for gathering information
 * from the user and presenting information to the user. It requires an object that implements
 * the BasicConsole interface to handle the mechanics of reading from and writing to the console.
 */

public class SSGeekAdminView {
    // **************************************************************
    // region Printing to console in color
    // **************************************************************
    /*
    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println has an example of
    printing in color. Here we use (char)27, which is the same character as '\u001B' (hex 1B == dec 27).
    Escape codes for color
    https://en.wikipedia.org/wiki/ANSI_escape_code#Escape_sequences
    +~~~~~~+~~~~~~+~~~~~~~~~~~+
    |  fg  |  bg  |  color    |
    +~~~~~~+~~~~~~+~~~~~~~~~~~+
    |  30  |  40  |  black    |
    |  31  |  41  |  red      |
    |  32  |  42  |  green    |
    |  33  |  43  |  yellow   |
    |  34  |  44  |  blue     |
    |  35  |  45  |  magenta  |
    |  36  |  46  |  cyan     |
    |  37  |  47  |  white    |
    |  39  |  49  |  default  |
    +~~~~~~+~~~~~~+~~~~~~~~~~~+
    */
    private final String FOREGROUND_DEFAULT = (char) 27 + "[39m";
    private final String FOREGROUND_RED = (char) 27 + "[31m";
    private final String FOREGROUND_GREEN = (char) 27 + "[32m";
    private final String FOREGROUND_BLUE = (char) 27 + "[34m";
    // **************************************************************
    // endregion Printing to console in color
    // **************************************************************

    private final BasicConsole console;

    // Constructor expect a console object to print to.
    public SSGeekAdminView(BasicConsole console) {
        this.console = console;
    }

    // **************************************************************
    // region console pass-through methods
    // **************************************************************

    // printMessage passes call through to console
    public void printMessage(String message) {
        console.printMessage(message);
    }

    // printErrorMessage makes the text color RED
    public void printErrorMessage(String message) {
        console.printErrorMessage(FOREGROUND_RED + message + FOREGROUND_DEFAULT);
    }

    // printBanner makes the text color GREEN
    public void printBanner(String message) {
        console.printBanner(FOREGROUND_GREEN + message + FOREGROUND_DEFAULT);
    }

    // promptForYesNo passes call through to console
    public boolean promptForYesNo(String prompt) {
        return console.promptForYesNo(prompt);
    }

    // getMenuSelection prints a BLUE banner, then passes through to console.
    public String getMenuSelection(String menuTitle, String[] options) {
        printBanner(FOREGROUND_BLUE + menuTitle + FOREGROUND_DEFAULT);
        return console.getMenuSelection(options);
    }

    // **************************************************************
    // endregion console pass-through methods
    // **************************************************************

    // **************************************************************
    // region Print lists of objects to the console
    // **************************************************************

    public void listUtilities(List<Utility> utilities) {
        printBanner("All Utilities");
        printUtilitiesList(utilities);
        console.pauseOutput();
    }

    public void listGenerators(List<Generator> generators) {
        printBanner("All Generators");
        printGeneratorList(generators);
        console.pauseOutput();
    }

    private void printUtilitiesList(List<Utility> utilities) {

        for (Utility utility : utilities) {
            printMessage(utility.toString());
        }
    }

    public void printGeneratorList(List<Generator> generators) {

        for (Generator generator : generators) {
            printMessage(generator.toString());
        }
    }


//    private void printLineItemList(List<LineItem> lineItems) {
//        String heading1 = "Product  Name                                                  Qty         Price        Amount";
//        String heading2 = "=======  ==================================================  =====  ============  ============";
//        String formatString = "%7d  %-50s  %5d %12.2f  %12.2f";
//        printMessage(heading1);
//        printMessage(heading2);
//        for (LineItem lineItem : lineItems) {
//            String productName = lineItem.getProductName();
//            if (productName.length() > 50) {
//                productName = productName.substring(0, 47) + "...";
//            }
//            String s = String.format(formatString,
//                    lineItem.getProductId(),
//                    productName,
//                    lineItem.getQuantity(),
//                    lineItem.getPrice(),
//                    lineItem.getExtendedPrice());
//            printMessage(s);
//        }
//    }
    // **************************************************************
    // endregion Print lists of objects to the console
    // **************************************************************

    // **************************************************************
    // region Prompt the user to select an object from the list
    // **************************************************************

    public Utility selectUtility(List<Utility> utilities) {
        while (true) {
            printUtilitiesList(utilities);
            Integer utilityId = console.promptForInteger("Enter utility id [0 to cancel]: ");
            if (utilityId == null || utilityId == 0) {
                return null;
            }
            Utility selectedUtility = findUtilityById(utilities, utilityId);
            if (selectedUtility != null) {
                return selectedUtility;
            }
            printErrorMessage("That's not a valid id, please try again.");
        }
    }

    public Generator selectGenerator(List<Generator> generators) {
        while (true) {
            printGeneratorList(generators);
            Integer generatorId = console.promptForInteger("Enter generator id [0 to cancel]: ");
            if (generatorId == null || generatorId == 0) {
                return null;
            }
            Generator selectedGenerator    = findGeneratorById(generators, generatorId);
            if (selectedGenerator != null) {
                return selectedGenerator;
            }
            printErrorMessage("That's not a valid id, please try again.");
        }
    }

    // **************************************************************
    // endregion Prompt the user to select an object from the list
    // **************************************************************

    // **************************************************************
    // region Find an object in the list
    // **************************************************************
    private Utility findUtilityById(List<Utility> utilities, int utilityId) {
        for (Utility utility : utilities) {
            if (utility.getUtilityId() == utilityId) {
                return utility;
            }
        }
        return null;
    }

    private Generator findGeneratorById(List<Generator> generators, int generatorId) {
        for (Generator generator : generators) {
            if (generator.getGeneratorId() == generatorId) {
                return generator;
            }
        }
        return null;
    }


    // **************************************************************
    // endregion Find an object in the list
    // **************************************************************

    // **************************************************************
    // region Print single object details to the console
    // **************************************************************

    public void printUtilityDetail (Utility utility) {
        printMessage("Utility details: ");
        printMessage(utility.toString());
    }

    public void printGeneratorDetail (Generator generator) {
        printMessage("Generator details: ");
        printMessage(generator.toString());
    }


//    public void printCustomerDetail(Customer customer) {
//        printMessage(String.format("Customer id: %s", customer.getCustomerId()));
//        printMessage(String.format("Customer name: %s", customer.getName()));
//        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
//        printMessage(String.format("Street Address: %s", customer.getStreetAddress1(), " ", customer.getStreetAddress2()));
//        printMessage(String.format("City: %s", customer.getCity()));
//        printMessage(String.format("State: %s", customer.getState()));
//        printMessage(String.format("ZIP Code: %s", customer.getZipCode()));
//        console.pauseOutput();
//    }


    // **************************************************************
    // endregion Print single object details to the console
    // **************************************************************

    // **************************************************************
    // region Prompt the user for object property information
    // **************************************************************

    public Utility promptForUtilityInformation (Utility existingUtility) {
        Utility newUtility = new Utility();
        if (existingUtility == null) {
            // no default values
            newUtility.setName (promptForUtilityName(null));
        } else {
            newUtility.setName (promptForUtilityName(existingUtility.getName()));
        }
        return newUtility;
    }

        private String promptForUtilityName(String defaultValue) {
        return promptForString("Utility name", true, defaultValue);
    }

//    public Customer promptForCustomerInformation(Customer existingCustomer) {
//        Customer newCustomer = new Customer();
//        if (existingCustomer == null) {
//            // No default values
//            newCustomer.setName(promptForCustomerName(null));
//            newCustomer.setStreetAddress1(promptForCustomerStreetAddress1(null));
//            newCustomer.setStreetAddress2(promptForCustomerStreetAddress2(null));
//            newCustomer.setCity(promptForCustomerCity(null));
//            newCustomer.setState(promptForCustomerState(null));
//            newCustomer.setZipCode(promptForCustomerZipCode(null));
//        } else {
//            // This is an update -- make all prompts default to current values
//            // Set the id
//            newCustomer.setCustomerId(existingCustomer.getCustomerId());
//            newCustomer.setName(promptForCustomerName(existingCustomer.getName()));
//            newCustomer.setStreetAddress1(promptForCustomerStreetAddress1(existingCustomer.getStreetAddress1()));
//            newCustomer.setStreetAddress2(promptForCustomerStreetAddress2(existingCustomer.getStreetAddress2()));
//            newCustomer.setCity(promptForCustomerCity(existingCustomer.getCity()));
//            newCustomer.setState(promptForCustomerState(existingCustomer.getState()));
//            newCustomer.setZipCode(promptForCustomerZipCode(existingCustomer.getZipCode()));
//        }
//        return newCustomer;
//    }
//
//    private String promptForCustomerName(String defaultValue) {
//        return promptForString("Customer name", true, defaultValue);
//    }
//
//    private String promptForCustomerStreetAddress1(String defaultValue) {
//        return promptForString("Street 1", true, defaultValue);
//    }
//
//    private String promptForCustomerStreetAddress2(String defaultValue) {
//        return promptForString("Street 2", false, defaultValue);
//    }
//
//    private String promptForCustomerCity(String defaultValue) {
//        return promptForString("City", true, defaultValue);
//    }
//
//    private String promptForCustomerState(String defaultValue) {
//        return promptForString("State (2-letter)", true, defaultValue);
//    }
//
//    private String promptForCustomerZipCode(String defaultValue) {
//        return promptForZipCode("5-digit Zip Code", true, defaultValue);
//    }
//
//    public Product promptForProductInformation(Product existingProduct) {
//        Product newProduct = new Product();
//        if (existingProduct == null) {
//            // No default values
//            newProduct.setName(promptForProductName(null));
//            newProduct.setDescription(promptForDescription(null));
//            newProduct.setPrice(promptForPrice(null));
//            newProduct.setImageName(promptForImageName(null));
//        } else {
//            // This is an update -- make all prompts default to current values
//            // Set the id
//            newProduct.setProductId(existingProduct.getProductId());
//            newProduct.setName(promptForProductName(existingProduct.getName()));
//            newProduct.setDescription(promptForDescription(existingProduct.getDescription()));
//            newProduct.setPrice(promptForPrice(existingProduct.getPrice()));
//            newProduct.setImageName(promptForImageName(existingProduct.getImageName()));
//        }
//        return newProduct;
//    }
//
//    private String promptForProductName(String defaultValue) {
//        return promptForString("Product name", true, defaultValue);
//    }
//
//    private String promptForDescription(String defaultValue) {
//        return promptForString("Description", false, defaultValue);
//    }
//
//    private BigDecimal promptForPrice(BigDecimal defaultValue) {
//        return promptForBigDecimal("Price", true, defaultValue);
//    }
//
//    private String promptForImageName(String defaultValue) {
//        return promptForString("Image name", false, defaultValue);
//    }

    private String promptWithDefault(String prompt, Object defaultValue) {
        if (defaultValue != null) {
            return prompt + "[" + defaultValue.toString() + "]: ";
        }
        return prompt + ": ";
    }

    private String promptForString(String prompt, boolean required, String defaultValue) {
        prompt = promptWithDefault(prompt, defaultValue);
        while (true) {
            String entry = console.promptForString(prompt);
            if (!entry.isEmpty()) {
                return entry;
            }
            // Entry is empty: see if we have a default, or if empty is OK (!required)
            if (defaultValue != null && !defaultValue.isEmpty()) {
                return defaultValue;
            }
            if (!required) {
                return entry;
            }
            printErrorMessage("A value is required, please try again.");
        }
    }
//
//    private BigDecimal promptForBigDecimal(String prompt, boolean required, BigDecimal defaultValue) {
//        prompt = promptWithDefault(prompt, defaultValue);
//        while (true) {
//            BigDecimal entry = console.promptForBigDecimal(prompt);
//            if (entry != null) {
//                return entry;
//            }
//            // Entry is empty: see if we have a default, or if empty is OK (!required)
//            if (defaultValue != null) {
//                return defaultValue;
//            }
//            if (!required) {
//                return BigDecimal.valueOf(0.0);
//            }
//            printErrorMessage("A value is required, please try again.");
//        }
//    }
//
//    private String promptForZipCode(String prompt, boolean required, String defaultValue) {
//        prompt = promptWithDefault(prompt, defaultValue);
//        while (true) {
//            String entry = console.promptForString(prompt);
//
//            if (!entry.isEmpty()) {
//                // Zip code entry must have a length of 5
//                if (entry.length() == 5) {
//                    return entry;
//                }
//                printErrorMessage("Zip Code must have a length of 5, please try again.");
//            }
//            // Entry is empty: see if we have a default, or if empty is OK (!required)
//            if (defaultValue != null && !defaultValue.isEmpty()) {
//                return defaultValue;
//            }
//            if (!required) {
//                return entry;
//            }
//            printErrorMessage("A value is required, please try again.");
//        }
//    }
    // **************************************************************
    // endregion Prompt the user for object property information
    // **************************************************************
}

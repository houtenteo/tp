package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Importing and exporting of csv files into the database
 */
public class CsvAddressBookImportExport implements ImportExport {

    public static final String MESSAGE_DUPLICATE_PERSON = "Import list contains duplicate person(s). These person(s) "
            + "will not be added: \n";

    private static final Logger logger = LogsCenter.getLogger(CsvAddressBookImportExport.class);

    private boolean fileFound = true;
    private String unsuccessfulRowImport = "";
    private String duplicateNameImport = "";
    private int successfulImport = 0;


    private final Path filePath;

    /**
     * Constructor of the import export
     */
    public CsvAddressBookImportExport(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * The filepath the program expects the csv file to be
     * @return
     */
    public Path getImportExportPath() {
        return filePath;
    }

    /**
     * Imports the csv file into the model
     *
     * @param model Model of the addressbook
     * @return Optional list of person
     * @throws DataConversionException if there are any errors found in the data
     */
    @Override
    public Optional<List<Person>>importIntoAddressBook(Model model) throws DataConversionException {
        return importAddressBook(this.filePath, model);
    }

    public String getImportStatus() {
        if (fileFound) {
            return String.format("Successful Imports : " + successfulImport + "\nUnsuccessful rows : "
                    + unsuccessfulRowImport + " Check logs for detailed explaination.\nDuplicate names : "
                    + duplicateNameImport);
        }
        return String.format("CSV file not found in " + filePath);
    }

    /**
     * Returns a list of person optional that are to be imported
     *   Returns {@code Optional.empty()} if the csvfile is not found
     * @param filePath expected path of csv file
     * @param model model to me inserted into
     * @return optional list of person to be added
     * @throws DataConversionException when there are any errors in the person creation
     */
    private Optional<List<Person>> importAddressBook(Path filePath, Model model) throws DataConversionException {
        requireNonNull(filePath);
        unsuccessfulRowImport = ""; // reset
        fileFound = true;

        Optional<List<Person>> csvImportAddressBook = CsvUtil.readCsvFile(filePath);
        if (csvImportAddressBook.isEmpty()) {
            fileFound = false;
            return Optional.empty();
        }

        try {

            addImportIntoAddressBook(csvImportAddressBook.get(), model);

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
        }
        unsuccessfulRowImport = CsvUtil.getUnsuccessfulRow();
        return csvImportAddressBook;
    }

    /**
     * Imports the list of valid people found in the CSV file
     *   Will not include duplicate people
     * @param people list of valid people
     * @param model model to import into
     * @throws IllegalValueException
     */
    public void addImportIntoAddressBook(List<Person> people, Model model) throws IllegalValueException {
        duplicateNameImport = ""; // reset
        successfulImport = 0; // reset
        for (Person importPeople : people) {
            if (model.hasPerson(importPeople)) {
                duplicateNameImport += importPeople.getName().fullName + ", ";
            } else {
                model.addPerson(importPeople);
                successfulImport++;
            }
        }
        logger.info(successfulImport + " person(s) successfully added");
        if (successfulImport != people.size()) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON + duplicateNameImport);
        }
    }

}

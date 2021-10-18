package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.CsvAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.IsDone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

class CsvAdaptedPersonTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DONE = "not yet";


    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DONE = BENSON.getIsDone().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        CsvAdaptedPerson person = new CsvAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        String csvString = INVALID_NAME + ";" + VALID_PHONE + ";" + VALID_EMAIL + ";" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyName_throwsIllegalValueException() {
        String csvString = ";" + VALID_PHONE + ";" + VALID_EMAIL + ";" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        String csvString = VALID_NAME + ";" + INVALID_PHONE + ";" + VALID_EMAIL + ";" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyPhone_throwsIllegalValueException() {
        String csvString = VALID_NAME + ";;" + VALID_EMAIL + ";" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        String csvString = VALID_NAME + ";" + VALID_PHONE + ";" + INVALID_EMAIL + ";" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyEmail_throwsIllegalValueException() {
        String csvString = VALID_NAME + ";" + VALID_PHONE + ";;" + VALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIsDone_throwsIllegalValueException() {
        String csvString = VALID_NAME + ";" + VALID_PHONE + ";" + VALID_EMAIL + ";" + INVALID_DONE;
        CsvAdaptedPerson person = new CsvAdaptedPerson(csvString);
        String expectedMessage = IsDone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}

package seedu.address.commons.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.testutil.TestUtil;

class CsvUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.csv");

    @Test
    public void invalidCsvString_createPerson_emptyOptional() {
        String invalidCsvString = "Name;;;";
        assertEquals(Optional.empty(), CsvUtil.createPerson(invalidCsvString, 0));
    }

    @Test
    public void mixOfValidAndInvalidPeople_fromCsvString_peopleList() {
        String header = "Name;Phone;Email;Done\n";
        String validPerson = "name 3;123456789;email@email.com;TRUE\n";
        String invalidPerson = ";123111222;;\n";
        String csvString = header + validPerson + invalidPerson;
        assertEquals(1, CsvUtil.fromCsvString(csvString).size());
    }

    @Test
    public void invalidFilePath_readCsvFile_emptyOptional() throws DataConversionException {
        assertEquals(Optional.empty(), CsvUtil.readCsvFile(SERIALIZATION_FILE));
    }

}

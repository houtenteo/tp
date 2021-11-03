package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IsDoneTest {

    @Test
    public void equals() {
        IsDone isDone = new IsDone("false");

        // same object -> returns true
        assertTrue(isDone.equals(isDone));

        // same values -> returns true
        IsDone remarkCopy = new IsDone(isDone.value ? "TRUE" : "FALSE");
        assertTrue(isDone.equals(remarkCopy));

        // different types -> returns false
        assertFalse(isDone.equals(1));

        // null -> returns false
        assertFalse(isDone.equals(null));

        // different remark -> returns false
        IsDone differentIsDone = new IsDone("true");
        assertFalse(isDone.equals(differentIsDone));
    }

    @Test
    public void isValidIsDone() {
        //true
        assertTrue(IsDone.isValidIsDone(""));
        assertTrue(IsDone.isValidIsDone("true"));
        assertTrue(IsDone.isValidIsDone("false"));
        assertTrue(IsDone.isValidIsDone("tRuE"));
        assertTrue(IsDone.isValidIsDone("FaLsE"));

        //false
        assertFalse(IsDone.isValidIsDone("this is not valid"));
        assertFalse(IsDone.isValidIsDone("1"));
        assertFalse(IsDone.isValidIsDone("f"));
        assertFalse(IsDone.isValidIsDone("t"));
    }
}

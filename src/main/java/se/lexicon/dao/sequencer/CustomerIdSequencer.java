package se.lexicon.dao.sequencer;

public class CustomerIdSequencer {
    private static int customerIdSequencer = 100;

    public static int nextId() {
        return ++customerIdSequencer;
    }
}

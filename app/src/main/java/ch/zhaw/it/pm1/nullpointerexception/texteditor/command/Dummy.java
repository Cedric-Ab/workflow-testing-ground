package ch.zhaw.it.pm1.nullpointerexception.texteditor.command;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.InvalidInputException;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.TextFormatter;
import ch.zhaw.it.pm1.nullpointerexception.texteditor.command.furtherinput.FurtherInput;

import java.util.List;
import java.util.Optional;

/**
 * Represents the Dummy Command used for testing.
 * Behaves like Add, but is intended for inserting dummy text.
 */
public class Dummy extends Add {
    public static final String DUMMY_TEXT = "Vorzeitige Beendigung des Studiums (Austrittserklärung); " +
            "Rechtsgrundlagen: " +
            "Die vorzeitige Beendigung des Studiums ist in der Rahmenprüfungsordnung für " +
            "Bachelor- und Masterstudiengänge an der Zürcher Hochschule für Angewandte Wissenschaft " +
            "vom 29. Januar 2008 / 22 und 35 (RPO) und durch das Reglement zur Zulassung, Immatrikulation und " +
            "Exmatrikulation an der ZHAW vom 1. März 2012 / Art. 25-28 " +
            "(Reglement Immatrikulation/Exmatrikulation ZHAW) geregelt; " +
            "Allgemeine Informationen zur vorzeitigen Beendigung des Studiums: " +
            "- Die rechtlichen Konsequenzen der vorzeitigen Abmeldung sind den studierenden Personen gemäss RPO " +
            "(22 und 35) und dem Reglement Immatrikulation/Exmatrikulation ZHAW (Art. 25-28) bekannt. " +
            "- Sie bleiben im angemeldeten Semester in den Modulen und für die Prüfungen eingeschrieben. " +
            "Nicht absolvierte Leistungsnachweise werden mit der Note 1.0 bewertet. " +
            "- Abgeschlossene Module werden mit Note und ECTS-Punkten bestätigt und unter myZHAW ausgewiesen. " +
            "Die Datenabschrift kann nach der Notenaufschaltung am Ende des Semesters auf myZHAW eingesehen werden. " +
            "- Eine verspätete Abmeldung verpflichtet zur Entrichtung der Semestergebühr " +
            "(22 der RPO vom 29. Januar 2008). Die Abmeldefristen sind im Dokument \"Wichtige Daten Studium\" unter " +
            "https://www.zhaw.ch/de/lsfm/studium/bachelor/studium-organisieren festgehalten. Davon ausgenommen sind " +
            "Studienabbrüche aufgrund nichtbestandener Repetitionsprüfungen und Modulwiederholungen. " +
            "- Die Exmatrikulationsbestätigung erhalten Sie aus administrativen Gründen " +
            "am Ende des laufenden Semesters." +
            "- Reglement Immatrikulation/Exmatrikulation und die vorzeitige Beendung des Studiums." +
            "Rechtsgrundlagen: " +
            "Die vorzeitige Beendigung des Studiums ist in der Rahmenprüfungsordnung für " +
            "Bachelor- und Masterstudiengänge an der Zürcher Hochschule für Angewandte Wissenschaft " +
            "vom 29. Januar 2008 / 22 und 35 (RPO) und durch das Reglement zur Zulassung, Immatrikulation und " +
            "Exmatrikulation an der ZHAW vom 1. März 2012 / Art. 25-28 " +
            "(Reglement Immatrikulation/Exmatrikulation ZHAW) geregelt; " +
            "Allgemeine Informationen zur vorzeitigen Beendigung des Studiums: " +
            "- Die rechtlichen Konsequenzen der vorzeitigen Abmeldung sind den studierenden Personen gemäss RPO " +
            "(22 und 35) und dem Reglement Immatrikulation/Exmatrikulation ZHAW (Art. 25-28) bekannt. " +
            "- Sie bleiben im angemeldeten Semester in den Modulen und für die Prüfungen eingeschrieben. " +
            "Nicht absolvierte Leistungsnachweise werden mit der Note 1.0 bewertet. " +
            "- Abgeschlossene Module werden mit Note und ECTS-Punkten bestätigt und unter myZHAW ausgewiesen. " +
            "Die Datenabschrift kann nach der Notenaufschaltung am Ende des Semesters auf myZHAW eingesehen werden. " +
            "- Eine verspätete Abmeldung verpflichtet zur Entrichtung der Semestergebühr " +
            "(22 der RPO vom 29. Januar 2008). Die Abmeldefristen sind im Dokument \"Wichtige Daten Studium\" unter " +
            "https://www.zhaw.ch/de/lsfm/studium/bachelor/studium-organisieren festgehalten. Davon ausgenommen sind " +
            "Studienabbrüche aufgrund nichtbestandener Repetitionsprüfungen und Modulwiederholungen. " +
            "- Die Exmatrikulationsbestätigung erhalten Sie aus administrativen Gründen " +
            "am Ende des laufenden Semesters." +
            "- Reglement Immatrikulation/Exmatrikulation und die vorzeitige Beendung des Studiums.";

    /**
     * Creates a new Dummy Command instance.
     *
     * @param arguments the additional input given upon calling of the command
     * @throws InvalidInputException if the additional input is invalid
     */
    public Dummy(String arguments, Text text, TextFormatter textFormatter) throws InvalidInputException {
        super(arguments, text, textFormatter);
    }

    @Override
    protected List<FurtherInput<?>> getAllFurtherInputs() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand() {
        try {
            Optional<FurtherInput<?>> optionalFurtherInput = super.getAllFurtherInputs().stream().findFirst();

            if (optionalFurtherInput.isEmpty()) {
                logger.severe("No Further Input found for Dummy Command.");
                return;
            }

            optionalFurtherInput.get().resolve(DUMMY_TEXT);
            super.executeCommand();
        } catch (InvalidInputException e) {
            logger.severe("Sending DUMMY_TEXT to Add Command failed with " + e);
        }
    }
}

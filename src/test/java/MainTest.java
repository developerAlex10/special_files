import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainTest {
    private static final String fileCsvTest = "test_data.csv";
    private static final String invalidFileCsvTest = "invalid_test_data.csv";
    private static final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

    @Test
    @DisplayName("Проверка создания 3-х файлов")
    public void csvFileCreateTest() throws IOException {
        createCsvFile();
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Проверка данных первого пользователя")
    public void csvFileOneTest() throws IOException {
        createCsvFile();
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Employee firstEmployee = result.get(0);
        Assertions.assertEquals(1, firstEmployee.id);
        Assertions.assertEquals("John", firstEmployee.firstName);
        Assertions.assertEquals("Smith", firstEmployee.lastName);
        Assertions.assertEquals("USA", firstEmployee.country);
        Assertions.assertEquals(25, firstEmployee.age);
    }

    @Test
    @DisplayName("Проверка обработки пустого CSV файла")
    public void emptyCsvFileTest() throws IOException {
        createEmptyCsvFile();
        List<Employee> result = Main.parseCSV(columnMapping, invalidFileCsvTest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Проверка обработки дубликатов")
    public void csvFileDuplicateEntriesTest() throws IOException {
        createDuplicateCsvFile();
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
    }

    private void createCsvFile() throws IOException {
        try (FileWriter writer = new FileWriter(fileCsvTest)) {
            writer.write("1,John,Smith,USA,25\n");
            writer.write("2,Ivan,Petrov,RU,23\n");
            writer.write("3,Irina,Smirnova,RU,19\n");
        }
    }

    private void createEmptyCsvFile() throws IOException {
        try (FileWriter writer = new FileWriter(invalidFileCsvTest)) {
        }
    }

    private void createDuplicateCsvFile() throws IOException {
        try (FileWriter writer = new FileWriter(fileCsvTest)) {
            writer.write("1,John,Smith,USA,25\n");
            writer.write("2,Ivan,Petrov,RU,23\n");
            writer.write("2,Ivan,Petrov,RU,23\n");
            writer.write("4,Irina,Smirnova,RU,19\n");
        }
    }
}

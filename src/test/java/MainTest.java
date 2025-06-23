import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainTest {
    private static final String fileCsvTest = "test_data.csv";
    private static final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

    @BeforeEach
    public void setUp() throws IOException {
        createTestCsvFile();
    }

    private void createTestCsvFile() throws IOException {
        try (FileWriter writer = new FileWriter(fileCsvTest)) {
            writer.write("1,John,Smith,USA,25\n");
            writer.write("2,Ivan,Petrov,RU,23\n");
            writer.write("3,Irina,Smirnova,RU,19\n");
        }
    }

    @Test
    @DisplayName("Проверка создания 3-х файлов")
    public void csvFileCreateTest() {
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Проверка данных первого пользователя")
    public void csvFileOneTest() {
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Employee firstEmployee = result.get(0);
        Assertions.assertEquals(1, firstEmployee.id);
        Assertions.assertEquals("John", firstEmployee.firstName);
        Assertions.assertEquals("Smith", firstEmployee.lastName);
        Assertions.assertEquals("USA", firstEmployee.country);
        Assertions.assertEquals(25, firstEmployee.age);
    }

    @Test
    @DisplayName("Проверка данных второго пользователя")
    public void csvFileTwoTest() {
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Employee firstEmployee = result.get(1);
        Assertions.assertEquals(2, firstEmployee.id);
        Assertions.assertEquals("Ivan", firstEmployee.firstName);
        Assertions.assertEquals("Petrov", firstEmployee.lastName);
        Assertions.assertEquals("RU", firstEmployee.country);
        Assertions.assertEquals(23, firstEmployee.age);
    }

    @Test
    @DisplayName("Проверка данных третьего пользователя")
    public void csvFileThreeTest() {
        List<Employee> result = Main.parseCSV(columnMapping, fileCsvTest);

        Employee firstEmployee = result.get(2);
        Assertions.assertEquals(3, firstEmployee.id);
        Assertions.assertEquals("Irina", firstEmployee.firstName);
        Assertions.assertEquals("Smirnova", firstEmployee.lastName);
        Assertions.assertEquals("RU", firstEmployee.country);
        Assertions.assertEquals(19, firstEmployee.age);
    }
}

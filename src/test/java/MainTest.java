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
    @DisplayName("Тест метода toString()")
    public void toStringTest() {
        Employee employee = new Employee(2, "Ivan", "Petrov", "RU", 23);
        String expected = "Employee{id=2, firstName='Ivan', lastName='Petrov', country='RU', age=23}";

        Assertions.assertEquals(expected, employee.toString());
    }
}

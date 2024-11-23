import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ФИО (через пробел): ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Введите дату рождения (дд.мм.гггг или дд/мм/гггг): ");
        String birthDateInput = scanner.nextLine().trim();

        String[] nameParts = fullName.split("\\s+");
        if (nameParts.length != 3) {
            System.out.println("Ошибка: ФИО должно содержать три части.");
            return;
        }

        String lastName = nameParts[0];
        String firstName = nameParts[1];
        String patronymic = nameParts[2];

        String gender = determineGender(patronymic);

        int age = calculateAge(birthDateInput);

        String initials = String.format("%s %s.%s.", lastName, firstName.charAt(0), patronymic.charAt(0));
        String ageSuffix = getAgeSuffix(age);

        System.out.println("Инициалы: " + initials);
        System.out.println("Пол: " + gender);
        System.out.println("Возраст: " + age + " " + ageSuffix);
    }

    private static String determineGender(String patronymic) {
        if (patronymic.endsWith("ич")) {
            return "Мужской";
        } else if (patronymic.endsWith("на")) {
            return "Женский";
        } else {
            return "ОПРЕДЕЛИТЬ_НЕ_УДАЛОСЬ";
        }
    }

    private static int calculateAge(String birthDateInput) {
        try {
            birthDateInput = birthDateInput.replace("/", ".");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthDate = LocalDate.parse(birthDateInput, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты.");
            System.exit(1);
            return -1; // Никогда не достигнет этого
        }
    }

    private static String getAgeSuffix(int age) {
        if (age % 10 == 1 && age % 100 != 11) {
            return "год";
        } else if (age % 10 >= 2 && age % 10 <= 4 && (age % 100 < 10 || age % 100 >= 20)) {
            return "года";
        } else {
            return "лет";
        }
    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double frequency;

    public Toy(int id, String name, int quantity, double frequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}

public class mystore {
    private static List<Toy> toys = new ArrayList<>();
    private static List<Toy> prizeToys = new ArrayList<>();

    public static void main(String[] args) {
        // Создаем несколько игрушек для примера
        toys.add(new Toy(1, "Лего", 15, 20));
        toys.add(new Toy(2, "Трансформер", 10, 25));
        toys.add(new Toy(3, "Ролики", 4, 25));
        toys.add(new Toy(4, "Тобот", 8, 10));
        toys.add(new Toy(5, "Бионики", 10, 20));

        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        int drawCounter = 0;
        int totalToyQuantity = 0; // Переменная для хранения суммарного количества игрушек

        while (playAgain) {
            drawCounter++;

            // Выводим список игрушек в магазине и суммируем количество игрушек
            System.out.println("Игрушки в магазине:");
            for (Toy toy : toys) {
                System.out.println("Игрушка " + toy.getId() +
                        ", Название: " + toy.getName() +
                        ", Количество: " + toy.getQuantity() +
                        ", Частота выпадения: " + toy.getFrequency() + "%");
                totalToyQuantity += toy.getQuantity();
            }

            // Получаем количество игрушек для розыгрыша
            System.out.print("Введите количество игрушек для розыгрыша: ");
            int numToys = scanner.nextInt();

            // Проверяем, что количество игрушек для розыгрыша не больше, чем общее количество доступных игрушек
            if (numToys > totalToyQuantity) {
                System.out.println("Ошибка! Количество игрушек для розыгрыша не может быть больше, чем общее количество игрушек в магазине.");
                return;
            }

            // Розыгрыш игрушек
            List<Toy> winningToys = new ArrayList<>();
            Random random = new Random();
            System.out.println("Выигрышные игрушки:");

            while (winningToys.size() < numToys) {
                // Получение суммы частот выпадения всех игрушек
                double totalFrequency = 0;
                for (Toy toy : toys) {
                    totalFrequency += toy.getFrequency();
                }

                // Генерация случайного числа в диапазоне от 0 до суммы частот выпадения
                double randomNumber = random.nextDouble() * totalFrequency;

                // Выбор игрушки на основе сгенерированного числа и частоты выпадения каждой игрушки
                double cumulativeFrequency = 0;
                for (Toy toy : toys) {
                    cumulativeFrequency += toy.getFrequency();
                    if (randomNumber <= cumulativeFrequency && toy.getQuantity() > 0) {
                        winningToys.add(toy);
                        toy.setQuantity(toy.getQuantity() - 1); // Уменьшаем количество игрушек в магазине
                        totalToyQuantity--; // Уменьшаем суммарное количество игрушек
                        break;
                    }
                }
            }

            // Выводим выигрышные игрушки
            for (Toy toy : winningToys) {
                System.out.println(toy.getName());
            }

            // Записываем выигрышные игрушки в файл
            try (FileWriter writer = new FileWriter("winning_toys.txt", true)) {
                writer.write("Розыгрыш №" + drawCounter + "\n");
                for (Toy toy : winningToys) {
                    writer.write(toy.getName() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Выводим список оставшихся игрушек
            System.out.println("Оставшиеся игрушки:");
            for (Toy toy : toys) {
                System.out.println("Игрушка " + toy.getId() +
                        ", Название: " + toy.getName() +
                        ", Количество: " + toy.getQuantity() +
                        ", Частота выпадения: " + toy.getFrequency() + "%");
            }

            // Проверяем желание пользователя продолжить розыгрыш
            System.out.print("Желаете продолжить розыгрыш? (Да/Нет) ");
            String answer = scanner.next();
            playAgain = answer.equalsIgnoreCase("Да");
        }
    }
}

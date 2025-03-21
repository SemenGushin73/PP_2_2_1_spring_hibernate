package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User user1 = new User("Maria", "Ilina", "maria@mail.ru");
        Car car1 = new Car("LADA", 6);
        user1.setCar(car1);

        User user2 = new User("Vladislav", "Sidorov", "vlad@mail.ru");
        Car car2 = new Car("KAMAZ", 10);
        user2.setCar(car2);

        User user3 = new User("John", "Marston", "john@mail.com");
        Car car3 = new Car("Mustang", 666);
        user3.setCar(car3);

        userService.add(user1);
        userService.add(user2);
        userService.add(user3);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar().getModel() + " (Series: " + user.getCar().getSeries() + ")");
            System.out.println();
        }

        String model = "KAMAZ";
        int series = 10;
        User foundUser = userService.findUserByCar(model, series);
        if (foundUser != null) {
            System.out.println("Найден владелец машины " + model + " серии " + series + ": " + foundUser.getFirstName() + " " + foundUser.getLastName());
        } else {
            System.out.println("Владелец машины " + model + " серии " + series + " не найден.");
        }

        context.close();
    }
}
package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {

      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();     //получение данных
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }


      //4. Создайте несколько пользователей с машинами, добавьте их в базу данных, вытащите обратно.
      User user5 = new User("User5", "Lastname5", "user5@mail.ru");
      user5.setCar(new Car("Model1", 1));
      User user6 = new User("User6", "Lastname6", "user6@mail.ru");
      user6.setCar(new Car("Model1", 2));
      User user7 = new User("User7", "Lastname7", "user7@mail.ru");
      user7.setCar(new Car("Model2", 1));
      userService.add(user5);
      userService.add(user6);
      userService.add(user7);

      users = userService.listUsers();
      for (User user : users) {

         if(user.getCar() != null) {
            System.out.println("Id = "+user.getId());
            System.out.println("First Name = "+user.getFirstName());
            System.out.println("Last Name = "+user.getLastName());
            System.out.println("Email = "+user.getEmail());
            System.out.println("CarModel = " + user.getCar().getModel());
            System.out.println("CarSeries = " + user.getCar().getSeries());
            System.out.println();
         }
      }

      //5. В сервис добавьте метод, который с помощью hql-запроса будет доставать юзера, владеющего машиной по ее модели и серии.
      String model = "Model2";
      int series = 1;
      User user = userService.getUserByCar(model, series);

      if(user!=null) {
         System.out.println("Автомобиль моделит " + model + "и серии" + series + "у Юзера " + user.getLastName());
      } else {
         System.out.println("Нет таких машин");
      }

      context.close();
   }
}


/*
CREATE  TABLE cars (
    id bigint not null auto_increment,
    model varchar(100),
    series integer,
    PRIMARY KEY (id),
    foreign key (id) references users(id)
);
* */

package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl test = new UserServiceImpl();
        test.createUsersTable();
        test.saveUser("Igor", "Krutoy", (byte) 55);
        test.saveUser("Irina", "Logunova", (byte) 33);
        test.saveUser("Goga", "Buriashwilli", (byte) 28);
        test.saveUser("Alex", "Pavlov", (byte) 31);
        List<User> userList = test.getAllUsers();
        userList.stream().forEach(System.out::println);
        test.createUsersTable();
        test.dropUsersTable();
    }
}

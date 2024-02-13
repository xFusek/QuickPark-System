package pl.kul.quickparksystem.service;

import pl.kul.quickparksystem.model.UserModel;
import java.io.*;

public class UserService {
    private static final String USER_FILE = "database/users.txt";

    public void saveUser(UserModel user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getFirstName() + "," + user.getLastName());
            writer.newLine();
        }
    }

    public boolean checkUserExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserModel getUserDetails(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username)) {
                    UserModel user = new UserModel();
                    user.setUsername(userDetails[0]);
                    user.setFirstName(userDetails[2]);
                    user.setLastName(userDetails[3]);
                    return user;
                }
            }
        }
        return null; // Returns null if user not found
    }

    public boolean checkPassword(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

}

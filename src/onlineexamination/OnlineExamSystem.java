/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlineexamination;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author kali-i
 */
public class OnlineExamSystem {
    private static ArrayList<User> users = new ArrayList<>();
   public ArrayList<Question> questions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    public OnlineExamSystem() {
        loadUsersFromFile("users.txt");
        loadQuestionsFromFile("questions.txt");
    }
    
    public int getQuestionSize(){
        return questions.size();
    }
   

    static void loadUsersFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNext()) {
                String username = fileScanner.next();
                String password = fileScanner.next();
                users.add(new User(username, password));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

     void loadQuestionsFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String questionText = fileScanner.nextLine();
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = fileScanner.nextLine();
                }
                int correctOption = Integer.parseInt(fileScanner.nextLine().split(" ")[1]);
                questions.add(new Question(questionText, options, correctOption));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void saveUsersToFile(String filename) {
         File file = new File(filename);
         if (file.exists()) {
            file.delete();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (User user : users) {
                writer.println(user.username + " " + user.password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     boolean registerUser(String username,String password) {
        users.add(new User(username, password));
        saveUsersToFile("users.txt");
        return true;
    }

    static void modifyUser(String NewUser,String newPass) {
       for (User user : users) {
        if (user.username.equals(Login.user)) {
            user.username = NewUser;
            user.password = newPass;
            saveUsersToFile("users.txt");
           
        }
       }
          JOptionPane.showMessageDialog(
            null,                   
            "User Modified Succesfully", 
            "Done",          // Dialog title
            JOptionPane.INFORMATION_MESSAGE
        );

    }
      boolean login(String username, String password) {

         for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                return true;
            }
        }
         return false;
    }
}
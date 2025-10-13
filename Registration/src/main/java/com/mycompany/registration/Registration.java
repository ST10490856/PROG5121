/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Karabo Masello Thekiso-ST10490856
 */
public class Registration {//start of class
    
     private String registeredUser;
    private String registeredPassword;
    private String registeredPhone;
    private ArrayList<String> recipientPhone = new ArrayList<>();
    private ArrayList<String> hashID = new ArrayList<>();
    private ArrayList<String> disregardMessage = new ArrayList<>();
    private ArrayList<String> storeMessage = new ArrayList<>();
    private ArrayList<String> sentMessage = new ArrayList<>();
    private HashSet<String> uniqueMessageID = new HashSet<>();
    private Random random = new Random();
    
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPassword(String password) {
        if (password == null) return false;
        // Fixed regex pattern
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_+=])(?=\\S+$).{8,}$");
    }
    
    public boolean checkCellNumber(String cellphone) {
        if (cellphone == null) return false;
        return cellphone.matches("^(\\+27|0)[6-8][0-9]{8}$");
    }
    
    public void signUp() {
        String userName;
        String userPassword;
        String userCellNumber;
        
        // Username loop
        do {
            userName = JOptionPane.showInputDialog("Enter your username");
            if (userName == null) return;
            
            if (!checkUserName(userName)) {
                JOptionPane.showMessageDialog(null, 
                    "Username must contain '_' and be max 5 characters");
            }
        } while (!checkUserName(userName));
        registeredUser = userName;
        
        // Password loop
        do {
            userPassword = JOptionPane.showInputDialog("Enter password");
            if (userPassword == null) return;
            
            if (!checkPassword(userPassword)) {
                JOptionPane.showMessageDialog(null, 
                    "Password must have 8+ chars, uppercase, lowercase, number, and special character");
            }
        } while (!checkPassword(userPassword));
        registeredPassword = userPassword;
        
        // Cellphone loop
        do {
            userCellNumber = JOptionPane.showInputDialog("Enter cellphone number");
            if (userCellNumber == null) return;
            
            if (!checkCellNumber(userCellNumber)) {
                JOptionPane.showMessageDialog(null, "Invalid SA cellphone number format");
            }
        } while (!checkCellNumber(userCellNumber));
        registeredPhone = userCellNumber;
        
        JOptionPane.showMessageDialog(null, "Registration successful!");
    }
    
    public void signIn() {
        if (registeredUser == null) {
            JOptionPane.showMessageDialog(null, "Please sign up first");
            return;
        }
        
        String userName = JOptionPane.showInputDialog("Enter username to login");
        if (userName == null) return;
        
        String userPassword = JOptionPane.showInputDialog("Enter password to login");
        if (userPassword == null) return;
        
        if (userName.equals(registeredUser) && userPassword.equals(registeredPassword)) {
            String[] options = {"Send message", "Coming soon", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                null, "Welcome to Chat App", "Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            switch (choice) {
                case 0: sendMessage(); break;
                case 1: JOptionPane.showMessageDialog(null, "Coming soon"); break;
                case 2: System.exit(0); break;
                default: break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials");
        }
    }
    
    public void sendMessage() {
        String recipientNumber;
        do {
            recipientNumber = JOptionPane.showInputDialog("Enter recipient number");
            if (recipientNumber == null) return;
            
            if (!checkCellNumber(recipientNumber)) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number");
            }
        } while (!checkCellNumber(recipientNumber));
        
        String messageCountStr = JOptionPane.showInputDialog("How many messages?");
        if (messageCountStr == null) return;
        
        try {
            int messageCount = Integer.parseInt(messageCountStr);
            for (int i = 0; i < messageCount; i++) {
                processSingleMessage(recipientNumber, i + 1);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number");
        }
        
        printMessages();
        JOptionPane.showMessageDialog(null, "Total sent: " + returnTotalMessages());
    }
    
    private void processSingleMessage(String recipientNumber, int messageIndex) {
        String message = JOptionPane.showInputDialog("Enter message " + messageIndex);
        if (message == null) return;
        
        if (message.length() > 250) {
            JOptionPane.showMessageDialog(null, "Message too long (max 250 chars)");
            return;
        }
        
        String[] options = {"Send", "Store", "Disregard"};
        int action = JOptionPane.showOptionDialog(
            null, "Choose action for this message", "Message Action",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]
        );
        
        if (action == -1) return;
        
        String messageId = generateMessageId();
        
        switch (action) {
            case 0: // Send
                sentMessage.add(message);
                recipientPhone.add(recipientNumber);
                uniqueMessageID.add(messageId);
                String hash = createMessageHash(messageId, sentMessage.size(), message);
                hashID.add(hash);
                
                String details = String.format(
                    "Message Sent!\nID: %s\nHash: %s\nTo: %s\nMessage: %s",
                    messageId, hash, recipientNumber, message
                );
                JOptionPane.showMessageDialog(null, details);
                break;
                
            case 1: // Store
                storeMessage.add(message);
                JOptionPane.showMessageDialog(null, "Message stored");
                break;
                
            case 2: // Disregard
                disregardMessage.add(message);
                JOptionPane.showMessageDialog(null, "Message disregarded");
                break;
        }
    }
    
    private String generateMessageId() {
        String id;
        do {
            int firstNum = 1 + random.nextInt(9);
            int remainingNum = random.nextInt(1_000_000_000);
            id = firstNum + String.format("%09d", remainingNum);
        } while (uniqueMessageID.contains(id));
        return id;
    }
    
    private String createMessageHash(String messageId, int messageNum, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return messageId.substring(0, 2) + ":" + messageNum + ":" + firstWord + lastWord;
    }
    
    public int returnTotalMessages() {
        return sentMessage.size();
    }
    
    public void printMessages() {
        if (sentMessage.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent");
            return;
        }
        
        StringBuilder sb = new StringBuilder("*** SENT MESSAGES ***\n\n");
        for (int i = 0; i < sentMessage.size(); i++) {
            sb.append("Message ").append(i + 1).append(":\n")
              .append("Hash: ").append(hashID.get(i)).append("\n")
              .append("ID: ").append(new ArrayList<>(uniqueMessageID).get(i)).append("\n")
              .append("To: ").append(recipientPhone.get(i)).append("\n")
              .append("Content: ").append(sentMessage.get(i)).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    public static void main(String[] args) {
        Registration app = new Registration();
        boolean running = true;
        
        while (running) {
            String[] options = {"Sign Up", "Sign In", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                null, "Welcome to Chat App", "Main Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            if (choice == -1 || choice == 2) {
                running = false;
                JOptionPane.showMessageDialog(null, "Goodbye!");
            } else if (choice == 0) {
                app.signUp();
            } else if (choice == 1) {
                app.signIn();
            }
        }
    }
}//end of class

    



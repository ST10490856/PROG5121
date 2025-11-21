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
public class Registration {

    

    private String registeredUser;
    private String registeredPassword;
    private String registeredPhone;
    
    // Part 3: Required arrays
    private final ArrayList<String> recipientPhone = new ArrayList<>();
    private final ArrayList<String> hashID = new ArrayList<>();
    private final ArrayList<String> disregardMessage = new ArrayList<>();
    private final ArrayList<String> storeMessage = new ArrayList<>();
    private final ArrayList<String> sentMessage = new ArrayList<>();
    private final ArrayList<String> messageIDs = new ArrayList<>(); // Added for Part 3
    
    private HashSet<String> uniqueMessageID = new HashSet<>();
    private final Random random = new Random();
    
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPassword(String password) {
        if (password == null) return false;
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
            userName = JOptionPane.showInputDialog(null, 
                "Enter your username:\n- Must contain '_'\n- Maximum 5 characters\n\nExample: kyl_1", 
                "Create username", JOptionPane.PLAIN_MESSAGE);
            if (userName == null) return;
            
            if (!checkUserName(userName)) {
                JOptionPane.showMessageDialog(null, 
                    "Username must contain '_' and be maximum 5 characters\n\nExample: kyl_1", 
                    "Invalid Username", JOptionPane.ERROR_MESSAGE);
            }
        } while (!checkUserName(userName));
        registeredUser = userName;
        
        // Password loop
        do {
            userPassword = JOptionPane.showInputDialog(null, 
                "Enter password:\n- At least 8 characters\n- Uppercase letter\n- Lowercase letter\n- Number\n- Special character\n\nExample: Password123!", 
                "Create password", JOptionPane.PLAIN_MESSAGE);
            if (userPassword == null) return;
            
            if (!checkPassword(userPassword)) {
                JOptionPane.showMessageDialog(null, 
                    "Password must have:\n- 8+ characters\n- Uppercase letter\n- Lowercase letter\n- Number\n- Special character\n\nExample: Password123!", 
                    "Invalid Password", JOptionPane.ERROR_MESSAGE);
            }
        } while (!checkPassword(userPassword));
        registeredPassword = userPassword;
        
        // Cellphone loop
        do {
            userCellNumber = JOptionPane.showInputDialog(null, 
                "Enter cellphone number:\n- South African format\n- Start with +27 or 0\n\nExample: +27831234567 or 0831234567", 
                "Create number", JOptionPane.PLAIN_MESSAGE);
            if (userCellNumber == null) return;
            
            if (!checkCellNumber(userCellNumber)) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid SA cellphone number format\n\nMust be:\n- +27 followed by 9 digits\nOR\n- 0 followed by 9 digits\n\nExample: +27831234567 or 0831234567", 
                    "Invalid Number", JOptionPane.ERROR_MESSAGE);
            }
        } while (!checkCellNumber(userCellNumber));
        registeredPhone = userCellNumber;
        
        JOptionPane.showMessageDialog(null, 
            "Registration Successful!\n\nUsername: " + registeredUser + "\nPhone: " + registeredPhone, 
            "Successful", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void signIn() {
        if (registeredUser == null) {
            JOptionPane.showMessageDialog(null, "Please sign up first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String userName = JOptionPane.showInputDialog(null, "Enter username to login", "Login", JOptionPane.PLAIN_MESSAGE);
        if (userName == null) return;
        
        String userPassword = JOptionPane.showInputDialog(null, "Enter password to login", "Login", JOptionPane.PLAIN_MESSAGE);
        if (userPassword == null) return;
        
        if (userName.equals(registeredUser) && userPassword.equals(registeredPassword)) {
            JOptionPane.showMessageDialog(null, "Welcome back, " + registeredUser + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showMainMenu() {
        boolean inMenu = true;
        while (inMenu) {
            String[] options = {"Send Message", "View Messages", "Message Management", "Account Info", "Logout"};
            int choice = JOptionPane.showOptionDialog(
                null, 
                "Chat Application - Main Menu\n\nWelcome, " + registeredUser + "!\nWhat would you like to do?",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            switch (choice) {
                case 0: 
                    sentMessage(); 
                    break;
                case 1: 
                    printMessages(); 
                    break;
                case 2:
                    showMessageManagementMenu();
                    break;
                case 3: 
                    showAccountInfo(); 
                    break;
                case 4: 
                case -1:
                    inMenu = false;
                    JOptionPane.showMessageDialog(null, "Goodbye, " + registeredUser + "!", "Logged Out", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }
    
    // PART 3: Message Management Menu
    private void showMessageManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            String[] options = {
                "Populate Test Data", 
                "Display Longest Message", 
                "Search by Message ID",
                "Search by Recipient", 
                "Delete by Hash", 
                "Display Full Report",
                "Back to Main Menu"
            };
            
            int choice = JOptionPane.showOptionDialog(
                null, 
                """
                Message Management System
                
                Total Messages: """ + getTotalMessageCount() + "\n" +
                "Sent: " + sentMessage.size() + " | Stored: " + storeMessage.size() + " | Disregarded: " + disregardMessage.size(),
                "Message Management",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            switch (choice) {
                case 0:
                    populateTestData();
                    break;
                case 1:
                    displayLongestMessage();
                    break;
                case 2:
                    searchByMessageID();
                    break;
                case 3:
                    searchByRecipient();
                    break;
                case 4:
                    deleteByMessageHash();
                    break;
                case 5:
                    displayFullReport();
                    break;
                case 6:
                case -1:
                    inMenu = false;
                    break;
            }
        }
    }
    
    private void showAccountInfo() {
        String accountInfo = """
                             *** ACCOUNT INFORMATION ***
                             
                             Username: """ + registeredUser + "\n" +
            "Phone: " + registeredPhone + "\n" +
            "Total Messages Sent: " + sentMessage.size() + "\n" +
            "Total Messages Stored: " + storeMessage.size() + "\n" +
            "Total Messages Disregarded: " + disregardMessage.size();
        
        JOptionPane.showMessageDialog(null, accountInfo, "Your Account", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void sentMessage() {
        String recipientNumber;
        do {
            recipientNumber = JOptionPane.showInputDialog(null,
                "Enter recipient number:\n\nMust be South African format:\n- +27831234567\n- 0831234567", 
                "Enter recipient number", JOptionPane.PLAIN_MESSAGE);
            if (recipientNumber == null) return;
            
            if (!checkCellNumber(recipientNumber)) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid recipient number format\n\nMust be:\n- +27 followed by 9 digits\nOR\n- 0 followed by 9 digits", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!checkCellNumber(recipientNumber));
        
        String messageCountStr = JOptionPane.showInputDialog(null, 
            "How many messages would you like to send?", "Message Count", JOptionPane.QUESTION_MESSAGE);
        if (messageCountStr == null) return;
        
        try {
            int messageCount = Integer.parseInt(messageCountStr);
            if (messageCount <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int successfulMessages = 0;
            for (int i = 0; i < messageCount; i++) {
                if (processSingleMessage(recipientNumber, i + 1)) {
                    successfulMessages++;
                }
            }
            
            JOptionPane.showMessageDialog(null, 
                "Operation completed!\nSuccessfully processed: " + successfulMessages + " out of " + messageCount + " messages", 
                "Summary", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean processSingleMessage(String recipientNumber, int messageIndex) {
        String message = JOptionPane.showInputDialog("Enter message " + messageIndex + ":");
        if (message == null) return false;
        
        if (message.length() > 250) {
            JOptionPane.showMessageDialog(null, 
                "Message too long! Maximum 250 characters.\n\nYour message: " + message.length() + " characters", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String[] options = {"Send", "Store", "Disregard", "Cancel"};
        int action = JOptionPane.showOptionDialog(
            null, 
            "Choose action for message " + messageIndex + ":\n\n\"" + message + "\"",
            "Message Action",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]
        );
        
        if (action == -1 || action == 3) return false;
        
        String messageId = generateMessageId();
        
        switch (action) {
            case 0 -> {
                // Send
                sentMessage.add(message);
                recipientPhone.add(recipientNumber);
                uniqueMessageID.add(messageId);
                messageIDs.add(messageId); // For Part 3
                String hash = createMessageHash(messageId, sentMessage.size(), message);
                hashID.add(hash);
                
                String details = String.format(
                    "✅ Message Sent Successfully!\n\n📱 To: %s\n📝 Message: %s\n🆔 ID: %s\n🔐 Hash: %s",
                    recipientNumber, message, messageId, hash
                );
                JOptionPane.showMessageDialog(null, details, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
                
            case 1 -> {
                // Store
                storeMessage.add(message);
                recipientPhone.add(recipientNumber); // Store recipient for search functionality
                messageIDs.add(messageId);
                String hash = createMessageHash(messageId, storeMessage.size(), message);
                hashID.add(hash);
                JOptionPane.showMessageDialog(null, 
                    "💾 Message stored successfully!\n\nMessage: " + message, 
                    "Stored", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
                
            case 2 -> {
                // Disregard
                disregardMessage.add(message);
                recipientPhone.add(recipientNumber); // Store recipient for consistency
                messageIDs.add(messageId);
                String hash = createMessageHash(messageId, disregardMessage.size(), message);
                hashID.add(hash);
                JOptionPane.showMessageDialog(null, 
                    "🗑️ Message disregarded\n\nMessage: " + message, 
                    "Disregarded", JOptionPane.WARNING_MESSAGE);
                return true;
            }
                
            default -> {
                return false;
            }
        }
    }
    
    private String generateMessageId() {
        String id;
        do {
            int firstNum = 1 + random.nextInt(9);
            int remainingNum = random.nextInt(1_000_000_000);
            id = firstNum + String.format("%09d", remainingNum);
        } while (uniqueMessageID.contains(id));
        uniqueMessageID.add(id);
        return id;
    }
    
    private String createMessageHash(String messageId, int messageNum, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Enhanced hash with more information
        return messageId.substring(0, 2) + ":" + messageNum + ":" + 
               firstWord.substring(0, Math.min(firstWord.length(), 3)) + ":" + 
               lastWord.substring(0, Math.min(lastWord.length(), 3));
    }
    
    public int returnTotalMessages() {
        return sentMessage.size();
    }
    
    // PART 3: Get total message count across all arrays
    private int getTotalMessageCount() {
        return sentMessage.size() + storeMessage.size() + disregardMessage.size();
    }
    
    public void printMessages() {
        if (sentMessage.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No messages sent yet.\n\nSend your first message using the 'Send Message' option!", 
                "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("*** YOUR SENT MESSAGES ***\n\n");
        for (int i = 0; i < sentMessage.size(); i++) {
            sb.append("📱 Message ").append(i + 1).append(":\n")
              .append("   To: ").append(recipientPhone.get(i)).append("\n")
              .append("   ID: ").append(messageIDs.get(i)).append("\n")
              .append("   Hash: ").append(hashID.get(i)).append("\n")
              .append("   Content: ").append(sentMessage.get(i)).append("\n\n");
        }
        
        sb.append("--- SUMMARY ---\n")
          .append("Total Sent: ").append(sentMessage.size()).append("\n")
          .append("Total Stored: ").append(storeMessage.size()).append("\n")
          .append("Total Disregarded: ").append(disregardMessage.size());
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Your Messages", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // =========================================================================
    // PART 3: IMPLEMENTATION OF REQUIRED FEATURES
    // =========================================================================
    
    /**
     * PART 3a: Populate arrays with test data
     */
    private void populateTestData() {
        // Clear existing data first
        sentMessage.clear();
        storeMessage.clear();
        disregardMessage.clear();
        recipientPhone.clear();
        hashID.clear();
        messageIDs.clear();
        uniqueMessageID.clear();
        
        // Test Data Message 1: Sent
        addTestMessage("+27834557896", "Did you get the cake?", "Sent", "1000000001");
        
        // Test Data Message 2: Stored
        addTestMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored", "1000000002");
        
        // Test Data Message 3: Disregarded
        addTestMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard", "1000000003");
        
        // Test Data Message 4: Sent
        addTestMessage("0838884567", "It is dinner time!", "Sent", "1000000004");
        
        // Test Data Message 5: Stored
        addTestMessage("+27838884567", "Ok, I am leaving without you.", "Stored", "1000000005");
        
        JOptionPane.showMessageDialog(null, """
                                            \u2705 Test data populated successfully!
                                            
                                            \u2022 5 sample messages added
                                            \u2022 2 Sent messages
                                            \u2022 2 Stored messages
                                            \u2022 1 Disregarded message""",
            "Test Data Loaded", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void addTestMessage(String recipient, String message, String type, String messageId) {
        recipientPhone.add(recipient);
        messageIDs.add(messageId);
        uniqueMessageID.add(messageId);
        String hash = createMessageHash(messageId, recipientPhone.size(), message);
        hashID.add(hash);
        
        switch (type) {
            case "Sent" -> sentMessage.add(message);
            case "Stored" -> storeMessage.add(message);
            case "Disregard" -> disregardMessage.add(message);
        }
    }
    
    /**
     * PART 3b: Display the longest sent message
     */
    private void displayLongestMessage() {
        if (getTotalMessageCount() == 0) {
            JOptionPane.showMessageDialog(null, "No messages available.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Combine all messages to find the longest
        ArrayList<String> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessage);
        allMessages.addAll(storeMessage);
        allMessages.addAll(disregardMessage);
        
        String longestMessage = "";
        for (String message : allMessages) {
            if (message.length() > longestMessage.length()) {
                longestMessage = message;
            }
        }
        
        JOptionPane.showMessageDialog(null,
            "📏 LONGEST MESSAGE\n\n" +
            "Message: \"" + longestMessage + "\"\n" +
            "Length: " + longestMessage.length() + " characters\n\n" +
            "Found in: " + 
            (sentMessage.contains(longestMessage) ? "Sent Messages" : 
             storeMessage.contains(longestMessage) ? "Stored Messages" : "Disregarded Messages"),
            "Longest Message", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * PART 3c: Search for a message ID and display the corresponding recipient and message
     */
    private void searchByMessageID() {
        if (messageIDs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to search.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchId = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (searchId == null || searchId.trim().isEmpty()) return;
        
        searchId = searchId.trim();
        
        // Search for the message ID
        int index = -1;
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchId)) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            String messageContent = getMessageByIndex(index);
            String recipient = recipientPhone.get(index);
            String hash = hashID.get(index);
            
            String result = String.format("""
                                          \u2705 MESSAGE FOUND
                                          
                                          \ud83c\udd94 Message ID: %s
                                          \ud83d\udcf1 Recipient: %s
                                          \ud83d\udcdd Message: %s
                                          \ud83d\udd10 Hash: %s
                                          \ud83d\udcca Status: %s""",
                searchId, recipient, messageContent, hash, getMessageStatus(index)
            );
            
            JOptionPane.showMessageDialog(null, result, "Message Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Message ID not found: " + searchId + "\n\nAvailable IDs: " + messageIDs.toString(),
                "Not Found", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * PART 3d: Search for all the messages sent to a particular recipient
     */
    private void searchByRecipient() {
        if (recipientPhone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to search.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchRecipient = JOptionPane.showInputDialog("Enter recipient phone number to search:");
        if (searchRecipient == null || searchRecipient.trim().isEmpty()) return;
        
        searchRecipient = searchRecipient.trim();
        
        StringBuilder results = new StringBuilder();
        results.append("🔍 MESSAGES FOR: ").append(searchRecipient).append("\n\n");
        
        int foundCount = 0;
        
        for (int i = 0; i < recipientPhone.size(); i++) {
            if (recipientPhone.get(i).equals(searchRecipient)) {
                foundCount++;
                results.append("Message ").append(foundCount).append(":\n")
                      .append("  ID: ").append(messageIDs.get(i)).append("\n")
                      .append("  Hash: ").append(hashID.get(i)).append("\n")
                      .append("  Status: ").append(getMessageStatus(i)).append("\n")
                      .append("  Content: ").append(getMessageByIndex(i)).append("\n\n");
            }
        }
        
        if (foundCount > 0) {
            results.append("--- SUMMARY ---\n")
                  .append("Total messages found: ").append(foundCount);
            JOptionPane.showMessageDialog(null, results.toString(), "Recipient Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ No messages found for recipient: " + searchRecipient,
                "No Results", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * PART 3e: Delete a message using the message hash
     */
    private void deleteByMessageHash() {
        if (hashID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to delete.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchHash = JOptionPane.showInputDialog("Enter message hash to delete:");
        if (searchHash == null || searchHash.trim().isEmpty()) return;
        
        searchHash = searchHash.trim();
        
        int index = -1;
        for (int i = 0; i < hashID.size(); i++) {
            if (hashID.get(i).equals(searchHash)) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            String deletedMessage = getMessageByIndex(index);
            String messageId = messageIDs.get(index);
            
            // Remove from all arrays
            if (index < sentMessage.size() && sentMessage.get(index) != null) {
                sentMessage.set(index, "[DELETED]");
            }
            if (index < storeMessage.size() && storeMessage.get(index) != null) {
                storeMessage.set(index, "[DELETED]");
            }
            if (index < disregardMessage.size() && disregardMessage.get(index) != null) {
                disregardMessage.set(index, "[DELETED]");
            }
            
            hashID.set(index, "[DELETED]");
            
            JOptionPane.showMessageDialog(null,
                """
                \ud83d\uddd1\ufe0f MESSAGE DELETED SUCCESSFULLY
                
                Message ID: """ + messageId + "\n" +
                "Hash: " + searchHash + "\n" +
                "Content: \"" + deletedMessage + "\"",
                "Message Deleted", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Hash not found: " + searchHash,
                "Not Found", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * PART 3f: Display a report that lists the full details of all the sent messages
     */
    private void displayFullReport() {
        if (sentMessage.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages available for report.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder report = new StringBuilder();
        report.append("📊 FULL MESSAGE REPORT\n\n");
        report.append("=== SENT MESSAGES ===\n\n");
        
        for (int i = 0; i < sentMessage.size(); i++) {
            report.append("Message ").append(i + 1).append(":\n")
                  .append("  🔐 Hash: ").append(hashID.get(i)).append("\n")
                  .append("  📱 Recipient: ").append(recipientPhone.get(i)).append("\n")
                  .append("  🆔 ID: ").append(messageIDs.get(i)).append("\n")
                  .append("  📝 Message: ").append(sentMessage.get(i)).append("\n")
                  .append("  📏 Length: ").append(sentMessage.get(i).length()).append(" characters\n\n");
        }
        
        report.append("=== SUMMARY ===\n")
              .append("Total Sent Messages: ").append(sentMessage.size()).append("\n")
              .append("Total Stored Messages: ").append(storeMessage.size()).append("\n")
              .append("Total Disregarded Messages: ").append(disregardMessage.size()).append("\n")
              .append("Overall Total: ").append(getTotalMessageCount());
        
        JOptionPane.showMessageDialog(null, report.toString(), "Full Message Report", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // =========================================================================
    // PART 3: HELPER METHODS
    // =========================================================================
    
    /**
     * Helper method to get message content by index
     */
    private String getMessageByIndex(int index) {
        if (index < sentMessage.size() && sentMessage.get(index) != null && !sentMessage.get(index).equals("[DELETED]")) {
            return sentMessage.get(index);
        }
        if (index < storeMessage.size() && storeMessage.get(index) != null && !storeMessage.get(index).equals("[DELETED]")) {
            return storeMessage.get(index);
        }
        if (index < disregardMessage.size() && disregardMessage.get(index) != null && !disregardMessage.get(index).equals("[DELETED]")) {
            return disregardMessage.get(index);
        }
        return "[MESSAGE NOT AVAILABLE]";
    }
    
    /**
     * Helper method to get message status by index
     */
    private String getMessageStatus(int index) {
        if (index < sentMessage.size() && sentMessage.get(index) != null && !sentMessage.get(index).equals("[DELETED]")) {
            return "Sent";
        }
        if (index < storeMessage.size() && storeMessage.get(index) != null && !storeMessage.get(index).equals("[DELETED]")) {
            return "Stored";
        }
        if (index < disregardMessage.size() && disregardMessage.get(index) != null && !disregardMessage.get(index).equals("[DELETED]")) {
            return "Disregarded";
        }
        return "Deleted";
    }
    
    public static void main(String[] args) {
        Registration app = new Registration();
        boolean running = true;
        
        while (running) {
            String[] options = {"Sign Up", "Sign In", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                null, 
                "=== CHAT APPLICATION ===\n\nWelcome! Please choose an option:",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            switch (choice) {
                case 0:
                    app.signUp();
                    break;
                case 1:
                    app.signIn();
                    break;
                case 2:
                case -1:
                    running = false;
                    JOptionPane.showMessageDialog(null, 
                        "Thank you for using Chat Application!\nGoodbye!", 
                        "Exit", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }

    

    

}//end of class

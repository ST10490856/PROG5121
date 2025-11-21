/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManager {
    private final ArrayList<message> allMessages;
    private final ArrayList<String> sentMessages;
    private final ArrayList<String> storedMessages;
    private final ArrayList<String> disregardedMessages;
    private final ArrayList<String> messageHashes;
    private final ArrayList<String> messageIDs;
    private final ArrayList<String> recipientPhones;
    
    public MessageManager() {
        this.allMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.messageHashes = new ArrayList<>();
        this.messageIDs = new ArrayList<>();
        this.recipientPhones = new ArrayList<>();
    }
    
    /**
     * Adds a new message to the system
     * @param message
     * @param status
     * @return 
     */
    public boolean addMessage(message message, String status) {
        if (message == null || !message.isValid()) {
            return false;
        }
        
        allMessages.add(message);
        message.setStatus(status);
        
        // Add to appropriate array based on status
        switch (status.toLowerCase()) {
            case "sent":
                sentMessages.add(message.getMessageText());
                break;
            case "stored":
                storedMessages.add(message.getMessageText());
                break;
            case "disregarded":
                disregardedMessages.add(message.getMessageText());
                break;
        }
        
        // Add to tracking arrays
        messageIDs.add(message.getMessageId());
        recipientPhones.add(message.getRecipient());
        messageHashes.add(message.getMessageHash());
        
        return true;
    }
    
    /**
     * Processes a single message with user interaction
     * @param recipientNumber
     * @param messageIndex
     * @return 
     */
    public boolean processSingleMessage(String recipientNumber, int messageIndex) {
        String messageText = JOptionPane.showInputDialog("Enter message " + messageIndex + ":");
        if (messageText == null || messageText.trim().isEmpty()) {
            return false;
        }
        
        // Validate message length
        String validationResult = message.validateMessageLength(messageText);
        if (!validationResult.equals("Message ready to send.")) {
            JOptionPane.showMessageDialog(null, validationResult, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Generate message ID
        String messageId = generateMessageId();
        message message = new message(messageId, recipientNumber, messageText, messageIndex);
        
        // Ask user for action
        String[] options = {"Send", "Store", "Disregard", "Cancel"};
        int action = JOptionPane.showOptionDialog(
            null, 
            "Choose action for message " + messageIndex + ":\n\n\"" + messageText + "\"",
            "Message Action",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]
        );
        
        if (action == -1 || action == 3) {
            return false; // User cancelled
        }
        
        String status = "";
        String confirmationMessage = "";
        
        switch (action) {
            case 0: // Send
                status = "Sent";
                confirmationMessage = "✅ Message Sent Successfully!\n\n" + message.getFullDetails();
                break;
            case 1: // Store
                status = "Stored";
                confirmationMessage = "💾 Message stored successfully!\n\nMessage: " + messageText;
                break;
            case 2: // Disregard
                status = "Disregarded";
                confirmationMessage = "🗑️ Message disregarded\n\nMessage: " + messageText;
                break;
        }
        
        // Add message to system
        if (addMessage(message, status)) {
            JOptionPane.showMessageDialog(null, confirmationMessage, "Message Processed", 
                action == 0 ? JOptionPane.INFORMATION_MESSAGE : 
                action == 1 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
            return true;
        }
        
        return false;
    }
    
    /**
     * Generates a unique message ID
     */
    private String generateMessageId() {
        // Simple ID generation - in real system, use more sophisticated approach
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp).substring(5, 15); // Get last 10 digits
    }
    
    /**
     * PART 3: Populate with test data
     */
    public void populateTestData() {
        clearAllData();
        
        // Test Data from requirements
        addMessage(new message("1000000001", "+27834557896", "Did you get the cake?", 1), "Sent");
        addMessage(new message("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 2), "Stored");
        addMessage(new message("1000000003", "+27834484567", "Yohoooo, I am at your gate.", 3), "Disregarded");
        addMessage(new message("1000000004", "0838884567", "It is dinner time!", 4), "Sent");
        addMessage(new message("1000000005", "+27838884567", "Ok, I am leaving without you.", 5), "Stored");
        
        JOptionPane.showMessageDialog(null, """
                                            \u2705 Test data populated successfully!
                                            
                                            \u2022 5 sample messages loaded
                                            \u2022 2 Sent messages
                                            \u2022 2 Stored messages
                                            \u2022 1 Disregarded message""",
            "Test Data Loaded", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * PART 3b: Display the longest message
     */
    public void displayLongestMessage() {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        message longestMessage = allMessages.get(0);
        for (message msg : allMessages) {
            if (msg.getMessageText().length() > longestMessage.getMessageText().length()) {
                longestMessage = msg;
            }
        }
        
        String result;
        result = "📏 LONGEST MESSAGE\n\n" +
                "Message: \"" + longestMessage.getMessageText() + "\"\n" +
                "Length: " + longestMessage.getMessageText().length() + " characters\n" +
                "Recipient: " + longestMessage.getRecipient() + "\n" +
                "Status: " + longestMessage.getStatus() + "\n" +
                "Message ID: " + longestMessage.getMessageId();
        
        JOptionPane.showMessageDialog(null, result, "Longest Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * PART 3c: Search for a message by ID
     */
    public void searchByMessageID() {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to search.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchId = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (searchId == null || searchId.trim().isEmpty()) return;
        
        searchId = searchId.trim();
        message foundMessage = null;
        
        for (message msg : allMessages) {
            if (searchId.equals(msg.getMessageId())) {
                foundMessage = msg;
                break;
            }
        }
        
        if (foundMessage != null) {
            String result = """
                            \u2705 MESSAGE FOUND
                            
                            \ud83c\udd94 Message ID: """ + foundMessage.getMessageId() + "\n" +
                           "📱 Recipient: " + foundMessage.getRecipient() + "\n" +
                           "📝 Message: " + foundMessage.getMessageText() + "\n" +
                           "📊 Status: " + foundMessage.getStatus() + "\n" +
                           "🔐 Hash: " + foundMessage.getMessageHash();
            
            JOptionPane.showMessageDialog(null, result, "Message Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Message ID not found: " + searchId,
                "Not Found", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * PART 3d: Search for all messages by recipient
     */
    public void searchByRecipient() {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to search.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchRecipient = JOptionPane.showInputDialog("Enter recipient phone number to search:");
        if (searchRecipient == null || searchRecipient.trim().isEmpty()) return;
        
        searchRecipient = searchRecipient.trim();
        ArrayList<message> recipientMessages = new ArrayList<>();
        
        for (message msg : allMessages) {
            if (searchRecipient.equals(msg.getRecipient())) {
                recipientMessages.add(msg);
            }
        }
        
        if (recipientMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "❌ No messages found for recipient: " + searchRecipient,
                "No Results", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringBuilder result = new StringBuilder();
        result.append("🔍 MESSAGES FOR: ").append(searchRecipient).append("\n\n");
        
        for (int i = 0; i < recipientMessages.size(); i++) {
            message msg = recipientMessages.get(i);
            result.append("Message ").append(i + 1).append(":\n")
                  .append("  ID: ").append(msg.getMessageId()).append("\n")
                  .append("  Status: ").append(msg.getStatus()).append("\n")
                  .append("  Message: ").append(msg.getMessageText()).append("\n")
                  .append("  Hash: ").append(msg.getMessageHash()).append("\n\n");
        }
        
        result.append("--- SUMMARY ---\n")
              .append("Total messages found: ").append(recipientMessages.size());
              
        JOptionPane.showMessageDialog(null, result.toString(), "Recipient Search Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * PART 3e: Delete a message by hash
     */
    public void deleteByMessageHash() {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available to delete.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String searchHash = JOptionPane.showInputDialog("Enter message hash to delete:");
        if (searchHash == null || searchHash.trim().isEmpty()) return;
        
        searchHash = searchHash.trim();
        message deletedMessage = null;
        
        Iterator<message> iterator = allMessages.iterator();
        while (iterator.hasNext()) {
            message msg = iterator.next();
            if (searchHash.equals(msg.getMessageHash())) {
                deletedMessage = msg;
                iterator.remove();
                break;
            }
        }
        
        if (deletedMessage != null) {
            // Remove from other arrays
            messageIDs.remove(deletedMessage.getMessageId());
            recipientPhones.remove(deletedMessage.getRecipient());
            messageHashes.remove(searchHash);
            
            // Remove from status-specific arrays
            switch (deletedMessage.getStatus()) {
                case "Sent" -> sentMessages.remove(deletedMessage.getMessageText());
                case "Stored" -> storedMessages.remove(deletedMessage.getMessageText());
                case "Disregarded" -> disregardedMessages.remove(deletedMessage.getMessageText());
            }
            
            String result = """
                            \ud83d\uddd1\ufe0f MESSAGE DELETED SUCCESSFULLY
                            
                            Message ID: """ + deletedMessage.getMessageId() + "\n" +
                           "Hash: " + deletedMessage.getMessageHash() + "\n" +
                           "Recipient: " + deletedMessage.getRecipient() + "\n" +
                           "Message: " + deletedMessage.getMessageText() + "\n" +
                           "Total messages remaining: " + allMessages.size();
            
            JOptionPane.showMessageDialog(null, result, "Message Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "❌ Hash not found: " + searchHash,
                "Not Found", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * PART 3f: Display full report of all sent messages
     */
    public void displayFullReport() {
        if (allMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages available for report.", "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder report = new StringBuilder();
        report.append("📊 FULL MESSAGE REPORT\n\n");
        
        int sentCount = 0;
        int storedCount = 0;
        int disregardedCount = 0;
        
        for (int i = 0; i < allMessages.size(); i++) {
            message msg = allMessages.get(i);
            
            report.append("Message ").append(i + 1).append(":\n")
                  .append("  🔐 Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("  📱 Recipient: ").append(msg.getRecipient()).append("\n")
                  .append("  🆔 ID: ").append(msg.getMessageId()).append("\n")
                  .append("  📝 Message: ").append(msg.getMessageText()).append("\n")
                  .append("  📊 Status: ").append(msg.getStatus()).append("\n")
                  .append("  📏 Length: ").append(msg.getMessageText().length()).append(" characters\n\n");
            
            // Count by status
            switch (msg.getStatus()) {
                case "Sent" -> sentCount++;
                case "Stored" -> storedCount++;
                case "Disregarded" -> disregardedCount++;
            }
        }
        
        report.append("=== SUMMARY ===\n")
              .append("Total Messages: ").append(allMessages.size()).append("\n")
              .append("✅ Sent: ").append(sentCount).append("\n")
              .append("💾 Stored: ").append(storedCount).append("\n")
              .append("🗑️ Disregarded: ").append(disregardedCount).append("\n")
              .append("📈 Longest Message: ").append(findLongestMessage().length()).append(" characters");
        
        JOptionPane.showMessageDialog(null, report.toString(), "Full Message Report", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Display all sent messages
     */
    public void displaySentMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No sent messages available.\n\nSend your first message using the 'Send Message' option!", 
                "No Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder("📨 YOUR SENT MESSAGES\n\n");
        int sentIndex = 0;
        
        for (int i = 0; i < allMessages.size(); i++) {
            message msg = allMessages.get(i);
            if ("Sent".equals(msg.getStatus())) {
                sentIndex++;
                sb.append("Message ").append(sentIndex).append(":\n")
                  .append("  To: ").append(msg.getRecipient()).append("\n")
                  .append("  ID: ").append(msg.getMessageId()).append("\n")
                  .append("  Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("  Content: ").append(msg.getMessageText()).append("\n\n");
            }
        }
        
        sb.append("--- SUMMARY ---\n")
          .append("Total Sent: ").append(sentMessages.size()).append("\n")
          .append("Total Stored: ").append(storedMessages.size()).append("\n")
          .append("Total Disregarded: ").append(disregardedMessages.size());
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Your Messages", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Get message statistics
     * @return 
     */
    public String getStatistics() {
        int total = allMessages.size();
        if (total == 0) {
            return "No messages available for statistics.";
        }
        
        int sentCount = sentMessages.size();
        int storedCount = storedMessages.size();
        int disregardedCount = disregardedMessages.size();
        
        return """
               === MESSAGE STATISTICS ===
               Total Messages: """ + total + "\n" +
               "Sent: " + sentCount + " (" + String.format("%.1f", (sentCount * 100.0 / total)) + "%)\n" +
               "Stored: " + storedCount + " (" + String.format("%.1f", (storedCount * 100.0 / total)) + "%)\n" +
               "Disregarded: " + disregardedCount + " (" + String.format("%.1f", (disregardedCount * 100.0 / total)) + "%)\n" +
               "Longest Message: " + findLongestMessage().length() + " characters";
    }
    
    /**
     * Helper method to find the longest message
     */
    private String findLongestMessage() {
        if (allMessages.isEmpty()) {
            return "";
        }
        
        message longest = allMessages.get(0);
        for (message msg : allMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return longest.getMessageText();
    }
    
    /**
     * Clear all data
     */
    public void clearAllData() {
        allMessages.clear();
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipientPhones.clear();
    }
    
    // Getters for statistics
    public int getTotalMessageCount() { return allMessages.size(); }
    public int getSentMessageCount() { return sentMessages.size(); }
    public int getStoredMessageCount() { return storedMessages.size(); }
    public int getDisregardedMessageCount() { return disregardedMessages.size(); }
    public ArrayList<message> getAllMessages() { return new ArrayList<>(allMessages); }
    
}

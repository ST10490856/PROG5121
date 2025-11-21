/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @Karabo Thekiso-ST10490856
 */
public final class message {
     private String messagesId;
    private String recipient;
    private String messageText;
    private int messageNumber;
    private String messageHash;
    private String status; // Sent, Stored, Disregard
    
    /**
     *
     */
    // Default constructor
    public message() {
        this.status = "Unknown";
    }
    
    // Main constructor
    public message(String messageId, String recipient, String messageText, int messageNumber) {
        this.messagesId = messageId;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.status = "Created";
        this.messageHash = createMessageHash(); // Auto-generate hash
    }
    
    // Enhanced constructor with status
    public message(String messageId, String recipient, String messageText, int messageNumber, String status) {
        this.messagesId = messageId;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.status = status;
        this.messageHash = createMessageHash(); // Auto-generate hash
    }
    
    /**
     * Validates message ID format
     * Must be exactly 10 digits
     * @return 
     */
    public boolean checkMessageId() {
        if (messagesId == null || messagesId.length() != 10) {
            return false;
        }
        
        // Check if all characters are digits
        for (int i = 0; i < messagesId.length(); i++) {
            if (!Character.isDigit(messagesId.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validates South African cell phone numbers
     * Supports both international (+27) and local (0) formats
     * @param recipient
     * @return 
     */
    public static String checkRecipientCell(String recipient) {
        if (recipient == null || recipient.trim().isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        String cleanRecipient = recipient.replaceAll("[^0-9+]", "");
        
        // Check international format (+27 followed by 9 digits)
        if (cleanRecipient.startsWith("+27")) {
            if (cleanRecipient.length() != 12) { // +27 + 9 digits
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            String numberPart = cleanRecipient.substring(3);
            if (!isAllDigits(numberPart)) {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            // Check if it starts with 6,7,8 (valid SA mobile prefixes)
            char firstDigit = numberPart.charAt(0);
            if (firstDigit != '6' && firstDigit != '7' && firstDigit != '8') {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            return "Cell phone number successfully captured.";
        }
        
        // Check local format (0 followed by 9 digits)
        if (cleanRecipient.startsWith("0")) {
            if (cleanRecipient.length() != 10) { // 0 + 9 digits
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            String numberPart = cleanRecipient.substring(1);
            if (!isAllDigits(numberPart)) {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            // Check if it starts with 6,7,8 (valid SA mobile prefixes)
            char firstDigit = numberPart.charAt(0);
            if (firstDigit != '6' && firstDigit != '7' && firstDigit != '8') {
                return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            }
            
            return "Cell phone number successfully captured.";
        }
        
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    
    /**
     * Helper method to check if string contains only digits
     */
    private static boolean isAllDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Validates message length (max 250 characters)
     * @param message
     * @return 
     */
    public static String validateMessageLength(String message) {
        if (message == null) {
            return "Message exceeds 250 characters by 250, please reduce size.";
        }
        
        if (message.length() > 250) {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
        
        return "Message ready to send.";
    }
    
    /**
     * Creates a unique hash for the message
     * Format: First2DigitsOfID:MessageNumber:First3CharsOfFirstWord:First3CharsOfLastWord
     * @return 
     */
    public String createMessageHash() {
        if (messagesId == null || messageText == null) {
            return "INVALID_HASH";
        }
        
        String firstTwoDigits = messagesId.length() >= 2 ? messagesId.substring(0, 2) : messagesId;
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Take first 3 characters of each word (or less if word is shorter)
        String firstWordPart = firstWord.length() > 0 ? 
            firstWord.substring(0, Math.min(firstWord.length(), 3)) : "";
        String lastWordPart = lastWord.length() > 0 ? 
            lastWord.substring(0, Math.min(lastWord.length(), 3)) : "";
        
        return firstTwoDigits + ":" + messageNumber + ":" + firstWordPart + ":" + lastWordPart;
    }
    
    /**
     * Marks message as sent and returns confirmation
     * @return 
     */
    public String sentMessage() {
        this.status = "Sent";
        return "Message processed successfully and marked as Sent";
    }
    
    /**
     * Marks message as stored and returns confirmation
     * @return 
     */
    public String storeMessage() {
        this.status = "Stored";
        return "Message processed successfully and marked as Stored";
    }
    
    /**
     * Marks message as disregarded and returns confirmation
     * @return 
     */
    public String disregardMessage() {
        this.status = "Disregarded";
        return "Message processed successfully and marked as Disregarded";
    }
    
    /**
     * Returns formatted message details
     * @return 
     */
    public String printMessages() {
        return getFullDetails();
    }
    
    /**
     * Placeholder for total messages count
     * In a real implementation, this would track multiple messages
     * @return 
     */
    public int returnTotalMessages() {
        return 1; // Single message instance
    }
    
    /**
     * Returns comprehensive message details
     * @return 
     */
    public String getFullDetails() {
        return """
               === MESSAGE DETAILS ===
               Message ID: """ + messagesId + "\n" +
               "Message Hash: " + (messageHash != null ? messageHash : createMessageHash()) + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText + "\n" +
               "Message Number: " + messageNumber + "\n" +
               "Status: " + status + "\n" +
               "Length: " + (messageText != null ? messageText.length() : 0) + " characters";
    }
    
    /**
     * Returns short summary of the message
     * @return 
     */
    public String getShortSummary() {
        String shortMessage = messageText != null && messageText.length() > 50 ? 
            messageText.substring(0, 50) + "..." : messageText;
            
        return String.format("ID: %s | To: %s | Status: %s | Message: %s", 
            messagesId, recipient, status, shortMessage);
    }
    
    // =========================================================================
    // PART 3: ARRAY MANAGEMENT AND FEATURES
    // =========================================================================
    
    /**
     * PART 3a: Populate arrays with test data
     * @return 
     */
    public static ArrayList<message> populateTestData() {
        ArrayList<message> testMessages = new ArrayList<>();
        
        // Test Data Message 1: Sent
        testMessages.add(new message("1000000001", "+27834557896", "Did you get the cake?", 1, "Sent"));
        
        // Test Data Message 2: Stored
        testMessages.add(new message("1000000002", "+27838884567", "Where are you? You are late! I have asked you to be on time.", 2, "Stored"));
        
        // Test Data Message 3: Disregarded
        testMessages.add(new message("1000000003", "+27834484567", "Yohoooo, I am at your gate.", 3, "Disregarded"));
        
        // Test Data Message 4: Sent
        testMessages.add(new message("1000000004", "0838884567", "It is dinner time!", 4, "Sent"));
        
        // Test Data Message 5: Stored
        testMessages.add(new message("1000000005", "+27838884567", "Ok, I am leaving without you.", 5, "Stored"));
        
        return testMessages;
    }
    
    /**
     * PART 3b: Display the longest sent message
     * @param messages
     * @return 
     */
    public static String displayLongestMessage(ArrayList<message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages available.";
        }
        
        message longestMessage = null;
        for (message msg : messages) {
            if (longestMessage == null || msg.getMessageText().length() > longestMessage.getMessageText().length()) {
                longestMessage = msg;
            }
        }
        
        if (longestMessage != null) {
            return "=== LONGEST MESSAGE ===\n" +
                   "Message: \"" + longestMessage.getMessageText() + "\"\n" +
                   "Length: " + longestMessage.getMessageText().length() + " characters\n" +
                   "Recipient: " + longestMessage.getRecipient() + "\n" +
                   "Status: " + longestMessage.getStatus() + "\n" +
                   "Message ID: " + longestMessage.getMessageId();
        }
        
        return "No messages found.";
    }
    
    /**
     * 
     * @param messages
     * @param searchId
     * @return 
     */
    public static String searchByMessageID(ArrayList<message> messages, String searchId) {
        if (messages == null || searchId == null || searchId.trim().isEmpty()) {
            return "Invalid search parameters.";
        }
        
        for (message msg : messages) {
            if (searchId.equals(msg.getMessageId())) {
                return """
                       === MESSAGE FOUND ===
                       Message ID: """ + msg.getMessageId() + "\n" +
                       "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageText() + "\n" +
                       "Status: " + msg.getStatus() + "\n" +
                       "Hash: " + msg.getMessageHash() + "\n" +
                       "Message Number: " + msg.getMessageNumber();
            }
        }
        
        return "Message ID '" + searchId + "' not found.";
    }
    
    /**
     * 
     * @param messages
     * @param recipient
     * @return 
     */
    public static String searchByRecipient(ArrayList<message> messages, String recipient) {
        if (messages == null || recipient == null || recipient.trim().isEmpty()) {
            return "Invalid search parameters.";
        }
        
        ArrayList<message> recipientMessages = new ArrayList<>();
        for (message msg : messages) {
            if (recipient.equals(msg.getRecipient())) {
                recipientMessages.add(msg);
            }
        }
        
        if (recipientMessages.isEmpty()) {
            return "No messages found for recipient: " + recipient;
        }
        
        StringBuilder result = new StringBuilder();
        result.append("=== MESSAGES FOR RECIPIENT: ").append(recipient).append(" ===\n\n");
        
        for (int i = 0; i < recipientMessages.size(); i++) {
            message msg = recipientMessages.get(i);
            result.append("Message ").append(i + 1).append(":\n")
                  .append("  ID: ").append(msg.getMessageId()).append("\n")
                  .append("  Status: ").append(msg.getStatus()).append("\n")
                  .append("  Message: ").append(msg.getMessageText()).append("\n")
                  .append("  Hash: ").append(msg.getMessageHash()).append("\n\n");
        }
        
        result.append("Total messages found: ").append(recipientMessages.size());
        return result.toString();
    }
    
    /**
     * 
     * @param messages
     * @param hash
     * @return 
     */
    public static ArrayList<message> deleteByMessageHash(ArrayList<message> messages, String hash) {
        if (messages == null || hash == null || hash.trim().isEmpty()) {
            return messages;
        }
        
        ArrayList<message> updatedMessages = new ArrayList<>(messages); // Create a copy
        Iterator<message> iterator = updatedMessages.iterator();
        
        while (iterator.hasNext()) {
            message msg = iterator.next();
            if (hash.equals(msg.getMessageHash())) {
                iterator.remove();
                break;
            }
        }
        
        return updatedMessages;
    }
    
    /**
     * 
     * @param messages
     * @param hash
     * @return 
     */
    public static String deleteByMessageHashWithConfirmation(ArrayList<message> messages, String hash) {
        if (messages == null || hash == null || hash.trim().isEmpty()) {
            return "Invalid deletion parameters.";
        }
        
        for (int i = 0; i < messages.size(); i++) {
            message msg = messages.get(i);
            if (hash.equals(msg.getMessageHash())) {
                message deletedMessage = messages.remove(i);
                return """
                       === MESSAGE DELETED SUCCESSFULLY ===
                       Message ID: """ + deletedMessage.getMessageId() + "\n" +
                       "Hash: " + deletedMessage.getMessageHash() + "\n" +
                       "Recipient: " + deletedMessage.getRecipient() + "\n" +
                       "Message: " + deletedMessage.getMessageText() + "\n" +
                       "Total messages remaining: " + messages.size();
            }
        }
        
        return "Message with hash '" + hash + "' not found.";
    }
    
    /**
     * 
     * @param messages
     * @return 
     */
    public static String displayFullReport(ArrayList<message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages available for report.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=== FULL MESSAGE REPORT ===\n\n");
        
        int sentCount = 0;
        int storedCount = 0;
        int disregardedCount = 0;
        
        for (int i = 0; i < messages.size(); i++) {
            message msg = messages.get(i);
            
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
              .append("Total Messages: ").append(messages.size()).append("\n")
              .append("✅ Sent: ").append(sentCount).append("\n")
              .append("💾 Stored: ").append(storedCount).append("\n")
              .append("🗑️ Disregarded: ").append(disregardedCount).append("\n")
              .append("📈 Longest Message: ").append(findLongestMessage(messages).length()).append(" characters");
        
        return report.toString();
    }
    
    
    /**
     * Finds the longest message in a list
     * @param messages
     * @return 
     */
    public static String findLongestMessage(ArrayList<message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages available";
        }
        
        message longestMessage = messages.get(0);
        for (message msg : messages) {
            if (msg.getMessageText().length() > longestMessage.getMessageText().length()) {
                longestMessage = msg;
            }
        }
        
        return longestMessage.getMessageText();
    }
    
    /**
     * Finds a message by ID in a list
     * @param messages
     * @param searchId
     * @return 
     */
    public static message findMessageById(ArrayList<message> messages, String searchId) {
        if (messages == null || searchId == null) {
            return null;
        }
        
        for (message msg : messages) {
            if (searchId.equals(msg.getMessageId())) {
                return msg;
            }
        }
        return null;
    }
    
    /**
     * Finds all messages for a specific recipient
     * @param messages
     * @param recipient
     * @return 
     */
    public static ArrayList<message> findMessagesByRecipient(ArrayList<message> messages, String recipient) {
        ArrayList<message> results = new ArrayList<>();
        if (messages == null || recipient == null) {
            return results;
        }
        
        for (message msg : messages) {
            if (recipient.equals(msg.getRecipient())) {
                results.add(msg);
            }
        }
        return results;
    }
    
    /**
     * Deletes a message by hash from a list
     * @param messages
     * @param hash
     * @return 
     */
    public static boolean deleteMessageByHash(ArrayList<message> messages, String hash) {
        if (messages == null || hash == null) {
            return false;
        }
        
        for (int i = 0; i < messages.size(); i++) {
            message msg = messages.get(i);
            if (hash.equals(msg.getMessageHash())) {
                messages.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Generates a comprehensive report of all messages
     * @param messages
     * @return 
     */
    public static String generateReport(ArrayList<message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages available for report.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=== MESSAGE REPORT ===\n\n");
        report.append(String.format("%-12s %-15s %-10s %-30s\n", 
            "Message ID", "Recipient", "Status", "Message Preview"));
        report.append("-".repeat(80)).append("\n");
        
        int sentCount = 0;
        int storedCount = 0;
        int disregardedCount = 0;
        
        for (message msg : messages) {
            String shortMessage = msg.getMessageText().length() > 25 ? 
                msg.getMessageText().substring(0, 25) + "..." : msg.getMessageText();
            
            report.append(String.format("%-12s %-15s %-10s %-30s\n",
                msg.getMessageId(),
                msg.getRecipient(),
                msg.getStatus(),
                shortMessage));
            
            // Count by status
            switch (msg.getStatus()) {
                case "Sent" -> sentCount++;
                case "Stored" -> storedCount++;
                case "Disregarded" -> disregardedCount++;
            }
        }
        
        report.append("\n=== SUMMARY ===\n")
              .append("Total Messages: ").append(messages.size()).append("\n")
              .append("Sent: ").append(sentCount).append("\n")
              .append("Stored: ").append(storedCount).append("\n")
              .append("Disregarded: ").append(disregardedCount);
        
        return report.toString();
    }
    
    /**
     * Gets statistics for messages
     * @param messages
     * @return 
     */
    public static String getMessageStatistics(ArrayList<message> messages) {
        if (messages == null || messages.isEmpty()) {
            return "No messages available for statistics.";
        }
        
        int totalMessages = messages.size();
        int sentCount = 0;
        int storedCount = 0;
        int disregardedCount = 0;
        int totalLength = 0;
        int longestMessage = 0;
        int shortestMessage = Integer.MAX_VALUE;
        
        for (message msg : messages) {
            int length = msg.getMessageText().length();
            totalLength += length;
            
            if (length > longestMessage) longestMessage = length;
            if (length < shortestMessage) shortestMessage = length;
            
            switch (msg.getStatus()) {
                case "Sent" -> sentCount++;
                case "Stored" -> storedCount++;
                case "Disregarded" -> disregardedCount++;
            }
        }
        
        double averageLength = (double) totalLength / totalMessages;
        
        return """
               === MESSAGE STATISTICS ===
               Total Messages: """ + totalMessages + "\n" +
               "Sent: " + sentCount + " (" + String.format("%.1f", (sentCount * 100.0 / totalMessages)) + "%)\n" +
               "Stored: " + storedCount + " (" + String.format("%.1f", (storedCount * 100.0 / totalMessages)) + "%)\n" +
               "Disregarded: " + disregardedCount + " (" + String.format("%.1f", (disregardedCount * 100.0 / totalMessages)) + "%)\n" +
               "Average Message Length: " + String.format("%.1f", averageLength) + " characters\n" +
               "Longest Message: " + longestMessage + " characters\n" +
               "Shortest Message: " + (shortestMessage == Integer.MAX_VALUE ? 0 : shortestMessage) + " characters";
    }
    
    
    public String getMessageId() { 
        return messagesId; 
    }
    
    public void setMessageId(String messageId) { 
        this.messagesId = messageId;
        this.messageHash = createMessageHash(); // Update hash when ID changes
    }
    
    public String getRecipient() { 
        return recipient; 
    }
    
    public void setRecipient(String recipient) { 
        this.recipient = recipient; 
    }
    
    public String getMessageText() { 
        return messageText; 
    }
    
    public void setMessageText(String messageText) { 
        this.messageText = messageText;
        this.messageHash = createMessageHash(); // Update hash when text changes
    }
    
    public int getMessageNumber() { 
        return messageNumber; 
    }
    
    public void setMessageNumber(int messageNumber) { 
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash(); // Update hash when number changes
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public String getMessageHash() { 
        return messageHash != null ? messageHash : createMessageHash(); 
    }
    
    /**
     * Returns message length
     * @return 
     */
    public int getMessageLength() {
        return messageText != null ? messageText.length() : 0;
    }
    
    /**
     * Checks if message is valid (has all required fields
     * @return )
     */
    public boolean isValid() {
        return messagesId != null && !messagesId.isEmpty() &&
               recipient != null && !recipient.isEmpty() &&
               messageText != null && !messageText.isEmpty() &&
               messageNumber > 0;
    }
    
    @Override
    public String toString() {
        return String.format("Message[ID: %s, To: %s, Status: %s, Preview: %s]", 
            messagesId, recipient, status, 
            messageText != null && messageText.length() > 20 ? messageText.substring(0, 20) + "..." : messageText);
    }
}
        
        
       
        
        
    


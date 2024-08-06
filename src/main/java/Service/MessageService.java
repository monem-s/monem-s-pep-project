package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    /**
     * Constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Creates a new message.
     * @param msg the message to be created.
     * @return new message if message creation was successful.
     */
    public Message addMessage(Message msg) {
        if (msg.getMessage_text() != "" && msg.getMessage_text().length() <= 255) {
            return messageDAO.createMessage(msg);
        }
        return null; // Creation failed
    }

    /**
     * Retrieves all messages.
     * @return list with all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Retrieves message by ID.
     * @param id the ID of the message.
     * @return message if message ID was found.
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * Retrieves all messages from account.
     * @param id the ID of the account.
     * @return message if account ID was found.
     */
    public List<Message> getMessagesByAccount(int id) {
        return messageDAO.getMessagesByAcc(id);
    }

    /**
     * Deletes message by ID.
     * @param id the ID of the message.
     * @return deleted message if deletion was successful.
     */
    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    /**
     * Updates message.
     * @param id the ID of the message.
     * @param text the new message text.
     * @return updated message if update was successful.
     */
    public Message updateMessage(int id, String text) {
        if (text != "" && text.length() <= 255 && getMessageById(id) != null) {
            return messageDAO.updateMessage(id, text);
        }
        return null; // Update failed
    }


}

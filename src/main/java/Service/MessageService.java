package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message msg) {
        if (msg.getMessage_text() != "" && msg.getMessage_text().length() <= 255) {
            return messageDAO.createMessage(msg);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(int id, String text) {
        if (text != "" && text.length() <= 255 && getMessageById(id) != null) {
            return messageDAO.updateMessage(id, text);
        }
        return null;    
    }

    public List<Message> getMessagesByAccount(int id) {
        return messageDAO.getMessagesByAcc(id);
    }
}

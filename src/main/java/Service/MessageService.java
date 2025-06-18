package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){

        messageDAO = new MessageDAO();

        public Message newMessage(Message message){
            Message message = 
            if (
                message.getMessage_text().trim().isEmpty() || 
                message.getMessage_text().length() > 255  ||
                mess) {
                    return null;
                } else {
                    return messageDAO.newMessage(message);
                }
            
        }

    }
    
}

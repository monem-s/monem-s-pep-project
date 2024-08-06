package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * DONE: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    

    /**
     * Handler to register a new account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account accReg = accountService.register(acc);
        if (accReg != null) {
            context.json(mapper.writeValueAsString(accReg));
        } else {
            // Client error - Registration not successful
            context.status(400);
        }    
    }

    /**
     * Handler to login an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account accLogged = accountService.login(acc);
        if (accLogged != null) {
            context.json(mapper.writeValueAsString(accLogged));
        } else {
            // Unauthorized - Login not successful
            context.status(401);
        }
    }

    /**
     * Handler to post a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(context.body(), Message.class);

        // Account check
        if (accountService.getAccountById(msg.getPosted_by()) == null) {
            context.status(400);
        } else {
            Message addedMsg = messageService.addMessage(msg);
            if (addedMsg != null) {
                context.json(mapper.writeValueAsString(addedMsg));
            } else {
                // Client error - post not successful
                context.status(400);
            }
        }
    }

    /**
     * Handler to retrieve all messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * Handler to get a message by ID.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context context) {
        Message msg = messageService.getMessageById(Integer.valueOf(context.pathParam("message_id")));
        
        if (msg != null) {
            context.json(msg);
        }

        context.status(200);
    }

    /**
     * Handler to delete a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context context) {
        Message msg = messageService.deleteMessage(Integer.valueOf(context.pathParam("message_id")));
        
        if (msg != null) {
            context.json(msg);
        }

        context.status(200);    
    }

    /**
     * Handler to update a message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String txt = mapper.readTree(context.body()).path("message_text").asText(null);
        Message msg = messageService.updateMessage(Integer.valueOf(context.pathParam("message_id")), txt);
        
        if (msg != null) {
            context.json(msg);
        } else {
            // Client error - Update not successful
            context.status(400);
        }
    }

    /**
     * Handler to retrieve messages from an account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesByAccountHandler(Context context) {
        List<Message> messages = messageService.getMessagesByAccount(Integer.valueOf(context.pathParam("account_id")));
        context.json(messages);
    }

}
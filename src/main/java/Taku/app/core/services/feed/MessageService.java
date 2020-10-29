package Taku.app.core.services.feed;

import Taku.app.core.models.feed.Messages;
import Taku.app.core.models.feed.Total_messages;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.MessageRequest;
import Taku.app.core.payload.response.ConversationResponse;
import Taku.app.core.repositories.ConversationRepository;
import Taku.app.core.repositories.MessageRepository;
import Taku.app.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<Messages> getDialog(User person, User interlocutor) {
        return messageRepository.findByRecipientOrSenderOrderByPostedDesc(person, interlocutor);
    }

    public List<ConversationResponse> getLastMessages(Long id) {
        Optional<User> person = userRepository.findById(id);
        User user = person.get();
        List<Messages> recentMessages = messageRepository.findLastMessagesByPerson(user);

        return recentMessages.stream().map(this::mapFromRequestToDto).collect(toList());
    }

    public Messages send(MessageRequest messageRequest) {

        User sender = userRepository.findByUsername(messageRequest.getSender());
        User recipient = userRepository.findByUsername(messageRequest.getRecipient());

        //check to see if conversation exist
        List<Messages> conversation = messageRepository.findByRecipientOrSenderOrderByPostedDesc(sender, recipient);
        if( conversation.isEmpty() || conversation.get(0) == null){
            //creates new conversation
            Total_messages total_messages = new Total_messages(1);
            Messages messages = new Messages(messageRequest.getMessage(), sender, recipient, total_messages);
            conversationRepository.save(total_messages);
            return messageRepository.save(messages);
        } else{
            //adds lines to total conversation
            Total_messages chat = conversationRepository.findById(conversation.get(0).getId()).get();
            Messages messages = new Messages(messageRequest.getMessage(), sender, recipient, chat);
            chat.setTotal_messages(chat.getTotal_messages() + 1);
            conversationRepository.save(chat);
            return messageRepository.save(messages);
        }

    }

    private ConversationResponse mapFromRequestToDto(Messages messages) {
        ConversationResponse conversationResponse = new ConversationResponse();
        conversationResponse.setMessage(messages.getMessage());
        conversationResponse.setSender(messages.getSender());
        conversationResponse.setRecipient(messages.getRecipient());
        conversationResponse.setPosted(messages.getPosted());
        return conversationResponse;
    }
}

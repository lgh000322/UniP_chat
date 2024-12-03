package UniP_server_chat.Unip_party_chat.domain.chatLog.repository.mongorepo;

import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLogMongo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatLogRepositoryMongo extends MongoRepository<ChatLogMongo, ObjectId>, CustomChatLogRepositoryMongo {
}

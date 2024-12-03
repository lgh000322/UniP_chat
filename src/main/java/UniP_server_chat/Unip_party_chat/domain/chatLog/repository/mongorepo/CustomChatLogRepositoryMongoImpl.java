package UniP_server_chat.Unip_party_chat.domain.chatLog.repository.mongorepo;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLogMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomChatLogRepositoryMongoImpl implements CustomChatLogRepositoryMongo {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<List<ChatLogMongo>> findChatLogsBeforeEnteringChat(UUID roomId) {
        // 1. Query 객체 생성
        Query query = new Query();

        // 2. 채팅방 ID로 필터링
        query.addCriteria(Criteria.where("chatRoomId").is(roomId));

        // 3. 채팅 기록을 최신순으로 내림차순 정렬 (sentTime 기준)
        query.with(Sort.by(Sort.Order.desc("sentTime")));

        // 4. 10개의 채팅 로그만 가져옴
        query.limit(10);

        // 5. MongoDB에서 실행하여 결과 반환
        List<ChatLogMongo> chatLogs = mongoTemplate.find(query, ChatLogMongo.class);;

        return Optional.ofNullable(chatLogs);
    }

    @Override
    public Optional<List<ChatLogMongo>> findChatLogMongoBySentTimeGoe(UUID roomId, LocalDateTime participatedTime, LocalDateTime pagingTime) {
        // 조건을 AND로 결합
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("chatRoomId").is(roomId),          // 채팅방 ID 조건
                Criteria.where("sentTime").gte(participatedTime),  // 참여한 시간보다 크거나 같은 조건
                Criteria.where("sentTime").lte(pagingTime)         // 페이징 시간보다 작거나 같은 조건
        );

        // 쿼리 객체 생성
        Query query = new Query(criteria);

        // 내림차순 정렬 (sentTime 기준)
        query.with(Sort.by(Sort.Order.desc("sentTime")));

        // 10개만 가져오기
        query.limit(10);

        // MongoDB에서 실행하여 결과 반환
        List<ChatLogMongo> chatLogMongos = mongoTemplate.find(query, ChatLogMongo.class);

        return Optional.ofNullable(chatLogMongos);
    }



}

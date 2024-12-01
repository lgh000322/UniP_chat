package UniP_server_chat.Unip_party_chat.global.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${server.name}")
    private String serverName;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory batchMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setBatchListener(true);  // 배치 처리를 활성화
        factory.setBatchSize(10);  // 한 번에 가져올 메시지 개수 설정
        factory.setConsumerBatchEnabled(true);  // 컨슈머 배치 활성화
        return factory;
    }

    // 저장 큐
    @Bean
    @Qualifier(value = "storageQueue")
    public Queue storageQueue() {
        return QueueBuilder.durable("chat.storage.queue")
                .withArgument("x-queue-mode", "lazy")
                .withArgument("x-dead-letter-exchange", "chat.storage.dlx")
                .withArgument("x-dead-letter-routing-key", "chat.storage.dlq")
                .build();
    }

    @Bean
    @Qualifier(value = "storageDeadLetterQueue")
    public Queue storageDeadLetterQueue() {
        return QueueBuilder.durable("chat.storage.dlq")
                .withArgument("x-queue-mode", "lazy")
                .build();
    }

    @Bean
    public Binding deadLetterBinding(@Qualifier("storageDeadLetterQueue") Queue chatStoreDeadLetterQueue) {
        return BindingBuilder.bind(chatStoreDeadLetterQueue)
                .to(storageDeadLetterExchange())
                .with("chat.storage.dlq");
    }

    @Bean
    public DirectExchange storageDeadLetterExchange() {
        return new DirectExchange("chat.storage.dlx");
    }

    // Broadcast Exchange 설정
    @Bean
    public FanoutExchange broadcastExchange() {
        return new FanoutExchange("chat.broadcast.exchange");
    }

    // 각 서버별 Unique한 브로드캐스트 수신 큐 생성 (서버 이름을 기반으로 큐 이름 설정)
    @Bean
    @Qualifier(value = "broadcastQueue")
    public Queue broadcastQueue() {
        return QueueBuilder.durable("chat.broadcast." + serverName)  // 서버마다 고유한 큐 이름
                .build();
    }

    @Bean
    public Binding broadcastBinding(@Qualifier(value = "broadcastQueue") Queue broadcastQueue, FanoutExchange broadcastExchange) {
        return BindingBuilder.bind(broadcastQueue)
                .to(broadcastExchange);
    }

}

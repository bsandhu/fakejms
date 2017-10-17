import java.io.Serializable
import javax.jms.*


class FakeSession : Session {

    var isRolledBack: Boolean = false
    var isRecovered: Boolean = false
    var isRunning: Boolean = false
    var isCommitted: Boolean = false
    var isUnsubscribed: Boolean = false
    var isClosed: Boolean = false
    var producers: MutableMap<String, MessageProducer> = mutableMapOf()
    var consumers: MutableMap<String, MessageConsumer> = mutableMapOf()
    var msgListener: MessageListener? = null

    override fun recover() {
        isRecovered = true
        println("FakeSession recovered")
    }

    override fun createQueue(p0: String?): Queue {
        return FakeQueue(p0 ?: "")
    }

    override fun createTemporaryQueue(): TemporaryQueue {
        return FakeTemporaryQueue()
    }

    override fun createTopic(p0: String?): Topic {
        return FakeTopic(p0 ?: "")
    }

    override fun createTemporaryTopic(): TemporaryTopic {
        return FakeTemporaryTopic()
    }

    override fun rollback() {
        isRolledBack = true
        println("FakeSession rolled back")
    }

    override fun commit() {
        isCommitted = true
        println("FakeSession committed")
    }

    override fun unsubscribe(p0: String?) {
        isUnsubscribed = true
        println("FakeSession Unsubscribed")
    }

    override fun close() {
        isClosed = true
        println("FakeSession closed")
    }

    override fun getAcknowledgeMode(): Int {
        return 1
    }

    override fun setMessageListener(p0: MessageListener?) {
        msgListener = p0
    }

    override fun getMessageListener(): MessageListener {
        return msgListener ?: throw JMSException("MsessgaeListener not set")
    }

    override fun run() {
        isRunning = true
        println("FakeSession running")
    }


    /**** Message consumers ****/

    override fun createConsumer(dest: Destination?): MessageConsumer {
        val consumer = FakeMessageConsumer(dest)
        consumers[getNameFor(dest)] = consumer
        return consumer
    }

    override fun createConsumer(dest: Destination?, p1: String?): MessageConsumer {
        return createConsumer(dest)
    }

    override fun createConsumer(dest: Destination?, p1: String?, p2: Boolean): MessageConsumer {
        return createConsumer(dest)
    }


    /**** Message Producers ****/

    override fun createProducer(dest: Destination?): MessageProducer {
        val producer = FakeMessageProducer(dest)
        producers[getNameFor(dest)] = producer
        return producer
    }

    private fun getNameFor(p0: Destination?): String {
        return when (p0) {
            is Topic -> p0.topicName
            is Queue -> p0.queueName
            is TemporaryTopic -> p0.topicName
            is TemporaryQueue -> p0.queueName
            else -> "NoName"
        }
    }


    /**** Message creation ****/

    override fun createTextMessage(): TextMessage {
        return FakeTextMessage()
    }

    override fun createTextMessage(p0: String?): TextMessage {
        return FakeTextMessage(p0 ?: "")
    }

    override fun createMessage(): Message {
        TODO("not implemented")
    }

    override fun createObjectMessage(): ObjectMessage {
        return FakeObjectMessage()
    }

    override fun createObjectMessage(p0: Serializable?): ObjectMessage {
        return FakeObjectMessage(p0)
    }

    override fun createBytesMessage(): BytesMessage {
        TODO("not implemented")
    }

    override fun createStreamMessage(): StreamMessage {
        TODO("not implemented")
    }

    override fun createMapMessage(): MapMessage {
        TODO("not implemented")
    }


    /**** Shared/Durables ****/

    override fun createSharedConsumer(p0: Topic?, p1: String?): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createSharedConsumer(p0: Topic?, p1: String?, p2: String?): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createSharedDurableConsumer(p0: Topic?, p1: String?): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createSharedDurableConsumer(p0: Topic?, p1: String?, p2: String?): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createDurableSubscriber(p0: Topic?, p1: String?): TopicSubscriber {
        TODO("not implemented")
    }

    override fun createDurableSubscriber(p0: Topic?, p1: String?, p2: String?, p3: Boolean): TopicSubscriber {
        TODO("not implemented")
    }

    override fun getTransacted(): Boolean {
        return false
    }

    override fun createDurableConsumer(p0: Topic?, p1: String?): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createDurableConsumer(p0: Topic?, p1: String?, p2: String?, p3: Boolean): MessageConsumer {
        return createConsumer(p0)
    }

    override fun createBrowser(p0: Queue?): QueueBrowser {
        TODO("not implemented")
    }

    override fun createBrowser(p0: Queue?, p1: String?): QueueBrowser {
        TODO("not implemented")
    }
}
import javax.jms.Queue
import javax.jms.TemporaryQueue
import javax.jms.TemporaryTopic
import javax.jms.Topic

class FakeTemporaryTopic(val name: String = "") : TemporaryTopic {
    var isDeleted = false

    override fun getTopicName(): String {
        return name
    }

    override fun delete() {
        isDeleted = true
        println("FakeTemporaryTopic deleted")
    }

}

class FakeTopic(val name: String = "") : Topic {
    override fun getTopicName(): String {
        return name
    }
}

class FakeTemporaryQueue(val name: String = "") : TemporaryQueue {
    var isDeleted = false

    override fun getQueueName(): String {
        return name
    }

    override fun delete() {
        isDeleted = true
        println("FakeTemporaryQueue deleted")
    }
}

class FakeQueue(val name: String = "") : Queue {
    override fun getQueueName(): String {
        return name
    }
}
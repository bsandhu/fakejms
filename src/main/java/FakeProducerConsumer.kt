import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import javax.jms.*

class FakeMessageConsumer : MessageConsumer {

    var msgListener: MessageListener? = null
    var receivedMsgs: BlockingQueue<Message> = LinkedBlockingQueue()
    var isClosed: Boolean = false

    constructor(dest: Destination?)

    override fun setMessageListener(p0: MessageListener?) {
        msgListener = p0
    }

    override fun getMessageListener(): MessageListener {
        return msgListener ?: throw JMSException("Msg listener not set")
    }

    private fun popTestMsg(fn: () -> Message?): Message {
        if (isClosed) throw JMSException("FakeMessageConsumer is closed")
        println("FakeMessageConsumer waiting to receive msg")
        val msg = fn()
        println("FakeMessageConsumer received msg: $msg")
        return msg ?: throw JMSException("Did not receive test msg")
    }

    override fun receive(): Message {
        return popTestMsg { receivedMsgs.take() }
    }

    override fun receive(p0: Long): Message {
        return popTestMsg { receivedMsgs.poll(p0, TimeUnit.MILLISECONDS) }
    }

    override fun receiveNoWait(): Message {
        return receive(0)
    }

    override fun getMessageSelector(): String {
        return "FakeMessageSelector"
    }

    override fun close() {
        isClosed = true
        println("FakeMessageSelector closed")
    }

}

class FakeMessageProducer : MessageProducer {
    var _deliveryMode: Int = 0
    var _timeToLive: Long = 0
    var _disableMessageID: Boolean = false
    var _deliveryDelay: Long = 0
    var _priority: Int = 0
    var _disableMessageTimestamp: Boolean = false
    var isClosed: Boolean = false
    var sentMsgs: MutableList<Message?> = mutableListOf()

    constructor(destination: Destination?)

    override fun getDeliveryMode(): Int = _deliveryMode
    override fun setDeliveryMode(p0: Int) {
        _deliveryMode = p0
    }

    override fun getTimeToLive(): Long = _timeToLive
    override fun setTimeToLive(p0: Long) {
        _timeToLive = p0
    }

    override fun getDisableMessageID(): Boolean = _disableMessageID
    override fun setDisableMessageID(p0: Boolean) {
        _disableMessageID = p0
    }

    override fun getDeliveryDelay(): Long = _deliveryDelay
    override fun setDeliveryDelay(p0: Long) {
        _deliveryDelay = p0
    }

    override fun getPriority(): Int = _priority
    override fun setPriority(p0: Int) {
        _priority = p0
    }

    override fun getDisableMessageTimestamp(): Boolean = _disableMessageTimestamp
    override fun setDisableMessageTimestamp(p0: Boolean) {
        _disableMessageTimestamp = p0
    }

    override fun getDestination(): Destination = destination

    override fun send(p0: Message?) {
        sentMsgs.add(p0)
    }

    override fun send(p0: Message?, p1: Int, p2: Int, p3: Long) {
        sentMsgs.add(p0)
    }

    override fun send(p0: Destination?, p1: Message?) {
        sentMsgs.add(p1)
    }

    override fun send(p0: Destination?, p1: Message?, p2: Int, p3: Int, p4: Long) {
        sentMsgs.add(p1)
    }

    override fun send(p0: Message?, p1: CompletionListener?) {
        sentMsgs.add(p0)
    }

    override fun send(p0: Message?, p1: Int, p2: Int, p3: Long, p4: CompletionListener?) {
        sentMsgs.add(p0)
    }

    override fun send(p0: Destination?, p1: Message?, p2: CompletionListener?) {
        sentMsgs.add(p1)
    }

    override fun send(p0: Destination?, p1: Message?, p2: Int, p3: Int, p4: Long, p5: CompletionListener?) {
        sentMsgs.add(p1)
    }

    override fun close() {
        isClosed = true
        println("FakeMessageProducer closed")
    }

}

class FakeMessageListener : MessageListener {

    override fun onMessage(p0: Message?) {
        println("Msg $p0 received on FakeMsgListner")
    }
}


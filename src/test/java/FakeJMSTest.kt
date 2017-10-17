import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.jms.Session
import kotlin.test.assertEquals
import javax.jms.TextMessage


class FakeJMSTest {

    @Test
    fun sendMessage() {
        val session: Session = FakeSession()

        // Invoke Fake session like the real thing
        val producer = session.createProducer(FakeTopic("TestTopic"))
        val msg = session.createTextMessage("{'A': 100}")
        val msg2 = session.createObjectMessage("{'B': 500}")
        producer.send(msg)
        producer.send(msg2)

        // Inspect state on the fake session
        val fakeProducer = (session as FakeSession).producers["TestTopic"] as FakeMessageProducer
        val sentMsg1 = fakeProducer.sentMsgs[0] as FakeTextMessage
        val sentMsg2 = fakeProducer.sentMsgs[1] as FakeObjectMessage

        assertEquals(fakeProducer.sentMsgs.size, 2)
        assertEquals(sentMsg1.msg, "{'A': 100}")
        assertEquals(sentMsg2.msg, "{'B': 500}")
    }

    @Test
    fun receiveMessage() {
        val session: Session = FakeSession()

        // Invoke Fake session like the real thing
        val consumer = session.createConsumer(FakeQueue("TestQueue"))

        // Set state on the fake session
        val fakeConsumer = (session as FakeSession).consumers["TestQueue"] as FakeMessageConsumer
        val msg = FakeTextMessage("TestMsgToConsume")

        // Set test message for delivery
        fakeConsumer.receivedMsgs.add(msg)

        // Start listening - this would normally happed in a separate thread
        val latch = CountDownLatch(1)
        Runnable {
            val m = consumer.receive()
            if (m != null) {
                if (m is TextMessage) {
                    System.out.println("Reading message: " + m.text)
                    latch.countDown()
                }
            }
        }.run()

        latch.await()
    }
}
import java.io.Serializable
import java.util.*
import javax.jms.*
import kotlin.collections.HashMap


class FakeObjectMessage(var msg: Serializable? = null) : ObjectMessage, FakeMessage() {

    override fun getObject(): Serializable {
        return msg ?: throw JMSException("Object not set on FakeObjectMessage")
    }

    override fun setObject(obj: Serializable) {
        msg = obj
    }

    override fun isBodyAssignableTo(c: Class<*>?): Boolean {
        return c!!.isAssignableFrom(msg?.javaClass)
    }

    override fun <T : Any?> getBody(c: Class<T>?): T {
        return msg as T
    }

    override fun clearBody() {
        msg = null
    }
}


class FakeTextMessage(var msg: String = "") : TextMessage, FakeMessage() {

    override fun isBodyAssignableTo(p0: Class<*>?): Boolean {
        return p0!!.isAssignableFrom(String.javaClass)
    }

    override fun <T : Any?> getBody(p0: Class<T>?): T {
        return msg as T
    }

    override fun clearBody() {
        msg = ""
    }

    override fun getText(): String {
        return msg
    }

    override fun setText(p0: String?) {
        msg = p0 ?: ""
    }
}


abstract class FakeMessage : Message {

    var floatProps = HashMap<String, Float>()
    var stringProps = HashMap<String, String?>()
    var booleanProps = HashMap<String, Boolean>()
    var objProps = HashMap<String, Any?>()
    var doubleProps = HashMap<String, Double>()
    var intProps = HashMap<String, Int>()
    var longProps = HashMap<String, Long>()
    var byteProps = HashMap<String, Byte>()
    var shortProps = HashMap<String, Short>()

    /**** Properties ****/

    override fun setFloatProperty(p0: String?, p1: Float) {
        floatProps.put(p0!!, p1)
    }

    override fun getFloatProperty(p0: String?): Float {
        return floatProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setStringProperty(p0: String?, p1: String?) {
        stringProps.put(p0!!, p1)
    }

    override fun getStringProperty(p0: String?): String {
        return stringProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setBooleanProperty(p0: String?, p1: Boolean) {
        booleanProps.put(p0!!, p1)
    }

    override fun getBooleanProperty(p0: String?): Boolean {
        return booleanProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setObjectProperty(p0: String?, p1: Any?) {
        objProps.put(p0!!, p1)
    }

    override fun getObjectProperty(p0: String?): Any {
        return objProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setDoubleProperty(p0: String?, p1: Double) {
        doubleProps.put(p0!!, p1)
    }

    override fun getDoubleProperty(p0: String?): Double {
        return doubleProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setIntProperty(p0: String?, p1: Int) {
        intProps.put(p0!!, p1)
    }

    override fun getIntProperty(p0: String?): Int {
        return intProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setLongProperty(p0: String?, p1: Long) {
        longProps.put(p0!!, p1)
    }

    override fun getLongProperty(p0: String?): Long {
        return longProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setByteProperty(p0: String?, p1: Byte) {
        byteProps.put(p0!!, p1)
    }

    override fun getByteProperty(p0: String?): Byte {
        return byteProps[p0] ?: throw JMSException("$p0 not found")
    }

    override fun setShortProperty(p0: String?, p1: Short) {
        shortProps.put(p0!!, p1)
    }

    override fun getShortProperty(p0: String?): Short {
        return shortProps[p0] ?: throw JMSException("$p0 not found")
    }


    /**** JMS Properties ****/

    var _jmsReplyTo: Destination? = null
    var _jmsDestination: Destination? = null
    var _jmsCorrelationIDAsBytes: ByteArray? = null
    var _jmsCorrelationID: String? = null
    var _jmsMessageID: String? = null
    var _jmsType: String? = null
    var _jmsDeliveryMode: Int? = null
    var _jmsPriority: Int? = null
    var _jmsExpiration: Long? = null
    var _jmsTimeStamp: Long? = null
    var _jmsDeliveryTime: Long? = null
    var _jmsRedelivered: Boolean = false

    override fun getJMSReplyTo(): Destination {
        return _jmsReplyTo ?: throw JMSException("Destination not set")
    }

    override fun setJMSReplyTo(p0: Destination?) {
        _jmsReplyTo = p0
    }

    override fun getJMSCorrelationIDAsBytes(): ByteArray {
        return _jmsCorrelationIDAsBytes ?: throw JMSException("JMSCorrelationIDAsBytes not set")
    }

    override fun setJMSCorrelationIDAsBytes(p0: ByteArray?) {
        _jmsCorrelationIDAsBytes = p0
    }

    override fun getJMSCorrelationID(): String {
        return _jmsCorrelationID ?: throw JMSException("JMSCorrelationID not set")
    }

    override fun setJMSCorrelationID(p0: String?) {
        _jmsCorrelationID = p0
    }

    override fun getJMSDeliveryMode(): Int {
        return _jmsDeliveryMode ?: throw JMSException("JMSDeliveryMode not set")
    }

    override fun setJMSDeliveryMode(p0: Int) {
        _jmsDeliveryMode = p0
    }

    override fun getJMSDestination(): Destination {
        return _jmsDestination ?: throw JMSException("JMSDestination not set")
    }

    override fun setJMSDestination(p0: Destination?) {
        _jmsDestination = p0
    }

    override fun getJMSMessageID(): String {
        return _jmsMessageID ?: throw JMSException("JMSMessageID not set")
    }

    override fun setJMSMessageID(p0: String?) {
        _jmsMessageID = p0
    }

    override fun getJMSExpiration(): Long {
        return _jmsExpiration ?: throw JMSException("JMSExpiration not set")
    }

    override fun setJMSExpiration(p0: Long) {
        _jmsExpiration = p0
    }

    override fun getPropertyNames(): Enumeration<*> = TODO("not implemented")

    override fun getJMSType(): String {
        return _jmsType ?: throw JMSException("JMSType not set")
    }

    override fun setJMSType(p0: String?) {
        _jmsType = p0
    }

    override fun getJMSTimestamp(): Long {
        return _jmsTimeStamp ?: throw JMSException("JMSTimestamp not set")
    }

    override fun setJMSTimestamp(p0: Long) {
        _jmsTimeStamp = p0
    }

    override fun getJMSRedelivered(): Boolean {
        return _jmsRedelivered
    }

    override fun setJMSRedelivered(p0: Boolean) {
        _jmsRedelivered = p0
    }

    override fun getJMSPriority(): Int {
        return _jmsPriority ?: throw JMSException("JMSPriority not set")
    }

    override fun setJMSPriority(p0: Int) {
        _jmsPriority = p0
    }

    override fun getJMSDeliveryTime(): Long {
        return _jmsDeliveryTime ?: throw JMSException("JMSDeliveryTime not set")
    }

    override fun setJMSDeliveryTime(p0: Long) {
        _jmsDeliveryTime = p0
    }

    /**** Others ****/

    override fun clearProperties() {
        floatProps.clear()
        stringProps.clear()
        booleanProps.clear()
        objProps.clear()
        doubleProps.clear()
        intProps.clear()
        longProps.clear()
        byteProps.clear()
        shortProps.clear()
    }

    override fun acknowledge() {
        println("FakeMsg Acknowledge")
    }

    override fun propertyExists(p0: String?): Boolean {
        return floatProps.containsKey(p0)
                || stringProps.containsKey(p0)
                || booleanProps.containsKey(p0)
                || objProps.containsKey(p0)
                || doubleProps.containsKey(p0)
                || intProps.containsKey(p0)
                || longProps.containsKey(p0)
                || byteProps.containsKey(p0)
                || shortProps.containsKey(p0)
    }
}

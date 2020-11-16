from umqtt.simple import MQTTClient
import properties as prop


# init program

class Cloud:
    MQTT_SERVER = '192.168.31.16'
    MQTT_PORT = '1883'
    # MQTT_USER =
    # MQTT_PASS =
    MQTT_TIMEOUT = 30
    MQTT_CLIENT = "kitchen"
    topic_l1 = 'sensors/home'

    def setTopic(self, topic):
        self.topic_l1 = topic

    def __init__(self):
        self.client = self.client()

    # helper methods
    # def mqtt_publish(self, topic_l2, payload, retain=False, qos=0):
    def mqtt_publish(self, payload, retain=False, qos=0, topic_l2=''):
        self.client.connect()
        self.client.publish(self.topic_l1 + topic_l2, payload, retain, 0)
        self.client.publish(payload, retain, 0)
        self.client.disconnect()
        print('mqtt published msg: %s; to topic: %s' % (payload, self.topic_l1 + topic_l2))

    def sub_cb(topic, msg):
        print('got msg')

    def client(self):
        # self.client = MQTTClient(client_id=self.MQTT_CLIENT, server=self.MQTT_SERVER, port=self.MQTT_PORT,
        #                          user=self.MQTT_USER, password=self.MQTT_PASS)
        self.client = MQTTClient(client_id=self.MQTT_CLIENT, server=self.MQTT_SERVER, port=self.MQTT_PORT)
        # client.set_last_will('status', 'disconnected', retain=True)
        return self.client

from umqtt.simple import MQTTClient
import properties as prop


# init program

class Cloud:
    MQTT_SERVER = prop.server()
    MQTT_PORT = prop.port()
    MQTT_USER = prop.user()
    MQTT_PASS = prop.user_pass()
    MQTT_TIMEOUT = 30
    MQTT_CLIENT = "esp32"
    topic_l1 = 'sensors/'

    def setTopic(self, topic):
        self.topic_l1 = topic

    def __init__(self):
        self.client = self.client()

    # helper methods
    def mqtt_publish(self, topic_l2, payload, retain=False, qos=0):
        self.client.connect()
        self.client.publish(self.topic_l1 + topic_l2, payload, retain, 0)
        self.client.disconnect()
        print('mqtt published msg: %s; to topic: %s' % (payload, self.topic_l1 + topic_l2))

    def sub_cb(topic, msg):
        print('got msg')

    def client(self):
        self.client = MQTTClient(client_id=self.MQTT_CLIENT, server=self.MQTT_SERVER, port=self.MQTT_PORT,
                                 user=self.MQTT_USER, password=self.MQTT_PASS)
        # client.set_last_will('status', 'disconnected', retain=True)
        return self.client

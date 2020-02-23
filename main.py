import utime
import machine
from machine import Pin, I2C
import dht
import uping


def measureDHT11():
    sensor = dht.DHT11(Pin(22))
    while True:
        try:
            sensor.measure()
        except OSError as e:
            print(e)
            utime.wait(5)
        else:
            break

    temp = sensor.temperature()
    hum = sensor.humidity()
    measure = [temp, hum]
    return measure


def relay():
    pin = Pin(0, Pin.OUT)
    pin.on()
    utime.sleep(3)
    pin.off()


def do_connect():
    import network
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        print('connecting to network...')
        wlan.connect('royter', 'traveller22')
        while not wlan.isconnected():
            pass
    print('network config:', wlan.ifconfig())


def measureFire():
    fire = machine.ADC(Pin(35))
    return 4095 - fire.read()


def measureSmoke():
    smoke = machine.ADC(Pin(32))
    return smoke.read()


def sendData(measure):
    import properties as prop
    from umqtt.simple import MQTTClient
    server = prop.server()
    client = MQTTClient("umqtt_client", server, user=prop.user(), password=prop.password(),
                        port=prop.port())
    apiKey = prop.apiKey()
    client.connect()
    client.subscribe(topic='smart_home')
    client.publish(msg=measure[0])


def main():
    import boot
    boot.init()
    while True:
        measures = measureDHT11()
        measures.append(measureFire())
        measures.append(measureSmoke())
        print('temp= ' + str(measures[0]) + ' hum= ' + str(measures[1]) + ' fire = ' + str(
            measures[2]) + ' smoke = ' + str(measures[3]))
        utime.sleep(5)
        sendData(measures)


if __name__ == '__main__':
    main()

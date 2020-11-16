import utime
import machine
from machine import Pin, I2C
import dht
import cloud
import gc
import webrepl

CLIENT_ID = "esp32-kitchen"


def measureDHT11():
    sensor = dht.DHT11(Pin(22, Pin.IN, Pin.PULL_UP))
    while True:
        try:
            sensor.measure()
        except OSError as e:
            print(e)
            utime.sleep(5)
        else:
            break
    temp = sensor.temperature()
    hum = sensor.humidity()
    data = [temp, hum]
    return data


def relay():
    pin = Pin(0, Pin.OUT)
    pin.on()
    utime.sleep(3)
    pin.off()


def measureFire():
    fire = machine.ADC(Pin(35))
    return 4095 - fire.read()


def measureFireDigital():
    fireStarter = machine.Pin(2, machine.Pin.IN, machine.Pin.PULL_UP)
    return fireStarter.value()


def measureSmoke():
    smokeSensor = machine.ADC(Pin(32))
    smoke = smokeSensor.read()
    if smoke < 1200:
        smoke = 0
    return smoke


def measureSmokeDigital():
    smokeDetector = machine.Pin(32, machine.Pin.IN, machine.Pin.PULL_UP)
    return 1 - smokeDetector.value()


def collectData():
    data = measureDHT11()
    data.append(measureFireDigital())
    data.append(measureSmokeDigital())
    return data


def collectRequest(data):
    year, month, day, hour, minute, second, ms, dayinyear = utime.localtime()
    request = '{"date":"' + str(year) + '.' + str(month) + '.' + str(day) + ' ' + str(hour) + ':' + str(
        minute) + ':' + str(second) + '",\n"temperatura":"' + str(data[0]) + '",\n"humidity":"' + str(
        data[1]) + '",\n"fire":"' + str(data[2]) + '",\n"smoke":"' + str(data[3]) + '"}'
    return request


def send(req):
    from umqtt.simple import MQTTClient
    mqtt = MQTTClient(CLIENT_ID, '192.168.31.16')
    mqtt.connect()
    mqtt.publish('sensors/home/{}'.format(CLIENT_ID).encode(), str(req).encode())
    mqtt.disconnect()


def main():
    # client = cloud.Cloud()
    while True:
        # names = ['temperature', 'humidity', 'fire', 'smoke', 'time']
        measures = collectData()
        request = collectRequest(measures)
        print(request)
        send(request)
        gc.collect()
        utime.sleep(60)


if __name__ == '__main__':
    webrepl.start()
    while True:
        try:
            main()
        except Exception as err:
            print(err)
            utime.sleep(60 * 5)

import utime
import machine
from machine import Pin, I2C
import dht
import uping
import cloud

client = NONE

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

def measureFire():
    fire = machine.ADC(Pin(35))
    return 4095 - fire.read()


def measureSmoke():
    smoke = machine.ADC(Pin(32))
    return smoke.read()

def collectData():
    return measureDHT11(), measureFire(), measureSmoke()

def main():
    client = Cloud()
    while True:
        names = ['temperature', 'humidity', 'fire', 'smoke']
        measures = collectData()      
        print('temp= ' + str(measures[0]) + ' hum= ' + str(measures[1]) + ' fire = ' + str(
            measures[2]) + ' smoke = ' + str(measures[3]))
        for idx in range(4):
            client.mqtt_publish(names[idx], str(measures[idx]), retain=True)
        utime.sleep(60)


if __name__ == '__main__':
    main()

import utime
import machine
from machine import Pin, I2C
import dht
import cloud
import gc

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
    return 1-smokeDetector.value()


def collectData():
    data = measureDHT11()
    data.append(measureFireDigital())
    data.append(measureSmokeDigital())
    return data


def main():
    client = cloud.Cloud()
    while True:
        names = ['temperature', 'humidity', 'fire', 'smoke', 'time']
        measures = collectData()
        print('temp= ' + str(measures[0]) + ' hum= ' + str(measures[1]) + ' fire = ' + str(
            measures[2]) + ' smoke = ' + str(measures[3]))
        for idx in range(4):
            client.mqtt_publish(names[idx], str(measures[idx]), retain=True)
        year, month, day, hour, minute, second, ms, dayinyear = utime.localtime()
        client.mqtt_publish(names[4], str(year) + '.' + str(month) + '.' + str(day) + ' ' + str(hour) + ':' + str(
            minute) + ':' + str(second), retain=True)
        gc.collect()
        utime.sleep(60)


if __name__ == '__main__':
    while True:
        try:
            main()
        except Exception as err:
            print(err)
            machine.reset()
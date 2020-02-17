import utime
import machine
from machine import Pin, I2C
import dht


def measureDHT11():
    sensor = dht.DHT11(Pin(22))
    sensor.measure()
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

def  measureFire():
    fire = machine.ADC(Pin(35))
    return fire.read()

def measureSmoke():
    smoke =machine.ADC(Pin(32))
    return smoke.read()


def main():
    do_connect()
    measures = measureDHT11()
    fire = measureFire()
    measures.append(fire)



if __name__ == '__main__':
    main()

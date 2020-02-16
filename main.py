import utime
import machine
from machine import Pin, I2C
import dht

def measureDHT11():
    sensor = dht.DHT11(Pin(22))
    sensor.measure()
    temp = sensor.temperature()
    hum = sensor.humidity()

def relay():
   pin = Pin(0,Pin.OUT)
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

def main():
    d = dht12.DHT11(machine.Pin(22))
    led = Pin(2, Pin.OUT)
    enabled = False
    while True:
        if enabled:
            led.off()
        else:
            led.on()
        utime.sleep_ms(1000)
        enabled = not enabled

if __name__ == '__main__':
    main()
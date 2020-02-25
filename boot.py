# This file is executed on every boot (including wake-boot from deepsleep)
# import esp
# esp.osdebug(None)
# import gc
# import webrepl
# webrepl.start()
import network
import machine
import esp
import gc
from ntptime import settime

machine.freq(240000000)
esp.osdebug(None)


def do_connect():
    import properties
    import uping
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        print('connecting to network...')
        wlan.connect(properties.ssid(), properties.password())
        while not wlan.isconnected():
            pass
    print('network config:', wlan.ifconfig())
    print(uping.ping('google.com'))


do_connect()
settime()
gc.collect()
print('wifi connected')

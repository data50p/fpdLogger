# fpdLogger
Logging support library

I have been using this provided formatter with hi satisfaction.

Typical out put looks like this

```
 000000189 INFO      15/10 17:57:43.853    ÷42     0 PartyTalkServer            dispatchMsg0               PartyTalkConnector:measure:dispatchMsg1 ConnectorLocal 4.659260ms
 000000190 FINEST    15/10 17:57:43.853    ÷42     0 PartyTalkClient            sendMsg                    sending   done  partytalk-mqtt-bridge
 000000191 CONFIG    15/10 17:57:45.539    ÷42  1686 PartyTalkClient            getDefaultAddrType         groupSet: [MQTT-BRIDGE] -> MQTT-BRIDGE
 000000192 FINEST    15/10 17:57:45.540    ÷42     1 PartyTalkClient            sendMsg                    sending   >>>>> ⌦ PLAIN partytalk-mqtt-bridge 📠{partytalk-mqtt-bridge@MQTT-BRIDGE *@MQTT-BRIDGE 1760543865540; hello-partytalk-mqtt-bridge-1}
 000000193 FINEST    15/10 17:57:45.540    ÷42     0 ClientGhost                matchSwitcher              validate public ✉️{partytalk-mqtt-bridge@MQTT-BRIDGE} ✉️{partytalk-mqtt-bridge@MQTT-BRIDGE} -> T type 6
 000000194 FINE      15/10 17:57:45.540    ÷42     0 Dispatcher                 dispatchMsg2               msg:msg: 1707852830 => 📠{partytalk-mqtt-bridge@MQTT-BRIDGE *@MQTT-BRIDGE 1760543865540; hello-partytalk-mqtt-bridge-1}
 000000195 FINEST    15/10 17:57:45.540    ÷42     0 ClientGhost                matchSwitcher              matching public ✉️{partytalk-admin@$ADMIN} ✉️{*@MQTT-BRIDGE} -> f type 0
 
                                                                                                          |__________ the log text
                                                                               |_____ method where logging took place
                                                    |_______ class where logging took place
                                                  |_______milli seconds sinca previous output
                                          |________Thread id 
                    |___ date and time, incl milliseconds
|___ Sequence number
```

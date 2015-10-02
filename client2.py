from socket import *
import serial

ser = serial.Serial('/dev/ttyACM0', 9600)



HOST = '192.168.1.94'
PORT = 38200
server_socket = socket(AF_INET, SOCK_STREAM)
server_socket.connect((HOST,PORT))
message = '0'
while message != '|':
    message = ser.readline()
    if message:
        server_socket.sendall(message)
    else:
        break
print ('end')
server_socket.close()
exit()

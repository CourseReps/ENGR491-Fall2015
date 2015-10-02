from socket import *


HOST = '127.0.0.1'
PORT = 3820
server_socket = socket(AF_INET, SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(1)
conn, addr = server_socket.accept()
message = str(conn.recv(1024),"utf-8")

while len(message) != 0:
    print(message)
    message = str(conn.recv(1024),"utf-8")

server_socket.close()
exit()

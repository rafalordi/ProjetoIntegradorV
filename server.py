import socket
import pyautogui


def handle_command(command):
    # Implemente a lógica para executar as ações correspondentes aos comandos recebidos
    if command == "play_pause":
        pyautogui.press("playpause")  # Simula a tecla de play/pause do teclado
    elif command == "stop":
        pyautogui.press("stop")  # Simula a tecla de stop do teclado
    elif command == "next_track":
        pyautogui.press("nexttrack")  # Simula a tecla de próxima faixa do teclado
    elif command == "previous_track":
        pyautogui.press("prevtrack")  # Simula a tecla de faixa anterior do teclado
    elif command == "volume_up":
        pyautogui.press("volumeup")  # Simula a tecla de aumento de volume do teclado
    elif command == "volume_down":
        pyautogui.press("volumedown")  # Simula a tecla de diminuição de volume do teclado
    else:
        print("Comando inválido")

def start_server():
    # Configurações do servidor
    host = "192.168.10.16"  # Use 'localhost' para testar no próprio PC
    port = 5000  # Use uma porta disponível

    # Crie um socket TCP/IP
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Associe o socket com o endereço e porta
    server_socket.bind((host, port))

    # Defina o socket para escutar conexões
    server_socket.listen()

    print(f"Servidor iniciado. Aguardando conexões em {host}:{port}")

    try:
        while True:
            # Aguarde por uma conexão
            client_socket, client_address = server_socket.accept()

            print(f"Conexão estabelecida com {client_address}")

            # Receba o comando enviado pelo cliente
            command = client_socket.recv(1024).decode("utf-8")

            # Manipule o comando recebido
            handle_command(command)

            # Feche a conexão com o cliente
            client_socket.close()

    except KeyboardInterrupt:
        print("Servidor encerrado pelo usuário.")
    finally:
        # Encerre o socket do servidor
        server_socket.close()

if __name__ == "__main__":
    start_server()

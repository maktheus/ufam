from threading import *         
import time

obj = Semaphore(5) 

# Variaveis globais (memoria compartilhada)

filaOficial = []
filaSargento = []
filaCabo = []

entrada = []
fim_fila = 0
tempo = 0
estado_barbearia = 1

num_atendimento_oficial = 0
num_atendimento_sargento = 0
num_atendimento_cabo = 0

def adicionarFila(tempo_descanso): # Realiza o controle da fila
    global entrada, filaOficial, filaSargento, filaCabo, fim_fila, tempo
    i = 0
    iter_oscioso = 0
    while (i < len(entrada)):

        if iter_oscioso >= 3:
            break

        obj.acquire()
        linha = entrada[i]
        if len(filaOficial) + len(filaSargento) + len(filaCabo) < 20:
            
            # Verifica se trata-se ou nao de um momento que nao possui clientes na fila
            if linha[0] == 0:
                iter_oscioso += 1
            else:
                iter_oscioso = 0

            # Insere na fila adequada
            if linha[0] == 1:
                filaOficial.append([linha[1], tempo])
            elif linha[0] == 2:
                filaSargento.append([linha[1], tempo])
            elif linha[0] == 3:
                filaCabo.append([linha[1], tempo])
            
        obj.release()
        # Tempo de espera do Sargento Tainha
        time.sleep(tempo_descanso)
        # Avanca o tempo
        obj.acquire()
        tempo += tempo_descanso
        obj.release()
        i += 1
    obj.acquire()
    fim_fila = 1
    obj.release()


def atenderCliente(nome): # Realiza o atendimento dos clientes
    global filaOficial, filaSargento, filaCabo, fim_fila, estado_barbearia, num_atendimento_oficial, num_atendimento_sargento, num_atendimento_cabo

    while True:
        tempo_atendimento = 1

        obj.acquire()
        # Se a fila esta vazia e nao possui mais nenhum cliente a entrar na fila podemos encerar o funcionamento da barbearia
        if (len(filaOficial) + len(filaSargento) + len(filaCabo) == 0) and (fim_fila == 1):
            break

        # Verifica as tres filas de clientes seguindo a prioridade pre-estabelecida    
        if (nome == "Recruta Zero"): # Recruta Zero ira atender a fila Oficial
            if len(filaOficial) > 0:
                atendimento = filaOficial.pop(0)
                num_atendimento_oficial += 1
                #print("Oficial esta sendo atendido")
                tempo_atendimento = atendimento[0]
            elif len(filaSargento) > 0:
                atendimento = filaSargento.pop(0)
                num_atendimento_oficial += 1
                #print("Sargento esta sendo atendido")
                tempo_atendimento = atendimento[0]    
            elif len(filaCabo) > 0:
                atendimento = filaCabo.pop(0)
                #print("Cabo esta sendo atendido")
                tempo_atendimento = atendimento[0]
                num_atendimento_cabo += 1
        elif (nome == "Dentinho"): # Dentinho ira atender a fila Sargento
            if len(filaSargento) > 0:
                atendimento = filaSargento.pop(0)
                num_atendimento_sargento += 1
                #print("Sargento esta sendo atendido")
                tempo_atendimento = atendimento[0]
            elif len(filaOficial) > 0:
                atendimento = filaOficial.pop(0)
                num_atendimento_oficial += 1
                #print("Oficial esta sendo atendido")
                tempo_atendimento = atendimento[0]
            elif len(filaCabo) > 0:
                atendimento = filaCabo.pop(0)
                num_atendimento_cabo += 1
                #print("Cabo esta sendo atendido")
                tempo_atendimento = atendimento[0]
        elif (nome == "Otto"): # Otto ira atender a fila Cabo
            if len(filaCabo) > 0:
                atendimento = filaCabo.pop(0)
                num_atendimento_cabo += 1
                #print("Cabo esta sendo atendido")
                tempo_atendimento = atendimento[0]
            elif len(filaSargento) > 0:
                atendimento = filaSargento.pop(0)
                num_atendimento_sargento += 1
                #print("Sargento esta sendo atendido")
                tempo_atendimento = atendimento[0]
            elif len(filaOficial) > 0:
                atendimento = filaOficial.pop(0)
                num_atendimento_oficial += 1
                #print("Oficial esta sendo atendido")
                tempo_atendimento = atendimento[0]
        obj.release()

        # Realiza o atendimento
        time.sleep(tempo_atendimento)

    # Indica que a barbearia fechou
    obj.acquire()
    estado_barbearia = 0
    obj.release()

def gerarRelatorio(): # Gera o relatorio do Tenete Escovinha
    global filaOficial, filaSargento, filaCabo, fim_fila, estado_barbearia, tempo, num_atendimento_oficial, num_atendimento_sargento, num_atendimento_cabo

    # Inicializa as variaveis

    qtd_amostras = 0
    ocupacao_cadeiras = 0
    ocupacao_oficial = 0
    ocupacao_sargento = 0
    ocupacao_cabo = 0
    comprimento_fila_oficial = 0
    comprimento_fila_sargento = 0
    comprimento_fila_cabo = 0
    tempo_atendimento_oficial = 0
    tempo_atendimento_sargento = 0
    tempo_atendimento_cabo = 0
    tempo_espera_oficial = 0
    tempo_espera_sargento = 0
    tempo_espera_cabo = 0
    
    
    while True:
        obj.acquire()
        # Verifica se a barbearia encerrou os trabalhos ou nao
        if (estado_barbearia == 0):
            break
        tempo_ = tempo
        filaOficial_ = filaOficial
        filaSargento_ = filaSargento
        filaCabo_ = filaCabo
        obj.release()
        ocupacao_cadeiras += (len(filaOficial_) + len(filaSargento) + len(filaCabo))/20
        if ((len(filaOficial_) + len(filaSargento) + len(filaCabo)) > 0):
            ocupacao_oficial += len(filaOficial_) / (len(filaOficial_) + len(filaSargento) + len(filaCabo))
            ocupacao_sargento += len(filaSargento_) / (len(filaOficial_) + len(filaSargento) + len(filaCabo))
            ocupacao_cabo += len(filaCabo_) / (len(filaOficial_) + len(filaSargento) + len(filaCabo))

        comprimento_fila_oficial += len(filaOficial_)
        comprimento_fila_sargento += len(filaSargento_)
        comprimento_fila_cabo += len(filaCabo_)

        tempo_atendimento = 0
        tempo_espera = 0
        for p in filaOficial_:
            tempo_atendimento += p[0] # Soma o tempo de atendimento
            tempo_espera += (tempo_ - p[1]) # Computa o tempo de espera
        if (len(filaOficial_) > 0):       
            tempo_atendimento_oficial += tempo_atendimento / len(filaOficial_) # Calcula a media do tempo atendimento da fila 
            tempo_espera_oficial += tempo_espera / len(filaOficial_) # Calcula a media do tempo de espera da fila

        tempo_atendimento = 0
        tempo_espera = 0
        for p in filaSargento_:
            tempo_atendimento += p[0]
            tempo_espera += (tempo_ - p[1])
        if (len(filaSargento_) > 0):
            tempo_atendimento_sargento += tempo_atendimento / len(filaSargento_)
            tempo_espera_sargento += tempo_espera / len(filaSargento_)

        tempo_atendimento = 0
        tempo_espera = 0
        for p in filaCabo_:
            tempo_atendimento += p[0]
            tempo_espera += (tempo_ - p[1])
        if (len(filaCabo_) > 0):
            tempo_atendimento_cabo += tempo_atendimento / len(filaCabo_)
            tempo_espera_cabo += tempo_espera / len(filaCabo_)

        qtd_amostras += 1
        # A amostra deve ser coletada a cada 3 segundos
        time.sleep(3)

    obj.acquire()
    num_atendimento_oficial_ = num_atendimento_oficial
    num_atendimento_sargento_ = num_atendimento_sargento
    num_atendimento_cabo_ = num_atendimento_cabo
    obj.release()

    # Apresenta os resultados

    print("\n\n\n")
    print("-------------------------------------")
    print("Relatorio Tenete Escovinha")
    print("Ocupacao: {} %".format(100 * (ocupacao_cadeiras / qtd_amostras)))
    print("Ocupacao Oficial: {} %".format(100 * (ocupacao_oficial / qtd_amostras)))
    print("Ocupacao Sargento: {} %".format(100 * (ocupacao_sargento / qtd_amostras)))
    print("Ocupacao Cabo: {} %".format(100 * (ocupacao_cabo / qtd_amostras)))
    print("Comprimento medio fila oficial: {}".format(comprimento_fila_oficial / qtd_amostras))
    print("Comprimento medio fila sargento: {}".format(comprimento_fila_sargento / qtd_amostras))
    print("Comprimento medio fila cabo: {}".format(comprimento_fila_cabo / qtd_amostras))
    print("Tempo medio de atendimento oficial: {}s".format(tempo_atendimento_oficial / qtd_amostras))
    print("Tempo medio de atendimento sargento: {}s".format(tempo_atendimento_sargento / qtd_amostras))
    print("Tempo medio de atendimento cabo: {}s".format(tempo_atendimento_cabo / qtd_amostras))
    print("Tempo medio de espera oficial: {}s".format(tempo_espera_oficial/ qtd_amostras))
    print("Tempo medio de espera sargento: {}s".format(tempo_espera_sargento / qtd_amostras))
    print("Tempo medio de espera cabo: {}s".format(tempo_espera_cabo / qtd_amostras))
    print("Numero de atendimentos oficial: {}".format(num_atendimento_oficial_))
    print("Numero de atendimentos sargento: {}".format(num_atendimento_sargento_))
    print("Numero de atendimentos cabo: {}".format(num_atendimento_cabo_))

# Leitura do arquivo de entrada

with open("entrada.txt", "r", encoding="utf-8") as arquivo:
    for linha in arquivo.readlines():
        entrada.append(list(map(int, linha.split())))

# Leitura do tempo de descanso Sargento Tainha

tempo_descanso = -1
while ((tempo_descanso < 1) or (tempo_descanso > 5)):
    print("Digite o tempo de descanso do Sargento Tainha")
    tempo_descanso = int(input())
    if ((tempo_descanso < 1) or (tempo_descanso > 5)):
        print("Tempo invalido, digite novamente")

# Cria as threads

SargentoTainha = Thread(target = adicionarFila, args = (tempo_descanso,)) 
RecrutaZero = Thread(target = atenderCliente, args = ("Recruta Zero",))
Dentinho = Thread(target = atenderCliente, args = ("Dentinho",)) 
Otto = Thread(target = atenderCliente, args = ("Otto",)) 
TeneteEscovinha = Thread(target = gerarRelatorio) 

# Iniciliza as threads

SargentoTainha.start()
RecrutaZero.start()
Dentinho.start()
TeneteEscovinha.start()
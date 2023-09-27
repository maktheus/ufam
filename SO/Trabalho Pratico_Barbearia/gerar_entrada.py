import random


#ENGENHARIA DA COMPUTAÇÃO
with open("entrada1.txt", "w") as arquivo:
    for i in range(1000):
        categoria = random.randint(0, 3)
        if categoria == 0:
            arquivo.write("{} {}\n".format(categoria, random.randint(1, 3)))
        elif categoria == 1:
            arquivo.write("{} {}\n".format(categoria, random.randint(4, 6)))
        elif categoria == 2:
            arquivo.write("{} {}\n".format(categoria, random.randint(2, 4)))
        elif categoria == 3:
            arquivo.write("{} {}\n".format(categoria, random.randint(1, 3)))

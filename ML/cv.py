from PIL import Image
import numpy as np

imagem = Image.open('/home/muchoa/ufam/ML/iris_dataset/MMU-Iris-Database/14/left/liujwl1.bmp')

matriz_imagem = np.array(imagem)

print(matriz_imagem)


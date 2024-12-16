#Autocompletar texto:
# Dado un texto de entrada, el programa generará una continuación usando un modelo de IA preentrenado.

from transformers import pipeline
import time

# Cargar modelo
start_time = time.time()
generator = pipeline("text-generation", model="gpt2")
load_time = time.time() - start_time

# Generar texto
input_text = "Enginnering is art because"
start_time = time.time()
output = generator(input_text, max_length=50, num_return_sequences=1, temperature=0.7,top_p=0.9,repetition_penalty=1.2)
generation_time = time.time() - start_time

# Resultados
print("\n")
print(f"Tiempo de carga del modelo: {load_time:.2f} segundos")
print(f"Tiempo de generación: {generation_time:.2f} segundos")
print("Texto generado:", output[0]["generated_text"])

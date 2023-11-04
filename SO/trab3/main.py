class Frame:
    def __init__(self):
        self.process = -1
        self.page = -1
        self.time = 0
        self.bit = 0

MEMORY_SIZE = 8000
memory = [Frame() for _ in range(MEMORY_SIZE)]

def fifo(reference_string):
    global memory
    memory = [Frame() for _ in range(MEMORY_SIZE)]
    
    page_faults = 0
    next_frame = 0

    for ref in reference_string:
        if ref[0] == 0 and ref[1] == 0:
            break
        
        found = False
        for frame in memory:
            if frame.process == ref[0] and frame.page == ref[1]:
                found = True
                break

        if not found:
            memory[next_frame].process = ref[0]
            memory[next_frame].page = ref[1]
            next_frame = (next_frame + 1) % MEMORY_SIZE
            page_faults += 1

    return page_faults

def lru(reference_string):
    global memory
    memory = [Frame() for _ in range(MEMORY_SIZE)]

    page_faults = 0
    current_time = 0

    for ref in reference_string:
        if ref[0] == 0 and ref[1] == 0:
            break
        
        found = False
        for frame in memory:
            if frame.process == ref[0] and frame.page == ref[1]:
                frame.time = current_time
                found = True
                break

        if not found:
            min_time = min(memory, key=lambda f: f.time)
            min_time.process = ref[0]
            min_time.page = ref[1]
            min_time.time = current_time
            page_faults += 1

        current_time += 1

    return page_faults

def segunda_chance(reference_string):
    global memory
    memory = [Frame() for _ in range(MEMORY_SIZE)]

    page_faults = 0
    next_frame = 0

    for ref in reference_string:
        if ref[0] == 0 and ref[1] == 0:
            break
        
        found = False
        for frame in memory:
            if frame.process == ref[0] and frame.page == ref[1]:
                frame.bit = 1
                found = True
                break

        if not found:
            while memory[next_frame].bit == 1:
                memory[next_frame].bit = 0
                next_frame = (next_frame + 1) % MEMORY_SIZE

            memory[next_frame].process = ref[0]
            memory[next_frame].page = ref[1]
            next_frame = (next_frame + 1) % MEMORY_SIZE
            page_faults += 1

    return page_faults

import argparse

def load_reference_string(file_path):
    with open(file_path, 'r') as f:
        raw_string = f.read().strip()
        pairs = raw_string.split(';')[:-1]  # Omitir último par (0,0), pois ele indica fim da string.
        reference_string = [(int(pair.split(',')[0]), int(pair.split(',')[1])) for pair in pairs]
        return reference_string + [(0, 0)]  # Adicionar (0,0) no final para sinalizar o fim.

def main():
    parser = argparse.ArgumentParser(description='Simula algoritmos de troca de página.')
    parser.add_argument('file_path', type=str, help='O caminho para o arquivo de string de referência.')
    
    args = parser.parse_args()
    
    reference_string = load_reference_string(args.file_path)
    
    print(f"FIFO Page Faults: {fifo(reference_string)}")
    print(f"LRU Page Faults: {lru(reference_string)}")
    print(f"Segunda Chance Page Faults: {segunda_chance(reference_string)}")

if __name__ == "__main__":
    main()

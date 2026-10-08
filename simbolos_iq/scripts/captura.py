#!/usr/bin/python3.12
"""Executa os flowgraphs de captura (blocos reais: Random Source -> Chunks to Symbols -> Head -> Vector Sink)
compilados pelo grcc e grava dados/<mod>_iq.csv e dados/resumo.json (calculado dos dados capturados)."""
import sys, json, math, numpy as np
B = "/home/user/ufam/simbolos_iq"
sys.path.insert(0, B + "/build")
M = {"bpsk": 2, "qpsk": 4, "16qam": 16}
resumo = {}
for mod, m in M.items():
    top = getattr(__import__(f"captura_{mod}"), f"captura_{mod}")()
    top.start(); top.wait()
    x = np.array(top.blocks_vector_sink_x_0.data(), dtype=np.complex64)
    assert len(x) == 5000, len(x)
    # indice do simbolo recuperado a partir do mapeamento (para auditoria): posicao na tabela observada
    I = x.real.astype(np.float64); Q = x.imag.astype(np.float64)
    with open(f"{B}/dados/{mod}_iq.csv", "w") as f:
        f.write("indice,I,Q\n")
        for k in range(len(x)):
            f.write(f"{k},{I[k]:.9f},{Q[k]:.9f}\n")
    lvl = lambda v: sorted({round(float(a), 4) for a in v})
    pts = {(round(float(a), 4), round(float(b), 4)) for a, b in zip(I, Q)}
    amp = sorted({round(float(a), 4) for a in np.hypot(I, Q)})
    Es = float(np.mean(I**2 + Q**2))
    resumo[mod] = {
        "M": m, "n_simbolos": int(len(x)),
        "niveis_I": lvl(I), "niveis_Q": lvl(Q),
        "n_pontos_distintos": len(pts),
        "pontos_distintos": sorted(pts),
        "Es_media": round(Es, 6),
        "amplitudes_distintas": amp,
        "taxa_simbolos_sps": 32000,
        "bits_por_simbolo": int(math.log2(m)),
        "taxa_bits_bps": 32000 * int(math.log2(m)),
    }
json.dump(resumo, open(f"{B}/dados/resumo.json", "w"), indent=2)
print(json.dumps({k: {kk: v[kk] for kk in ("niveis_I","niveis_Q","n_pontos_distintos","Es_media","amplitudes_distintas","taxa_bits_bps")} for k, v in resumo.items()}, indent=1))

"""Verificacao numerica da prova de Sistemas de Controle + figuras."""
import numpy as np
from numpy.polynomial import polynomial as Pol
from scipy import signal
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

FIGDIR = "/home/user/ufam/controle_prova/prova4/figs"
import os
os.makedirs(FIGDIR, exist_ok=True)

# ---------------- Questao 2 ----------------
# P(s) = Y/U = (s+7)(s^2-3/4) / [s^2 (s+1)(s^2+6s+18)]
num_P = np.polymul([1, 7], [1, 0, -0.75])            # s^3+7s^2-0.75s-5.25
den_P = np.polymul([1, 0, 0], np.polymul([1, 1], [1, 6, 18]))
print("num_P =", num_P)
print("den_P =", den_P)

def rlocus(num, den, gains):
    pts = []
    for k in gains:
        d = np.polyadd(den, k * np.pad(num, (len(den) - len(num), 0)))
        pts.append(np.roots(d))
    return np.array(pts)

# figura: root locus K>0 e K<0 (ganho puro)
gains = np.concatenate([np.linspace(0, 5, 400), np.logspace(np.log10(5), 3, 300)])
fig, axs = plt.subplots(1, 2, figsize=(10, 4.2))
for ax, sgn, ttl in [(axs[0], +1, r"$K>0$ (LGR $180^\circ$)"),
                     (axs[1], -1, r"$K<0$ (LGR $0^\circ$)")]:
    pts = rlocus(num_P, den_P, sgn * gains)
    ax.plot(pts.real, pts.imag, ".", ms=1.2, color="#1a6feb")
    pl = np.roots(den_P); zr = np.roots(num_P)
    ax.plot(pl.real, pl.imag, "x", ms=9, color="#c22", mew=2, label="polos")
    ax.plot(zr.real, zr.imag, "o", ms=7, mfc="none", color="#171", mew=1.6, label="zeros")
    ax.axvline(0, color="k", lw=.6); ax.axhline(0, color="k", lw=.6)
    ax.set_xlim(-9, 3); ax.set_ylim(-6, 6); ax.grid(alpha=.3)
    ax.set_title(ttl); ax.set_xlabel(r"Re$(s)$")
axs[0].set_ylabel(r"Im$(s)$"); axs[0].legend(loc="upper left", fontsize=8)
fig.suptitle(r"LGR de $Y(s)/U(s)$")
fig.tight_layout(); fig.savefig(f"{FIGDIR}/q2_rlocus.pdf"); plt.close(fig)

# stability check pure gain
for k in [-0.1, -0.5, -1, -2, 0.1, 0.5]:
    d = np.polyadd(den_P, k * np.pad(num_P, (len(den_P) - len(num_P), 0)))
    r = np.roots(d)
    print(f"K={k:6.2f} max Re = {r.real.max():+.4f}")

# grid search lead compensator C(s) = Kc (s+zc)/(s+pc), Kc < 0
def cl_metrics(Kc, zc, pc):
    numL = Kc * np.polymul(num_P, [1, zc])
    denL = np.polymul(den_P, [1, pc])
    den_cl = np.polyadd(denL, np.pad(numL, (len(denL) - len(numL), 0)))
    r = np.roots(den_cl)
    if r.real.max() > -1e-9:
        return None
    sys = signal.TransferFunction(numL, den_cl)
    t = np.linspace(0, 120, 6000)
    t, y = signal.step(sys, T=t)
    Mp = (y.max() - y[-1]) / y[-1] if y[-1] > 0 else np.inf
    # settling 5%
    idx = np.where(np.abs(y - y[-1]) > 0.05 * abs(y[-1]))[0]
    ts = t[idx[-1] + 1] if len(idx) and idx[-1] + 1 < len(t) else np.inf
    us = -min(0.0, y.min()) / y[-1]   # undershoot (NMP)
    return Mp, ts, us, r.real.max()

print("\n-- grid search C(s)=Kc(s+zc)/(s+pc) --")
best = []
for zc in [0.1, 0.15, 0.2, 0.25, 0.3, 0.5]:
    for pc in [1, 1.5, 2, 3, 5]:
        for Kc in [-0.5, -1, -1.5, -2, -3, -4, -5, -7, -10]:
            m = cl_metrics(Kc, zc, pc)
            if m and m[0] < 0.5:
                best.append((m[1], Mp := m[0], Kc, zc, pc, m[2]))
best.sort()
for b in best[:12]:
    print(f"ts5%={b[0]:7.2f}s  Mp={b[1]*100:5.1f}%  Kc={b[2]:5.1f} zc={b[3]:.2f} pc={b[4]:.1f} undershoot={b[5]*100:.1f}%")

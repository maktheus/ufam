import numpy as np
from scipy import signal
from scipy.integrate import solve_ivp
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

FIGDIR = "/home/user/ufam/controle_prova/prova4/figs"

# ---------- Q2: projeto final C(s) = -2(s+0.1)/(s+2) ----------
num_P = np.polymul([1, 7], [1, 0, -0.75])
den_P = np.polymul([1, 0, 0], np.polymul([1, 1], [1, 6, 18]))
Kc, zc, pc = -2.0, 0.1, 2.0
numL = Kc * np.polymul(num_P, [1, zc])
denL = np.polymul(den_P, [1, pc])
den_cl = np.polyadd(denL, np.pad(numL, (len(denL) - len(numL), 0)))
print("polos MF:", np.sort_complex(np.roots(den_cl)))
sys = signal.TransferFunction(numL, den_cl)
t = np.linspace(0, 60, 6000)
t, y = signal.step(sys, T=t)
Mp = (y.max() - y[-1]) / y[-1]
tp = t[np.argmax(y)]
idx = np.where(np.abs(y - y[-1]) > 0.05 * abs(y[-1]))[0]
ts = t[idx[-1] + 1]
print(f"Mp = {Mp*100:.1f}%  tp = {tp:.1f}s  ts(5%) = {ts:.1f}s  y_min = {y.min():.3f}  y_inf = {y[-1]:.4f}")

fig, ax = plt.subplots(figsize=(6.4, 3.6))
ax.plot(t, y, color="#1a6feb", lw=1.8)
ax.axhline(1, color="k", lw=.7, ls="--")
ax.axhline(1.5, color="#c22", lw=.7, ls=":")
ax.annotate(f"$M_p = {Mp*100:.0f}\\%$", xy=(tp, y.max()), xytext=(tp+6, y.max()+0.04),
            arrowprops=dict(arrowstyle="->", lw=.8), fontsize=10)
ax.set_xlabel("Tempo (s)"); ax.set_ylabel("$y(t)$")
ax.set_title(r"Resposta ao degrau — malha fechada com $C(s)=-2\,\frac{s+0{,}1}{s+2}$")
ax.grid(alpha=.3)
fig.tight_layout(); fig.savefig(f"{FIGDIR}/q2_step.pdf"); plt.close(fig)

# erro em regime: L = C*P tem 1/s^2 -> tipo 2. Ka:
# Ka = lim s^2 L(s) = C(0)*[s^2 P]_{s=0} = (-2*0.1/2)*(7*(-0.75)/18)
Ka = (Kc * zc / pc) * (7 * (-0.75) / 18.0)
print("Ka =", Ka, " e_ss rampa^2 =", 1/Ka)

# ---------- Q3: valida formula Mp e figura ----------
def mp_formula(z_, eta):  # eta = wn/z
    pref = np.sqrt(1 + eta**2 - 2*z_*eta)
    phi0 = np.arctan2(z_, np.sqrt(1-z_**2))
    b1 = np.arctan2(eta - z_, np.sqrt(1-z_**2))
    ang = np.pi - phi0 - b1
    return pref * np.exp(-z_/np.sqrt(1-z_**2) * ang)

def mp_sim(z_, eta, wn=1.0):
    zz = wn/eta if eta > 0 else 1e9
    num = [wn**2/zz, wn**2]
    den = [1, 2*z_*wn, wn**2]
    t = np.linspace(0, 40/wn, 40000)
    t, y = signal.step(signal.TransferFunction(num, den), T=t)
    return y.max() - 1

print("\nQ3 validacao (zeta, eta): formula vs simulacao")
for z_ in [0.3, 0.5, 0.7]:
    for eta in [0.5, 1.0, 2.0]:
        print(f"  zeta={z_} eta={eta}: {mp_formula(z_, eta):.4f} vs {mp_sim(z_, eta):.4f}")

zg = np.linspace(0.05, 0.95, 200)
fig, ax = plt.subplots(figsize=(6.2, 3.8))
for eta, c in [(0, "#444"), (0.5, "#1a6feb"), (1.0, "#171"), (2.0, "#c22")]:
    if eta == 0:
        mp = np.exp(-np.pi*zg/np.sqrt(1-zg**2))
        lab = r"$\omega_n/z = 0$ (sem zero)"
    else:
        mp = np.array([mp_formula(z_, eta) for z_ in zg])
        lab = rf"$\omega_n/z = {eta}$"
    ax.plot(zg, 100*mp, color=c, lw=1.6, label=lab)
ax.set_xlabel(r"$\zeta$"); ax.set_ylabel(r"$M_p$ (%)"); ax.set_ylim(0, 120)
ax.grid(alpha=.3); ax.legend(fontsize=9)
ax.set_title(r"Sobressinal $M_p(\zeta,\,\omega_n/z)$ — efeito do zero extra")
fig.tight_layout(); fig.savefig(f"{FIGDIR}/q3_mp.pdf"); plt.close(fig)

# zeta necessario p/ Mp=20%
from scipy.optimize import brentq
print("\nzeta p/ Mp=20%:")
for eta in [0, 0.5, 1.0, 2.0]:
    f = (lambda z_: np.exp(-np.pi*z_/np.sqrt(1-z_**2)) - 0.2) if eta == 0 else (lambda z_: mp_formula(z_, eta) - 0.2)
    print(f"  eta={eta}: zeta = {brentq(f, 0.05, 0.99):.3f}")

# ---------- Q4: simulacao PI descentralizado ----------
Kv, tv, Kb, tb = 2.0, 10.0, 2.5, 2.0
kp1, Ti1 = 0.05, 100.0
kp2, Ti2 = 0.10, 40.0
# checa polos malha diagonal
print("\nQ4 polos malha 1 (diag):", np.roots([tv, 1, Kv*kp1, Kv*kp1/Ti1]))
print("Q4 polos malha 2 (diag):", np.roots([tb, 1, Kb*kp2, Kb*kp2/Ti2]))

def sim(leak):
    r1, r2 = 1.2, 0.8
    def f(t, x):
        v1, v2, y1, y2, I1, I2 = x
        d = leak if t >= 300 else 0.0
        e1, e2 = r1 - y1, r2 - y2
        u1 = kp1*(e1 + I1/Ti1); u2 = kp2*(e2 + I2/Ti2)
        return [(-v1 + Kv*u1)/tv, (-v2 + Kb*u2)/tb,
                v1 - v2, v2 - v1 - d, e1, e2]
    return solve_ivp(f, [0, 900], [0, 0, 1, 1, 0, 0], max_step=0.5, dense_output=True)

for leak, name in [(0.0, "q4_sim"), (0.005, "q4_sim_leak")]:
    s = sim(leak)
    t = np.linspace(0, 900, 2000); X = s.sol(t)
    fig, axs = plt.subplots(2, 1, figsize=(6.4, 4.6), sharex=True)
    axs[0].plot(t, X[2], color="#1a6feb", label="$y_1$")
    axs[0].plot(t, X[3], color="#c22", label="$y_2$")
    axs[0].axhline(1.2, ls=":", color="#1a6feb", lw=.8)
    axs[0].axhline(0.8, ls=":", color="#c22", lw=.8)
    if leak: axs[0].axvline(300, color="k", ls="--", lw=.8)
    axs[0].set_ylabel("nível (m)"); axs[0].legend(fontsize=9); axs[0].grid(alpha=.3)
    axs[1].plot(t, X[0], color="#1a6feb", label="$v_1$")
    axs[1].plot(t, X[1], color="#c22", label="$v_2$")
    if leak: axs[1].axvline(300, color="k", ls="--", lw=.8)
    axs[1].set_ylabel("vazão (m³/s)"); axs[1].set_xlabel("Tempo (s)")
    axs[1].legend(fontsize=9); axs[1].grid(alpha=.3)
    ttl = "sem vazamento" if leak == 0 else "vazamento $d=0{,}005$ m³/s a partir de $t=300$ s"
    axs[0].set_title(f"Q4 — PI descentralizado ({ttl})")
    fig.tight_layout(); fig.savefig(f"{FIGDIR}/{name}.pdf"); plt.close(fig)
    print(f"leak={leak}: y1(900)={X[2,-1]:.3f} y2(900)={X[3,-1]:.3f} v1(900)={X[0,-1]:.3f} v2(900)={X[1,-1]:.3f}")

# Routh Q4(d): verifica alguns ganhos P puros
a, b = 1/tv, 1/tb
for k1, k2 in [(0.1, 0.1), (1, 1), (10, 10), (0.5, 2)]:
    c1, c2 = Kv*k1/(2*tv), Kb*k2/(2*tb)
    p = [1, a+b, a*b + 2*c1 + 2*c2, 2*(a*c2 + b*c1)]
    print(f"k1={k1} k2={k2} raizes:", np.roots(p))

#!/bin/bash
# Abre o .grc no gnuradio-companion REAL sob xvfb+openbox (GDK_SCALE=2), captura a tela do canvas
# e o dialogo de propriedades do Chunks to Symbols. uso: grc_screenshot.sh <mod>
mod=$1; B=/home/user/ufam/simbolos_iq
export PATH=/usr/bin:$PATH XDG_RUNTIME_DIR=/tmp/runtime-root GDK_SCALE=2
mkdir -p $XDG_RUNTIME_DIR; chmod 700 $XDG_RUNTIME_DIR
Xvfb :77 -screen 0 2400x1200x24 >/dev/null 2>&1 &
XP=$!; sleep 2; export DISPLAY=:77
openbox >/dev/null 2>&1 &
(cd /; /usr/bin/python3.12 /usr/bin/gnuradio-companion $B/grc/aula2_${mod}_MATHEUS.grc >$B/build/grc_${mod}.log 2>&1) &
GP=$!; sleep 15
W=$(xdotool search --name "aula2_${mod}" | head -1)
xdotool windowmove $W 0 0; xdotool windowsize $W 2400 1200; sleep 3
import -window root $B/build/grc_${mod}_screen.png
# duplo clique no bloco Chunks to Symbols (coordenadas de tela, ver LEIAME) para abrir propriedades
xdotool mousemove ${CX:-880} ${CY:-770} click --repeat 2 --delay 120 1; sleep 3
import -window root $B/build/grc_${mod}_dialog.png
kill $GP $XP 2>/dev/null; pkill openbox

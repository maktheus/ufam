#!/usr/bin/python3.12
"""Roda o flowgraph REAL (compilado pelo grcc) com sinks Qt sob xvfb e grava as janelas.
uso: captura_qt.py <bpsk|qpsk|16qam>"""
import sys, signal
mod = sys.argv[1]
B = "/home/user/ufam/simbolos_iq"
sys.path.insert(0, B + "/build")
from PyQt5 import Qt
cls = getattr(__import__(f"aula2_{mod}_MATHEUS"), f"aula2_{mod}_MATHEUS")
app = Qt.QApplication(sys.argv)
tb = cls(); tb.resize(1100, 950); tb.start(); tb.show()
def grab():
    app.processEvents()
    tb.grab().save(f"{B}/build/qt_{mod}_janela.png")
    tb._qtgui_const_sink_x_0_win.grab().save(f"{B}/build/qt_{mod}_const.png")
    tb._qtgui_time_sink_x_0_win.grab().save(f"{B}/build/qt_{mod}_time.png")
    tb.stop(); tb.wait(); app.quit()
Qt.QTimer.singleShot(4000, grab)
app.exec_()

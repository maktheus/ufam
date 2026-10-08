#!/usr/bin/python3.12
"""Gera os .grc (GNU Radio 3.10) de BPSK, QPSK e 16-QAM e os flowgraphs de captura (Head + Vector Sink)."""
import sys, os
BASE = "/home/user/ufam/simbolos_iq"

TABLES = {
 "bpsk": (2, "[-1+0j, 1+0j]"),
 "qpsk": (4, "[(-1-1j)/2**0.5, (-1+1j)/2**0.5, (1-1j)/2**0.5, (1+1j)/2**0.5]"),
}
g = {"00": -3, "01": -1, "11": 1, "10": 3}
lv = [-3, -1, 1, 3]  # indice a (2 bits) -> nivel via Gray: 00->-3,01->-1,10->+3,11->+1
gray = {0: -3, 1: -1, 2: 3, 3: 1}
q16 = ", ".join("(%d%+dj)/10**0.5" % (gray[a], gray[b]) for a in range(4) for b in range(4))
TABLES["16qam"] = (16, "[" + q16 + "]")
NAMES = {"bpsk": "BPSK", "qpsk": "QPSK", "16qam": "16-QAM"}

def blk(id_, name, params, x, y, states_extra=""):
    p = "\n".join("    %s: %s" % (k, v) for k, v in params.items())
    return f"""- name: {name}
  id: {id_}
  parameters:
{p}
  states:
    coordinate: [{x}, {y}]
    rotation: 0
    state: enabled
"""

def head(mod, title, gen_id, gui):
    return f"""options:
  parameters:
    author: Matheus Serrao Uchoa
    catch_exceptions: 'True'
    category: '[GRC Hier Blocks]'
    cmake_opt: ''
    comment: ''
    copyright: ''
    description: ''
    gen_cmake: 'On'
    gen_linking: dynamic
    generate_options: {'qt_gui' if gui else 'no_gui'}
    hier_block_src_path: '.:'
    id: {gen_id}
    max_nouts: '0'
    output_language: python
    placement: (0,0)
    qt_qss_theme: ''
    realtime_scheduling: ''
    run: 'True'
    run_command: '{{python}} -u {{filename}}'
    run_options: prompt
    sizing_mode: fixed
    thread_safe_setters: ''
    title: {title}
    window_size: (1000,1000)
  states:
    coordinate: [8, 8]
    rotation: 0
    state: enabled

blocks:
"""

def flow(mod):
    M, tab = TABLES[mod]
    N = NAMES[mod]
    s = head(mod, f"Aula 2 - {N} - Matheus", f"aula2_{mod}_MATHEUS", True)
    s += blk("variable", "samp_rate", {"comment": "''", "value": "32000"}, 8, 100)
    s += blk("analog_random_source_x", "analog_random_source_x_0",
             {"affinity": "''", "alias": "''", "comment": "''", "max": str(M), "maxoutbuf": "0",
              "min": "0", "minoutbuf": "0", "num_samps": "100000", "repeat": "'True'", "type": "byte"}, 40, 200)
    s += blk("digital_chunks_to_symbols_xx", "digital_chunks_to_symbols_xx_0",
             {"affinity": "''", "alias": "''", "comment": "''", "dimension": "1", "in_type": "byte",
              "maxoutbuf": "0", "minoutbuf": "0", "num_ports": "1", "out_type": "complex",
              "symbol_table": "'" + tab + "'"}, 250, 200)
    s += blk("blocks_throttle", "blocks_throttle_0",
             {"affinity": "''", "alias": "''", "comment": "''", "ignoretag": "True", "maxoutbuf": "0",
              "minoutbuf": "0", "samples_per_second": "samp_rate", "type": "complex", "vlen": "1"}, 480, 212)
    s += blk("qtgui_const_sink_x", "qtgui_const_sink_x_0",
             {"affinity": "''", "alias": "''", "autoscale": "False", "axislabels": "True", "color1": "'blue'",
              "comment": "''", "grid": "True", "gui_hint": "'0,0,1,1'", "label1": "''", "legend": "True",
              "marker1": "0", "name": f"'Constelacao {N}'", "nconnections": "1", "size": "1024",
              "tr_chan": "0", "tr_level": "0.0", "tr_mode": "qtgui.TRIG_MODE_FREE", "tr_slope": "qtgui.TRIG_SLOPE_POS",
              "tr_tag": "''", "type": "complex", "update_time": "0.10", "xmax": "2", "xmin": "-2",
              "ymax": "2", "ymin": "-2"}, 680, 110)
    s += blk("qtgui_time_sink_x", "qtgui_time_sink_x_0",
             {"affinity": "''", "alias": "''", "comment": "''", "ctrlpanel": "False", "entags": "True",
              "grid": "True", "gui_hint": "'1,0,1,1'", "label1": "'I (fase)'", "label2": "'Q (quadratura)'",
              "legend": "True", "marker1": "-1", "marker2": "-1", "name": f"'Dominio do tempo {N}'",
              "nconnections": "1", "size": "64", "srate": "samp_rate", "stemplot": "False",
              "tr_chan": "0", "tr_delay": "0", "tr_level": "0.0", "tr_mode": "qtgui.TRIG_MODE_FREE",
              "tr_slope": "qtgui.TRIG_SLOPE_POS", "tr_tag": "''", "type": "complex",
              "update_time": "0.10", "width1": "1", "width2": "1", "ylabel": "'Amplitude'",
              "yunit": "''", "ymax": "1.5", "ymin": "-1.5", "axislabels": "True", "autoscale": "False"}, 680, 270)
    s += """
connections:
- [analog_random_source_x_0, '0', digital_chunks_to_symbols_xx_0, '0']
- [blocks_throttle_0, '0', qtgui_const_sink_x_0, '0']
- [blocks_throttle_0, '0', qtgui_time_sink_x_0, '0']
- [digital_chunks_to_symbols_xx_0, '0', blocks_throttle_0, '0']

metadata:
  file_format: 1
  grc_version: 3.10.9.2
"""
    return s

def capture(mod, nsym, outpath):
    M, tab = TABLES[mod]
    s = head(mod, f"Captura {mod}", f"captura_{mod}", False)
    s += blk("variable", "samp_rate", {"comment": "''", "value": "32000"}, 8, 100)
    s += blk("analog_random_source_x", "analog_random_source_x_0",
             {"affinity": "''", "alias": "''", "comment": "''", "max": str(M), "maxoutbuf": "0",
              "min": "0", "minoutbuf": "0", "num_samps": "100000", "repeat": "'True'", "type": "byte"}, 80, 260)
    s += blk("digital_chunks_to_symbols_xx", "digital_chunks_to_symbols_xx_0",
             {"affinity": "''", "alias": "''", "comment": "''", "dimension": "1", "in_type": "byte",
              "maxoutbuf": "0", "minoutbuf": "0", "num_ports": "1", "out_type": "complex",
              "symbol_table": "'" + tab + "'"}, 380, 260)
    s += blk("blocks_head", "blocks_head_0",
             {"affinity": "''", "alias": "''", "comment": "''", "maxoutbuf": "0", "minoutbuf": "0",
              "num_items": str(nsym), "type": "complex", "vlen": "1"}, 700, 200)
    s += blk("blocks_vector_sink_x", "blocks_vector_sink_x_0",
             {"affinity": "''", "alias": "''", "comment": "''", "reserve_items": "1024", "type": "complex", "vlen": "1"}, 900, 200)
    s += """
connections:
- [analog_random_source_x_0, '0', digital_chunks_to_symbols_xx_0, '0']
- [blocks_head_0, '0', blocks_vector_sink_x_0, '0']
- [digital_chunks_to_symbols_xx_0, '0', blocks_head_0, '0']

metadata:
  file_format: 1
  grc_version: 3.10.9.2
"""
    return s

for mod in TABLES:
    open(f"{BASE}/grc/aula2_{mod}_MATHEUS.grc", "w").write(flow(mod))
    os.makedirs(f"{BASE}/scripts/captura", exist_ok=True)
    open(f"{BASE}/scripts/captura/captura_{mod}.grc", "w").write(capture(mod, 5000, None))
print("ok")

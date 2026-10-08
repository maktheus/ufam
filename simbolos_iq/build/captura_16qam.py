#!/usr/bin/env python3
# -*- coding: utf-8 -*-

#
# SPDX-License-Identifier: GPL-3.0
#
# GNU Radio Python Flow Graph
# Title: Captura 16qam
# Author: Matheus Serrao Uchoa
# GNU Radio version: 3.10.9.2

from gnuradio import blocks
import numpy
from gnuradio import digital
from gnuradio import gr
from gnuradio.filter import firdes
from gnuradio.fft import window
import sys
import signal
from argparse import ArgumentParser
from gnuradio.eng_arg import eng_float, intx
from gnuradio import eng_notation




class captura_16qam(gr.top_block):

    def __init__(self):
        gr.top_block.__init__(self, "Captura 16qam", catch_exceptions=True)

        ##################################################
        # Variables
        ##################################################
        self.samp_rate = samp_rate = 32000

        ##################################################
        # Blocks
        ##################################################

        self.digital_chunks_to_symbols_xx_0 = digital.chunks_to_symbols_bc([(-3-3j)/10**0.5, (-3-1j)/10**0.5, (-3+3j)/10**0.5, (-3+1j)/10**0.5, (-1-3j)/10**0.5, (-1-1j)/10**0.5, (-1+3j)/10**0.5, (-1+1j)/10**0.5, (3-3j)/10**0.5, (3-1j)/10**0.5, (3+3j)/10**0.5, (3+1j)/10**0.5, (1-3j)/10**0.5, (1-1j)/10**0.5, (1+3j)/10**0.5, (1+1j)/10**0.5], 1)
        self.blocks_vector_sink_x_0 = blocks.vector_sink_c(1, 1024)
        self.blocks_head_0 = blocks.head(gr.sizeof_gr_complex*1, 5000)
        self.analog_random_source_x_0 = blocks.vector_source_b(list(map(int, numpy.random.randint(0, 16, 100000))), True)


        ##################################################
        # Connections
        ##################################################
        self.connect((self.analog_random_source_x_0, 0), (self.digital_chunks_to_symbols_xx_0, 0))
        self.connect((self.blocks_head_0, 0), (self.blocks_vector_sink_x_0, 0))
        self.connect((self.digital_chunks_to_symbols_xx_0, 0), (self.blocks_head_0, 0))


    def get_samp_rate(self):
        return self.samp_rate

    def set_samp_rate(self, samp_rate):
        self.samp_rate = samp_rate




def main(top_block_cls=captura_16qam, options=None):
    tb = top_block_cls()

    def sig_handler(sig=None, frame=None):
        tb.stop()
        tb.wait()

        sys.exit(0)

    signal.signal(signal.SIGINT, sig_handler)
    signal.signal(signal.SIGTERM, sig_handler)

    tb.start()

    try:
        input('Press Enter to quit: ')
    except EOFError:
        pass
    tb.stop()
    tb.wait()


if __name__ == '__main__':
    main()

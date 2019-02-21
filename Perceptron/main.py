# -*- coding: utf-8 -*-
"""
Created on Thu Feb 21 09:36:51 2019

@author: cek11kmi & dic15afr
"""
import matplotlib.pyplot as plt
import numpy as np

french_letter_count = [36961, 43621, 15964, 36231, 29945, 40588, 75255, 37709, 30899, 25486, 37497, 40398, 74105, 76725, 18317]
french_A_count = [2503, 2992, 1042, 2487, 2014, 2805, 5062, 2643, 2126, 1784, 2641, 2766, 5047, 5312, 1215]
english_letter_count = [35680, 42514, 15162, 35298, 29800, 40255, 74532, 37464, 31030, 24843, 36172, 39552, 72545, 75352, 18031]
english_A_count = [2217, 2761, 990, 2274, 1865, 2606, 4805, 2396, 1993, 1627, 2375, 2560, 4597, 4871, 1119]

def main():
    french_ltr_cnt_norm = normalize(french_letter_count)
    french_A_cnt_norm = normalize(french_A_count)
    english_ltr_cnt_norm = normalize(english_letter_count)
    english_A_cnt_norm = normalize(english_A_count)
    
    fig = plt.figure()


def normalize(list):
    return [i / max(list) for i in list]   
    
main()

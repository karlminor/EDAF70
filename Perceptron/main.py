# -*- coding: utf-8 -*-
"""
Created on Thu Feb 21 09:36:51 2019

@author: cek11kmi & dic15afr
"""
import matplotlib.pyplot as plt
import numpy as np
import random

french_letter_count = [36961, 43621, 15964, 36231, 29945, 40588, 75255, 37709, 30899, 25486, 37497, 40398, 74105, 76725, 18317]
french_A_count = [2503, 2992, 1042, 2487, 2014, 2805, 5062, 2643, 2126, 1784, 2641, 2766, 5047, 5312, 1215]
english_letter_count = [35680, 42514, 15162, 35298, 29800, 40255, 74532, 37464, 31030, 24843, 36172, 39552, 72545, 75352, 18031]
english_A_count = [2217, 2761, 990, 2274, 1865, 2606, 4805, 2396, 1993, 1627, 2375, 2560, 4597, 4871, 1119]

def main():
    max_value = max(french_letter_count + english_letter_count)
    fr_ltr_cnt_norm = normalize(french_letter_count, max_value)
    eng_ltr_cnt_norm = normalize(english_letter_count, max_value)
        
    max_value = max(french_A_count + english_A_count)
    fr_A_cnt_norm = normalize(french_A_count, max_value)
    eng_A_cnt_norm = normalize(english_A_count, max_value)
    
    

    plt.plot(fr_ltr_cnt_norm, fr_A_cnt_norm, 'ro')
    plt.plot(eng_ltr_cnt_norm, eng_A_cnt_norm, 'bo')
    w = linear_regression(fr_ltr_cnt_norm, fr_A_cnt_norm)
    x = np.array(fr_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'r')
    w = linear_regression(eng_ltr_cnt_norm, eng_A_cnt_norm)
    x = np.array(eng_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'b')
    

def normalize(list, max_value):
    return [i / max_value for i in list]

def linear_regression(list1, list2):
    alpha = 0.1
    w = [random.choice(list1), random.choice(list2)]
    while True:
        w_old = w.copy()
        sum = 0
        for i in range(len(list1)-1):
            sum += list2[i] - (w[0] + w[1]*list1[i])
        w[0] = w[0] + alpha*sum
        sum = 0
        for i in range(len(list2)-1):
            sum += (list2[i] - (w[0] + w[1]*list1[i]))*list1[i]
        w[1] = w[1] + alpha*sum 
        #print(w)
        if(max(abs(w_old[0] - w[0]),abs(w_old[1] - w[1])) < 0.00001):
            break
    return w   

main()
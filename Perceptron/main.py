# -*- coding: utf-8 -*-
"""
Created on Thu Feb 21 09:36:51 2019

@author: cek11kmi & dic15afr
"""
import matplotlib.pyplot as plt
import numpy as np
import os
import random
import math
from math import exp

french_letter_count = [36961, 43621, 15964, 36231, 29945, 40588, 75255, 37709, 30899, 25486, 37497, 40398, 74105, 76725, 18317]
french_A_count = [2503, 2992, 1042, 2487, 2014, 2805, 5062, 2643, 2126, 1784, 2641, 2766, 5047, 5312, 1215]
english_letter_count = [35680, 42514, 15162, 35298, 29800, 40255, 74532, 37464, 31030, 24843, 36172, 39552, 72545, 75352, 18031]
english_A_count = [2217, 2761, 990, 2274, 1865, 2606, 4805, 2396, 1993, 1627, 2375, 2560, 4597, 4871, 1119]

def linear_regression_batch():
    max_value = max(french_letter_count + english_letter_count)
    fr_ltr_cnt_norm = normalize(french_letter_count, max_value)
    eng_ltr_cnt_norm = normalize(english_letter_count, max_value)
        
    max_value = max(french_A_count + english_A_count)
    fr_A_cnt_norm = normalize(french_A_count, max_value)
    eng_A_cnt_norm = normalize(english_A_count, max_value)

    plt.plot(fr_ltr_cnt_norm, fr_A_cnt_norm, 'ro', label='French')
    plt.plot(eng_ltr_cnt_norm, eng_A_cnt_norm, 'bo', label='English')
    w = linear_regression_batch_help(fr_ltr_cnt_norm, fr_A_cnt_norm)
    x = np.array(fr_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'r', label='French regression line')
    w = linear_regression_batch_help(eng_ltr_cnt_norm, eng_A_cnt_norm)
    x = np.array(eng_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'b', label='English regression line')
    plt.suptitle("Batch linear regression")
    plt.xlabel('Normalized letter count per chapter')
    plt.ylabel('Normalized count of letter a per chapter')
    plt.legend()
    plt.show()
    
def linear_regression_stochastic():
    max_value = max(french_letter_count + english_letter_count)
    fr_ltr_cnt_norm = normalize(french_letter_count, max_value)
    eng_ltr_cnt_norm = normalize(english_letter_count, max_value)
        
    max_value = max(french_A_count + english_A_count)
    fr_A_cnt_norm = normalize(french_A_count, max_value)
    eng_A_cnt_norm = normalize(english_A_count, max_value)

    plt.plot(fr_ltr_cnt_norm, fr_A_cnt_norm, 'ro', label='French')
    plt.plot(eng_ltr_cnt_norm, eng_A_cnt_norm, 'bo', label='English')
    w = linear_regression_stoch_help(fr_ltr_cnt_norm, fr_A_cnt_norm)
    x = np.array(fr_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'r', label='French regression line')
    w = linear_regression_stoch_help(eng_ltr_cnt_norm, eng_A_cnt_norm)
    x = np.array(eng_ltr_cnt_norm)
    plt.plot(x,w[1]*x+w[0], 'b', label='English regression line')
    plt.suptitle("Stochastic linear regression")
    plt.xlabel('Normalized letter count per chapter')
    plt.ylabel('Normalized count of letter a per chapter')
    plt.legend()
    plt.show()
    

def normalize(list, max_value):
    return [i / max_value for i in list]

def linear_regression_batch_help(list1, list2):
    alpha = 0.1
    w = [list1[0], list2[0]]
    while True:
        w_old = w.copy()
        sum = 0
        for i in range(len(list1)):
            sum += list2[i] - (w[0] + w[1]*list1[i])
        w[0] = w[0] + alpha*sum
        sum = 0
        for i in range(len(list2)):
            sum += (list2[i] - (w[0] + w[1]*list1[i]))*list1[i]
        w[1] = w[1] + alpha*sum 
        #print(w)
        if(max(abs(w_old[0] - w[0]),abs(w_old[1] - w[1])) < 0.00001):
            break
    return w   

def linear_regression_stoch_help(list1, list2):
    alpha = 0.05
    w = [list1[0], list2[0]]
    while True:
        for i in range(len(list1)):
            w_old = w.copy()
            w[0] = w[0] + alpha*2*(list2[i] - (w[1]*list1[i] + w[0]))
            w[1] = w[1] + alpha*2*(list2[i] - (w[1]*list1[i] + w[0]))*list1[i]
            if(max(abs(w_old[0] - w[0]),abs(w_old[1] - w[1])) < 0.000005):
                return w 
      
def perceptron():
    match_rate = 0.99
    max_iterations = 10000
    
    en_nodes = libsvm_reader("data/salammbo_en.txt")
    en_y = calc_y(0, len(en_nodes))  
    fr_nodes = libsvm_reader("data/salammbo_fr.txt")     
    fr_y = calc_y(1, len(fr_nodes))
    scale(en_nodes, fr_nodes)
    y_list = en_y + fr_y
    nodes = en_nodes + fr_nodes
    
    w = [0,0,0]
    matches = 0
    counter = 0
    alpha = 1
    size = len(nodes)
    order = [i for i in range(size)]
    
    while True:
        if matches > match_rate*size or counter > max_iterations:
            break
        
        matches = 0
        random_order = order.copy()
        random.shuffle(random_order)
        
        for i in range(size):
            index = random_order[i]
            x = [1, nodes[index][0].v, nodes[index][1].v]
            y = y_list[index]
            
            res = y - h(w,x)
            
            if res == 0:
                matches += 1
            
            for n in range(len(w)):
                w[n] = w[n] + alpha*res*x[n]
        counter += 1 
    letter_cnt = []
    a_cnt = []
    for i in range(size):
        letter_cnt.append(nodes[i][0].v)
        a_cnt.append(nodes[i][1].v)
    w_line = []
    x_hlp = [i * 1/size for i in range(0,size+1)]
    for i in range(len(x_hlp)):
        w_line.append(-((w[0]/w[2]) * x_hlp[i] + (w[1]/w[2]) * x_hlp[i]))
    print(w)
    plt.plot(x_hlp, w_line, label='Classification line')
        
    plt.plot(letter_cnt[:14], a_cnt[:14], 'bo', label='English')
    plt.plot(letter_cnt[15:], a_cnt[15:], 'ro', label='French')
    plt.suptitle("Perceptron results")
    plt.xlabel('Normalized letter count per chapter')
    plt.ylabel('Normalized count of letter a per chapter')
    plt.legend()
    plt.show()
    
    
def leave_one_out_validation():
    en_nodes = libsvm_reader("data/salammbo_en.txt")
    en_y = calc_y(0, len(en_nodes))  
    fr_nodes = libsvm_reader("data/salammbo_fr.txt")     
    fr_y = calc_y(1, len(fr_nodes))
    scale(en_nodes, fr_nodes)
    y_list = en_y + fr_y
    nodes = en_nodes + fr_nodes
    
    matches = 0
    
    leave_one_out_nodes = []
    leave_one_out_y = []
    for n in range(len(nodes)):
        for i in range(len(nodes)):
            if(n != i):
                leave_one_out_nodes.append(nodes[i])
                leave_one_out_y.append(y_list[i])   
        w = leave_one_out_validation_helper(leave_one_out_nodes, leave_one_out_y)
        y = y_list[n]
        x = [1, nodes[n][0].v,  nodes[n][1].v]
        res = y - h(w,x)
        if(res == 0):
            matches += 1
    print(matches)
    
def leave_one_out_validation_helper(x_nodes, y_list):
    match_rate = 0.99
    max_iterations = 10000
    
    w = [0,0,0]
    matches = 0
    counter = 0
    alpha = 1
    size = len(x_nodes)
    order = [i for i in range(size)]
    
    while True:
        if matches > match_rate*size or counter > max_iterations:
            break
        
        matches = 0
        random_order = order.copy()
        random.shuffle(random_order)
        
        for i in range(size):
            index = random_order[i]
            x = [1, x_nodes[index][0].v, x_nodes[index][1].v]
            y = y_list[index]
            
            res = y - h(w,x)
            
            if res == 0:
                matches += 1
            
            for n in range(len(w)):
                w[n] = w[n] + alpha*res*x[n]
        counter += 1 
    return w
    

            

def libsvm_reader(filename):
    f = open(filename, "r")
    listoflists= []
    for line in f:
        lists = []
        index = 0
        for value in line.split():
            index += 1
            lists.append(SVM_Node(index, int(value)))
        listoflists.append(lists)
    return listoflists
      
class SVM_Node:
    def __init__(self, index, value):
        self.i = index
        self.v = value

def scale(list1,list2):
    max = [0,0]
    for l in list1:
        for i in range(len(l)):
            if l[i].v > max[i]:
                max[i] = l[i].v
    for l in list2:
        for i in range(len(l)):
            if l[i].v > max[i]:
                max[i] = l[i].v
    for l in list1:
        for i in range(len(l)):
            l[i].v = l[i].v / max[i]
    for l in list2:
        for i in range(len(l)):
            l[i].v = l[i].v / max[i]      
            
def h(w,x):
    product = 0
    for i in range(len(w)):
        product += w[i]*x[i]
    if(product >= 0):
        return 1
    else:
        return 0
    
def calc_y(classifier,size):
    list = []
    for i in range(size):
        list.append(classifier)
    return list

def h_logistic(w,x):
    product = 0
    for i in range(len(w)):
        product += w[i]*x[i]
        
    return 1 / (1 + exp(-product))
        
    


def logistic_regression():
    match_rate = 0.99
    max_iterations = 10000
    
    en_nodes = libsvm_reader("data/salammbo_en.txt")
    en_y = calc_y(0, len(en_nodes))  
    fr_nodes = libsvm_reader("data/salammbo_fr.txt")     
    fr_y = calc_y(1, len(fr_nodes))
    scale(en_nodes, fr_nodes)
    y_list = en_y + fr_y
    nodes = en_nodes + fr_nodes
    
    w = [0,0,0]
    matches = 0
    counter = 0
    alpha = 1
    size = len(nodes)
    order = [i for i in range(size)]
    
    while True:
        if matches > match_rate*size or counter > max_iterations:
            break
        
        matches = 0
        random_order = order.copy()
        random.shuffle(random_order)
        
        for i in range(size):
            index = random_order[i]
            x = [1, nodes[index][0].v, nodes[index][1].v]
            y = y_list[index]
            
            h_reg = h_logistic(w,x)
            res = y - round(h_reg)
            
            if res == 0:
                matches += 1
            
            for n in range(len(w)):
                w[n] = w[n] + alpha * res * h_reg * (1 - h_reg) * x[n]
        counter += 1 
    letter_cnt = []
    a_cnt = []
    for i in range(size):
        letter_cnt.append(nodes[i][0].v)
        a_cnt.append(nodes[i][1].v)
    w_line = []
    x_hlp = [i * 1/size for i in range(0,size+1)]
    for i in range(len(x_hlp)):
        w_line.append(-((w[0]/w[2]) * x_hlp[i] + (w[1]/w[2]) * x_hlp[i]))
    print(w)
    plt.plot(x_hlp, w_line, label='Classification line')
        
    plt.plot(letter_cnt[:14], a_cnt[:14], 'bo', label='English')
    plt.plot(letter_cnt[15:], a_cnt[15:], 'ro', label='French')
    plt.suptitle("Logistic regression")
    plt.xlabel('Normalized letter count per chapter')
    plt.ylabel('Normalized count of letter a per chapter')
    plt.legend()
    plt.show()
    
    
    
def leave_one_out_validation_logistic():
    en_nodes = libsvm_reader("data/salammbo_en.txt")
    en_y = calc_y(0, len(en_nodes))  
    fr_nodes = libsvm_reader("data/salammbo_fr.txt")     
    fr_y = calc_y(1, len(fr_nodes))
    scale(en_nodes, fr_nodes)
    y_list = en_y + fr_y
    nodes = en_nodes + fr_nodes
    
    matches = 0
    
    leave_one_out_nodes = []
    leave_one_out_y = []
    for n in range(len(nodes)):
        for i in range(len(nodes)):
            if(n != i):
                leave_one_out_nodes.append(nodes[i])
                leave_one_out_y.append(y_list[i])   
        w = leave_one_out_validation_helper_logistic(leave_one_out_nodes, leave_one_out_y)
        y = y_list[n]
        x = [1, nodes[n][0].v,  nodes[n][1].v]
        res = y - h(w,x)
        if(res == 0):
            matches += 1
    print(matches)
    
def leave_one_out_validation_helper_logistic(x_nodes, y_list):
    match_rate = 0.99
    max_iterations = 10000
    
    w = [0,0,0]
    matches = 0
    counter = 0
    alpha = 1
    size = len(x_nodes)
    order = [i for i in range(size)]
    
    while True:
        if matches > match_rate*size or counter > max_iterations:
            break
        
        matches = 0
        random_order = order.copy()
        random.shuffle(random_order)
        
        for i in range(size):
            index = random_order[i]
            x = [1, x_nodes[index][0].v, x_nodes[index][1].v]
            y = y_list[index]
            
            h_reg = h_logistic(w,x)
            res = y - round(h_reg)
            
            if res == 0:
                matches += 1
            
            for n in range(len(w)):
                w[n] = w[n] + alpha * res * h_reg * (1 - h_reg) * x[n]
        counter += 1 
    return w
    
if __name__ == '__main__':
    logistic_regression()
    leave_one_out_validation_logistic()

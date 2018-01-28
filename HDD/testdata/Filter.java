package com.bbn.filter;

public interface Filter<T> {
    T filter(T in);
}
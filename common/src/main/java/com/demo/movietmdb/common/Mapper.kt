package com.demo.movietmdb.common

interface Mapper<F, T> {
    fun mapFrom(from: F): T
}
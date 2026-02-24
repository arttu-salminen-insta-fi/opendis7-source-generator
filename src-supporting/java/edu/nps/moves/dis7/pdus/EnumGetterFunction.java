package edu.nps.moves.dis7.pdus;

@FunctionalInterface
public interface EnumGetterFunction<T, R> {
    R apply(T t) throws EnumNotFoundException;
}

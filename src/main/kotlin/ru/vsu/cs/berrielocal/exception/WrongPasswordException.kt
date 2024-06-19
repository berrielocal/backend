package ru.vsu.cs.berrielocal.exception

class WrongPasswordException(msg: String = "401: Wrong password", cause: Throwable? = null)
    : RuntimeException(msg, cause)
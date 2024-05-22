package ru.vsu.cs.berrielocal.exception

class UnauthorizedException(msg: String = "401: Unauthorized", cause: Throwable? = null)
    : RuntimeException(msg, cause)
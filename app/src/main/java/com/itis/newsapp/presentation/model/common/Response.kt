package com.itis.newsapp.presentation.model.common


class Response<T> private constructor(
    val status: Status,
    val data: T?,
    val errorMessage: String?
) {
    companion object {

        fun <T> loading(): Response<T> {
            return Response(
                Status.LOADING,
                null,
                null
            )
        }

        fun <T> success(data: T): Response<T> {
            return Response(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: String): Response<T> {
            return Response(
                Status.ERROR,
                null,
                error
            )
        }
    }
}
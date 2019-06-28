package com.itis.newsapp.presentation.rx.transformer

import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.MaybeTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PresentationMaybeTransformer<T> : MaybeTransformer<T, T> {
    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
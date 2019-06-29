package com.itis.newsapp.data.network.api

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.exception.TimeOutException
import com.itis.newsapp.data.network.exception.UnknownException
import com.itis.newsapp.data.network.exception.domain.DomainException
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.data.network.pojo.response.source.Sources
import io.reactivex.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NewsApiRequestDecorator(val apiRequest: NewsApiRequest) : NewsApiRequest {

    companion object {
        fun processApiThrowable(t: Throwable): Throwable {
            return when(t) {
                is DomainException -> t
                is UnknownHostException -> NoInternetConnectionException()
                is SocketTimeoutException -> TimeOutException()
                else -> UnknownException()
            }
        }
    }

    private class ApiRequestErrorSingleTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Single.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorObservableTransformer<T> : ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Observable.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorCompletableTransformer : CompletableTransformer {
        override fun apply(upstream: Completable): CompletableSource {
            return upstream.onErrorResumeNext { t: Throwable -> Completable.error(processApiThrowable(t)) }
        }
    }


    override fun getSourcesSingle(): Single<Sources> {
        return apiRequest
            .getSourcesSingle()
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getNews(sources: String): Single<News> {
        return apiRequest
            .getNews(sources)
            .compose(ApiRequestErrorSingleTransformer())
    }
}


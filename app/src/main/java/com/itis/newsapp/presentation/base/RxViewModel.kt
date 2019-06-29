package com.itis.newsapp.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.itis.newsapp.domain.usecase.LoadSourcesUseCase
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxViewModel (application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun Disposable.disposeWhenDestroy() {
        compositeDisposable.add(this)
    }
}
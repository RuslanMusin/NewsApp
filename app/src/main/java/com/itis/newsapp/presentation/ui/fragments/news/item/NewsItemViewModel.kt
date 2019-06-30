package com.itis.newsapp.presentation.ui.fragments.news.item

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itis.newsapp.domain.dto.input.ArticleInputMapper
import com.itis.newsapp.domain.usecase.GetIsArticleSavedUseCase
import com.itis.newsapp.domain.usecase.SaveArticleUseCase
import com.itis.newsapp.presentation.base.viewmodel.RxViewModel
import com.itis.newsapp.presentation.model.ArticleModel
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.rx.transformer.PresentationCompletableTransformer
import com.itis.newsapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import javax.inject.Inject

class NewsItemViewModel
    @Inject constructor(application: Application)
    : RxViewModel(application)
{

    @Inject
    lateinit var getIsArticleSavedUseCase: GetIsArticleSavedUseCase
    @Inject
    lateinit var saveArticleSavedUseCase: SaveArticleUseCase
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    private val _response = MutableLiveData<Response<Boolean>>()
    val response: LiveData<Response<Boolean>> = _response

    private val _isAddBtnClicked = MutableLiveData<Boolean>()
    val isAddBtnClicked: LiveData<Boolean> = _isAddBtnClicked

    fun initState() {
        _response.value = Response.success(true)
        _isAddBtnClicked.value = false
    }

    fun checkArticleExists(article: ArticleModel) {
        getIsArticleSavedUseCase
            .getIsArticleSavedSingle(article.url)
            .compose(PresentationSingleTransformer())
            .subscribe(
                { response ->
                    _response.value = Response.success(response)
                },
                { throwable ->
                    _response.setValue(
                        Response.error(exceptionProcessor.processException(throwable))
                    )
                }
            ).disposeWhenDestroy()
    }

    fun addArticle(article: ArticleModel) {
        saveArticleSavedUseCase
            .getSaveArticleCompletable(
                ArticleInputMapper.map(article)
            )
            .compose(PresentationCompletableTransformer())
            .subscribe(
                {
                    _response.setValue(Response.success(true))
                },
                { throwable ->
                    _response.setValue(
                        Response.error(exceptionProcessor.processException(throwable))
                    )
                }
            ).disposeWhenDestroy()
    }

    fun setAddBtnClicked() {
        _isAddBtnClicked.value = true
    }

}
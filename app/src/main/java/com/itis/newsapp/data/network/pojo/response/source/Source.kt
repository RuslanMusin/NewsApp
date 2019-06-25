package com.itis.newsapp.data.network.pojo.response.source

import java.io.Serializable

class Source : Serializable {
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var url: String = ""
    var category: String = ""
    var language: String = ""
    var country: String = ""
}
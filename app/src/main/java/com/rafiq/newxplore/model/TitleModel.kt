package com.rafiq.newxplore.model

class TitleModel {
    var id:Int=0
    var title:String=""
    var cover_photo:String=""

    constructor(id: Int, title: String, cover_photo: String) {
        this.id = id
        this.title = title
        this.cover_photo = cover_photo
    }
}
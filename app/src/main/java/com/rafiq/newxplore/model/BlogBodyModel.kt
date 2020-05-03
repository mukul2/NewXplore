package com.rafiq.newxplore.model

class BlogBodyModel {
    var id:Int=0
    var category_id:Int=0
    var title:String=""
    var body:String=""
    var cover_photo:String=""
    var created_at:String=""
    var post_type	:String=""

    constructor(
        id: Int,
        category_id: Int,
        title: String,
        body: String,
        cover_photo: String,
        created_at: String,
        post_type: String
    ) {
        this.id = id
        this.category_id = category_id
        this.title = title
        this.body = body
        this.cover_photo = cover_photo
        this.post_type = post_type
    }
}
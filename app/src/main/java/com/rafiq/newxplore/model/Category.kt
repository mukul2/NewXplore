package com.rafiq.newxplore.model

class Category {
    var id:Int=0
    var parent_id:Int=0
    var title:String=""
    var image:String=""

    constructor(id: Int, parent_id: Int, title: String, image: String) {
        this.id = id
        this.parent_id = parent_id
        this.title = title
        this.image = image
    }
}
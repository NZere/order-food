package project.app.team7cafe.Model

class Food {
    var category_id: String? = null
    var description: String? = null
    var discount: String? = null
    var food_id: String? = null
    var name: String? = null
    var image: String? = null
    var price: String? = null
    var time: String? = null

    constructor() {}
    constructor(
        category_id: String?,
        description: String?,
        discount: String?,
        food_id: String?,
        name: String?,
        image: String?,
        price: String?,
        time: String?
    ) {
        this.category_id = category_id
        this.description = description
        this.discount = discount
        this.food_id = food_id
        this.name = name
        this.image = image
        this.price = price
        this.time = time
    }
}
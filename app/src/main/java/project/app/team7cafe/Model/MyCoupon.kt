package project.app.team7cafe.Model

class MyCoupon {
    var coupon_id: String? = null
    var user_id: String? = null

    constructor()
    constructor(coupon_id: String?, user_id: String?) {
        this.coupon_id = coupon_id
        this.user_id = user_id
    }

}
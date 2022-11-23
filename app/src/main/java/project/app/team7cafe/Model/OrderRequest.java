package project.app.team7cafe.Model;

import java.util.List;

public class OrderRequest {
    private String user_id;
    private String table_id;
    private String total;
    private String coupon;
    private List<Order> foods;

    public OrderRequest() {
    }

    public OrderRequest(String user_id, String table_id, String total, String coupon, List<Order> foods) {
        this.user_id = user_id;
        this.table_id = table_id;
        this.total = total;
        this.coupon = coupon;
        this.foods = foods;
    }
}

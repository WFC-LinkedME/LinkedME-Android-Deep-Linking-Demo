package com.microquation.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.microquation.linkedme.android.moniter.LMTracking;
import com.microquation.linkedme.android.moniter.Order;
import com.microquation.linkedme.android.moniter.ShoppingCart;
import com.microquation.sample.R;

/**
 * 追踪示例
 * Created by LinkedME06 on 16/11/4.
 */

public class TrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_activity);
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onRegister("lipeng");
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onLogin("lipeng");
            }
        });
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onLogout("lipeng");
            }
        });
        findViewById(R.id.browse_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onViewItem("69123456789", "book", "new book", 99);
            }
        });
        findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onAddItemToShoppingCart("69123456789", "book", "new book", 99, 2);
            }
        });
        findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.removeItemFromShoppingCart("69123456789", "book", "new book", 99, 1);
            }
        });
        findViewById(R.id.browse_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCart shoppingCart = ShoppingCart.createShoppingCart().addItem("6912345678901", "Book", "Java", 99, 1);
                LMTracking.onViewShoppingCart(shoppingCart);
            }
        });
        findViewById(R.id.create_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = Order.createOrder("010201611110010", 188, "CNY").addItem("6912345678901", "Book", "Java", 99, 1).addItem("Book", "Android", 89, 1);
                LMTracking.onPlaceOrder("lipeng", order);
            }
        });
        findViewById(R.id.pay_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onPay("tianjunfeng", "1234", 12, "CNY", "支付宝", "69123456789", 12);

            }
        });
        findViewById(R.id.pay_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onPay("tianjunfeng", "1234", 12, "CNY");

            }
        });
        findViewById(R.id.pay_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onPay("tianjunfeng", "1234", 12, "CNY", "支付宝");

            }
        });
        findViewById(R.id.pay_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = Order.createOrder("1234", 198, "CNY").addItem("69123456789", "book", "new book", 99, 1).addItem("book", "new book", 99, 1);
                LMTracking.onPay("tianjunfeng", "1234", 12, "CNY", "支付宝", order);
            }
        });
        findViewById(R.id.pay_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onOrderPaySucc("tianjunfeng", "1234", "12", "CNY", "支付宝");
            }
        });
        findViewById(R.id.add_role).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onCreateRole("haimianbaobao");
            }
        });
        findViewById(R.id.del_role).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onDeleteRole("haimianbaobao");
            }
        });
        findViewById(R.id.sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onSign("paidaxing");
            }
        });
        findViewById(R.id.custom_point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LMTracking.onCustEvent("事件一");
            }
        });


    }
}

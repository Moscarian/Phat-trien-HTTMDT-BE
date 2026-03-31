package com.btl.snaker.service.imp;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.OrderRequest;

public interface OrderServiceImp {
    ResponseData getAllOrders();
    ResponseData getAllOrdersOfUser(long userId);
    ResponseData insertOrder(OrderRequest orderRequest);
    ResponseData getAllProductSells();
    ResponseData solveStatusOrder(long orderId);
}

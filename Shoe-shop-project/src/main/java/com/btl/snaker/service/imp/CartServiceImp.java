package com.btl.snaker.service.imp;

import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.CartRequest;

public interface CartServiceImp {
    ResponseData getCart(long userId);
    ResponseData insertToCart(CartRequest cartRequest);
    Boolean deleteCartItem(long cartItemId);
    ResponseData deleteAllCartItems(long userId);
}

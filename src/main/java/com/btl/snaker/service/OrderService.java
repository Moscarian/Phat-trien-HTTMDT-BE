package com.btl.snaker.service;

import com.btl.snaker.dto.OrderDTO;
import com.btl.snaker.dto.ProductSellDTO;
import com.btl.snaker.entity.*;
import com.btl.snaker.entity.key.KeyOrderItem;
import com.btl.snaker.payload.ResponseData;
import com.btl.snaker.payload.request.OrderItemRequest;
import com.btl.snaker.payload.request.OrderRequest;
import com.btl.snaker.repository.*;
import com.btl.snaker.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService implements OrderServiceImp {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Override
    public ResponseData getAllOrders() {
        ResponseData responseData = new ResponseData();
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            for(OrderItem item : order.getOrderItems()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(order.getId());
                orderDTO.setUserId(order.getUser().getId());
                orderDTO.setProductId(item.getProduct().getId());
                orderDTO.setProductImage(item.getProduct().getImage());
                orderDTO.setProductName(item.getProduct().getName());
                orderDTO.setProductDescription(item.getProduct().getDescription());
                orderDTO.setProductBrand(item.getProduct().getBrand().getName());
                orderDTO.setProductCategory(item.getProduct().getCategory().getName());
                orderDTO.setProductPrice(item.getProduct().getPrice());
                orderDTO.setProductQuantity(item.getQuantity());
                orderDTO.setProductSize(item.getSize());
                orderDTO.setShippingMethod(order.getShippingMethod());
                orderDTO.setPickupTime(order.getDeliveryTime());
                orderDTO.setNotes(order.getNote());
                orderDTO.setFullName(order.getFullName());
                orderDTO.setPhone(order.getPhoneNumber());
                orderDTO.setEmail(order.getEmail());
                orderDTO.setAddress(order.getAddress());
                orderDTO.setStatus(order.getStatus() == 1 ? "Hoàn thành" : "Chờ xử lý");
                orderDTO.setTime(formatDateTime(order.getCreatedAt()));
                orderDTOS.add(orderDTO);
            }
        }
        responseData.setSuccess(true);
        responseData.setData(orderDTOS);
        return responseData;
    }

    public static String formatDateTime(Date inputDate) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, 'ngày' dd 'tháng' MM 'năm' yyyy, H:mm", new Locale("vi", "VN"));
        return outputFormat.format(inputDate);
    }

    @Override
    public ResponseData getAllOrdersOfUser(long userId) {
        ResponseData responseData = new ResponseData();
        User user = userRepository.findById(userId);
        if(user == null) {
            responseData.setSuccess(false);
            responseData.setDescription("User not found");
            return responseData;
        }
        List<Order> orders = orderRepository.findByUser(user);
        if(orders.isEmpty()) {
            responseData.setSuccess(true);
            responseData.setData(new ArrayList<OrderDTO>());
            return responseData;
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders) {
            for(OrderItem item : order.getOrderItems()) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(order.getId());
                orderDTO.setUserId(order.getUser().getId());
                orderDTO.setProductId(item.getProduct().getId());
                orderDTO.setProductImage(item.getProduct().getImage());
                orderDTO.setProductName(item.getProduct().getName());
                orderDTO.setProductDescription(item.getProduct().getDescription());
                orderDTO.setProductBrand(item.getProduct().getBrand().getName());
                orderDTO.setProductCategory(item.getProduct().getCategory().getName());
                orderDTO.setProductPrice(item.getProduct().getPrice());
                orderDTO.setProductQuantity(item.getQuantity());
                orderDTO.setProductSize(item.getSize());
                orderDTO.setShippingMethod(order.getShippingMethod());
                orderDTO.setPickupTime(order.getDeliveryTime());
                orderDTO.setNotes(order.getNote());
                orderDTO.setFullName(order.getFullName());
                orderDTO.setPhone(order.getPhoneNumber());
                orderDTO.setEmail(order.getEmail());
                orderDTO.setAddress(order.getAddress());
                orderDTO.setStatus(order.getStatus() == 1 ? "Hoàn thành" : "Chờ xử lý");
                orderDTO.setTime(formatDateTime(order.getCreatedAt()));
                orderDTOS.add(orderDTO);
            }
        }
        responseData.setSuccess(true);
        responseData.setData(orderDTOS);
        return responseData;
    }

    @Override
    @Transactional
    public ResponseData insertOrder(OrderRequest orderRequest) {
        ResponseData responseData = new ResponseData();
        try {
            User user = userRepository.findById(orderRequest.getUserId());
            if (user == null) {
                responseData.setSuccess(false);
                responseData.setDescription("User not found");
                return responseData;
            }

            long totalAmount = 0;
            List<OrderItem> orderItems = new ArrayList<>();
            List<ProductSize> updatedProductSizes = new ArrayList<>();

            for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemRequests()) {
                Product product = productRepository.findById(orderItemRequest.getProductId());
                if (product == null) {
                    responseData.setSuccess(false);
                    responseData.setDescription("Product not found");
                    return responseData;
                }

                ProductSize productSize = productSizeRepository.findByProductAndSize(product, orderItemRequest.getSize());
                if (productSize == null || productSize.getStock() < orderItemRequest.getQuantity()) {
                    responseData.setSuccess(false);
                    responseData.setDescription("Product size not enough");
                    return responseData;
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(orderItemRequest.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setSize(orderItemRequest.getSize());
                totalAmount += product.getPrice() * orderItemRequest.getQuantity();
                orderItems.add(orderItem);

                productSize.setStock(productSize.getStock() - orderItemRequest.getQuantity());
                updatedProductSizes.add(productSize);
            }

            Order order = new Order();
            order.setUser(user);
            order.setStatus(0);
            order.setAddress(orderRequest.getAddress());
            order.setFullName(orderRequest.getFullName());
            order.setPhoneNumber(orderRequest.getPhoneNumber());
            order.setEmail(orderRequest.getEmail());
            order.setShippingMethod(orderRequest.getShippingMethod());
            order.setDeliveryTime(orderRequest.getDeliveryTime());
            order.setNote(orderRequest.getNote());
            order.setTotalAmount(totalAmount);
            order.setCreatedAt(new Date());

            Order savedOrder = orderRepository.save(order);

            for (OrderItem orderItem : orderItems) {
                KeyOrderItem keyOrderItem = new KeyOrderItem(savedOrder.getId(), orderItem.getProduct().getId());
                orderItem.setKeyOrderItem(keyOrderItem);
                orderItem.setCreatedAt(savedOrder.getCreatedAt());
                orderItemRepository.save(orderItem);
            }

            productSizeRepository.saveAll(updatedProductSizes);

            responseData.setSuccess(true);
            responseData.setDescription("Success");
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setDescription("Fail");
            return responseData;
        }
    }

    @Override
    public ResponseData getAllProductSells() {
        ResponseData responseData = new ResponseData();
        List<Order> orders = orderRepository.findAll();
        Map<Long, Map<Integer, ProductSellDTO>> productSells = new TreeMap<>();
        for(Order order : orders) {
            List<OrderItem> orderItems = order.getOrderItems();

            for(OrderItem orderItem : orderItems) {
                ProductSellDTO productSellDTO;
                long productId = orderItem.getProduct().getId();
                int size = orderItem.getSize();

                Map<Integer, ProductSellDTO> sizeMap = productSells.computeIfAbsent(productId, k -> new HashMap<>());
                if(sizeMap.containsKey(size)) {
                    productSellDTO = sizeMap.get(size);
                    productSellDTO.setQuantity(orderItem.getQuantity() + productSellDTO.getQuantity());
                    productSellDTO.setTotalRevenue(orderItem.getProduct().getPrice() * orderItem.getQuantity() + productSellDTO.getTotalRevenue());
                }
                else{
                    productSellDTO = new ProductSellDTO();
                    productSellDTO.setProductId(productId);
                    productSellDTO.setProductName(orderItem.getProduct().getName());
                    productSellDTO.setProductImage(orderItem.getProduct().getImage());
                    productSellDTO.setProductDescription(orderItem.getProduct().getDescription());
                    productSellDTO.setBrand(orderItem.getProduct().getBrand().getName());
                    productSellDTO.setCategory(orderItem.getProduct().getCategory().getName());
                    productSellDTO.setProductPrice(orderItem.getProduct().getPrice());
                    productSellDTO.setQuantity(orderItem.getQuantity());
                    productSellDTO.setTotalRevenue(orderItem.getProduct().getPrice() * orderItem.getQuantity());
                    productSellDTO.setSize(orderItem.getSize());
                    sizeMap.put(size, productSellDTO);
                }
            }
        }
        List<ProductSellDTO> productSellDTOList = new ArrayList<>();
        for (Map<Integer, ProductSellDTO> sizeMap : productSells.values()) {
            productSellDTOList.addAll(sizeMap.values());
        }
        productSellDTOList.sort((p1, p2) -> {
            int quantityComparison = Integer.compare(p2.getQuantity(), p1.getQuantity());
            if (quantityComparison == 0) {
                return Long.compare(p2.getTotalRevenue(), p1.getTotalRevenue());
            }
            return quantityComparison;
        });
        responseData.setSuccess(true);
        responseData.setData(productSellDTOList);
        return responseData;
    }

    @Override
    public ResponseData solveStatusOrder(long orderId) {
        ResponseData responseData = new ResponseData();
        Order order = orderRepository.findById(orderId);
        if(order == null) {
            responseData.setSuccess(false);
            responseData.setDescription("Order not found");
            return responseData;
        }
        order.setStatus(1);
        orderRepository.save(order);
        responseData.setSuccess(true);
        responseData.setDescription("Success");
        return responseData;
    }
}

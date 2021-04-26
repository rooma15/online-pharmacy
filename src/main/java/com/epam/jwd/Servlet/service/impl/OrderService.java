package com.epam.jwd.Servlet.service.impl;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.dao.impl.OrderDAO;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.service.CommonService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderService implements CommonService<OrderDto> {

    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }


    private final Function<ResultSet, Optional<Order>> adder = resultSet -> {
        try {
            return Optional.of(new Order(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };


    @Override
    public List<OrderDto> findAll() {
        return orderDAO.findAll()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id) {
        return orderDAO.deleteById(id);
    }

    @Override
    public Optional<OrderDto> findById(int id) {
        return orderDAO.findById(id)
                .map(this::convertToDto);
    }

    private OrderDto convertToDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getOrderDate().toString(),
                order.getOrderPrice());
    }


    /**
     * return if of last order of the user
     * @return id of hte order
     */
    public int getLastOrderId(int id){
        String st = "select * from pharmacy.Orders where user_id=? order by id desc limit 1";
        List<Order> order =  AbstractDAO.<Order>findByCriteria(st, "i", adder, id)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if(order.isEmpty()){
            return 0;
        }else {
            return order.get(0).getId();
        }
    }

    /**
     * creates an order in orders history  and add it to database
     * @param user user tht makes an order
     * @param order {@link Order}
     * @param cartItems list of {@link CartItemDto} to be paid for
     * @return true if order creation was successful, false otherwise
     */
    public boolean createOrder(User user, Order order, List<CartItemDto> cartItems) {
        OrderItemService orderItemService = new OrderItemService();
        boolean isOrderCreated = orderDAO.create(order);
        if(isOrderCreated){
            int orderId = getLastOrderId(user.getId());
            orderId = orderId == 0 ? 1 : orderId;
            for(CartItemDto cartItem : cartItems) {
                orderItemService.create(new OrderItem(
                        orderId,
                        cartItem.getAmount(),
                        cartItem.getPrice(),
                        cartItem.getMedicineName(),
                        cartItem.getMedicineDose(),
                        cartItem.getMedicineConsistency(),
                        cartItem.getMedicineId()
                ));
            }
        }
        return isOrderCreated;
    }

    public List<OrderDto> findByUserId(int id){
        String st = "select * from pharmacy.Orders where user_id=?";
        return AbstractDAO.<Order>findByCriteria(st, "i", adder, id)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * calculates full price of all order items
     * @param cartItems items in the cart
     * @return full price of order
     */
    public static double calculateOrderPrice(List<CartItemDto> cartItems){
        double orderPrice = 0;
        for(CartItemDto cartItem : cartItems) {
            orderPrice += cartItem.getPrice();
        }
        return orderPrice;
    }

    /**
     * delete all orders of concrete user from database with all order items
     * @param id user id
     */
    public void deleteFullOrders(int id){
        List<OrderDto> orders = findByUserId(id);
        OrderItemService orderItemService = new OrderItemService();
        for(OrderDto order : orders) {
            List<OrderItemDto> orderItems = orderItemService.findByOrderId(order.getId());
            for(OrderItemDto orderItem : orderItems) {
                orderItemService.deleteById(orderItem.getId());
            }
            deleteById(order.getId());
        }
    }
}

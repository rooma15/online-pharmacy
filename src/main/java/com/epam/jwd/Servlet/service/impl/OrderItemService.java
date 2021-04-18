package com.epam.jwd.Servlet.service.impl;

import com.epam.jwd.Servlet.dao.impl.OrderItemDAO;
import com.epam.jwd.Servlet.model.OrderItem;
import com.epam.jwd.Servlet.model.OrderItemDto;
import com.epam.jwd.Servlet.service.CommonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderItemService implements CommonService<OrderItemDto> {

    private final OrderItemDAO orderItemDAO;

    public OrderItemService() {
        this.orderItemDAO = new OrderItemDAO();
    }

    @Override
    public List<OrderItemDto> findAll() {
        return orderItemDAO.findAll()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(int id) {
        return orderItemDAO.deleteById(id);
    }

    @Override
    public Optional<OrderItemDto> findById(int id) {
        return orderItemDAO.findById(id).map(this::convertToDto);
    }

    private OrderItemDto convertToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getId(),
                                orderItem.getOrderId(),
                                orderItem.getAmount(),
                                orderItem.getPrice(),
                                orderItem.getName(),
                                orderItem.getDose(),
                                orderItem.getConsistency(),
                                orderItem.getCategory()
                                );
    }
}

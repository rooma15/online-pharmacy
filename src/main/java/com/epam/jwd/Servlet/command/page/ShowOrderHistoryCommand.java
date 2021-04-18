package com.epam.jwd.Servlet.command.page;

import com.epam.jwd.Servlet.command.Command;
import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.command.ResponseContext;
import com.epam.jwd.Servlet.model.OrderDto;
import com.epam.jwd.Servlet.model.OrderItemDto;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.service.impl.OrderItemService;
import com.epam.jwd.Servlet.service.impl.OrderService;
import com.epam.jwd.Servlet.service.impl.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ShowOrderHistoryCommand implements Command {

    INSTANCE;

    @Override
    public boolean isUnregisteredClientForbidden() {
        return true;
    }

    private final ResponseContext CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return "history.jsp";
        }

        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public boolean isAjax() {
            return false;
        }

        @Override
        public String getErrorCode() {
            return null;
        }
    };

    @Override
    public ResponseContext execute(RequestContext req) {
        HttpSession session = req.getSession();
        UserService userService = new UserService();
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userService.findByLogin(userDto.getLogin()).get();
        OrderItemService orderItemService = new OrderItemService();
        OrderService orderService = new OrderService();
        List<OrderDto> orders = orderService.findByUserId(user.getId());
        Map<OrderDto, List<OrderItemDto>> orderMap = new HashMap<>();
        for(OrderDto order : orders) {
            List<OrderItemDto> orderItems = orderItemService.findByOrderId(order.getId());
            orderMap.put(order, orderItems);
        }
        req.setAttribute("orders", orderMap);
        return CONTEXT;
    }
}

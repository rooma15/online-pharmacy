package com.epam.jwd.Servlet.service.impl;


import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.dao.impl.CartDAO;
import com.epam.jwd.Servlet.dao.impl.MedicineDAO;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.service.CommonService;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * service for handling with order items
 */
public class CartService implements CommonService<OrderItemDto> {

    private final CartDAO cartDAO;
    private static int sessionOrderId = 1;

    private final RequestContext req;

    /**
     * initializes cart DAO {@link CartDAO} and request Context {@link RequestContext}
     * @param req
     */
    public CartService(RequestContext req) {
        cartDAO = new CartDAO();
        this.req = req;
    }


    /**
     * finds all order items in database
     * @return {@link List} of {@link OrderItemDto} order items from database
     */
    @Override
    public List<OrderItemDto> findAll() {
        return new ArrayList<>();
    }

    /**
     * add new order item to database
     * @param item order item to be added
     * @param userId user id who added item to cart
     * @return true if item added successfully, false otherwise
     */
    public boolean create(OrderItemDto item, int userId) {
        double price = item.getPrice();
        int amount = item.getAmount();
        return cartDAO.create(new OrderItem(item.getMedicineId(), userId, amount, price));
    }


    /**
     * delete item from database by id
     * @param id of item to be deleted
     * @return true if item was deleted, false otherwise
     */
    @Override
    public boolean deleteById(int id) {
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null){
            session.removeAttribute("order_" + id);
            return true;
        }else {
            return cartDAO.deleteById(id);
        }
    }


    /**
     * finds item by item id in database
     * @param id of item
     * @return {@link Optional} of {@link OrderItemDto} founded in database, epmty {@link Optional} otherwise
     */
    @Override
    public Optional<OrderItemDto> findById(int id) {
        return cartDAO.findById(id).map(this::convertToDto);
    }


    /**
     * coverts {@link OrderItem} to {@link OrderItemDto}
     * @param orderItem to be converted
     * @return converted {@link OrderItemDto}
     */
    private OrderItemDto convertToDto(OrderItem orderItem) {
        String medicineName;
        int medicineDose;
        String medicineConsistency;
        MedicineDAO medicineDAO = new MedicineDAO();
        Optional<Medicine> medicine = medicineDAO.findById(orderItem.getMedicineId());
        if(medicine.isPresent()) {
            medicineName = medicine.get().getName();
            medicineDose = medicine.get().getDose();
            medicineConsistency = medicine.get().getConsistency();
        } else {
            throw new IllegalStateException();
        }
        return new OrderItemDto(orderItem.getId(),
                orderItem.getMedicineId(),
                medicineName,
                medicineDose,
                medicineConsistency,
                orderItem.getPrice(),
                orderItem.getAmount()
        );
    }


    /**
     * finds all items of concrete user
     * @param userId if of user
     * @return {@link List} list of {@link OrderItemDto} items
     */
    public List<OrderItemDto> findByUserId(int userId) {
        String st = "select * from pharmacy.Cart where user_id=?";
        return cartDAO.findByCriteria(st, "i", userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * finds item on concrete medicine of concrete user
     * @param medicineId id of medicine
     * @param userId id of user
     * @return @return {@link Optional} of {@link OrderItemDto} founded in database, epmty {@link Optional} otherwise
     */
    public Optional<OrderItemDto> findByMedicineIdUserId(int medicineId, int userId) {
        String st = "select * from pharmacy.Cart where medicine_id=? and user_id=?";
        List<OrderItem> items = cartDAO.findByCriteria(st, "ii", medicineId, userId);
        if(!items.isEmpty()){
            return Optional.of(convertToDto(items.get(0)));
        }else {
            return Optional.empty();
        }
    }


    /**
     * increases amount of concrete order item in database or session
     * @param orderItemDto {@link OrderItemDto} order item with all needed info
     * @param amount amount growth of medicines in order
     * @return true if increasing was successful, false otherwise
     */
    private boolean increaseAmountOfOrder(OrderItemDto orderItemDto, int amount) {
        HttpSession session = req.getSession();
        int orderId = orderItemDto.getId();
        int newAmount = orderItemDto.getAmount() + amount;
        double newPrice = orderItemDto.getPrice() / orderItemDto.getAmount() * newAmount;
        if(session.getAttribute("user") == null) {
            session.removeAttribute("order_" + orderId);
            OrderItemDto newOrderItem = new OrderItemDto(orderItemDto.getId(),
                    orderItemDto.getMedicineId(),
                    orderItemDto.getMedicineName(),
                    orderItemDto.getMedicineDose(),
                    orderItemDto.getMedicineConsistency(),
                    newPrice,
                    newAmount);
            session.setAttribute("order_" + orderId, newOrderItem);
            return true;
        }

        return changeDbAmount(orderItemDto, newAmount);
    }

    /**
     * changes amount of medicine in order in database
     * @param orderItemDto {@link OrderItemDto} order item with all needed info
     * @param newAmount new amount of medicines in order
     * @return true if changing was successful, false otherwise
     */
    private boolean changeDbAmount(OrderItemDto orderItemDto, int newAmount){
        double newPrice = orderItemDto.getPrice() / orderItemDto.getAmount() * newAmount;
        String st = "update pharmacy.Cart set amount=?, price=? where medicine_id=?";
        return AbstractDAO.updateByCriteria(st, "idi", newAmount, newPrice, orderItemDto.getMedicineId());
    }


    /**
     * add order medicine to cart, if user is logged in, than order goes to data base, else it stores in session
     * @param item medicine to be added
     * @param amount amount of that medicine in order item
     * @return true if order was successfully added, false otherwise
     */
    public boolean addToCart(MedicineDto item, int amount) {
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null) {
            List<OrderItemDto> orderItems = getSessionCartItems();
            for(OrderItemDto orderItem : orderItems) {
                if(orderItem.getMedicineId() == item.getId()) {
                    return increaseAmountOfOrder(orderItem, amount);
                }
            }
        } else {
            UserService userService = new UserService();
            UserDto user = (UserDto)session.getAttribute("user");
            int userId = userService.findByLogin(user.getLogin()).get().getId();
            Optional<OrderItemDto> elem = findByMedicineIdUserId(item.getId(), userId);
            if(elem.isPresent()) {
                return increaseAmountOfOrder(elem.get(), amount);
            }
        }
        if(session.getAttribute("user") == null) {
            OrderItemDto orderItem = new OrderItemDto(
                    sessionOrderId,
                    item.getId(),
                    item.getName(),
                    item.getDose(),
                    item.getConsistency(),
                    amount * item.getPrice(),
                    amount
            );
            session.setAttribute("order_" + sessionOrderId, orderItem);
            sessionOrderId++;
            return true;
        } else {
            UserService userService = new UserService();
            UserDto user = (UserDto)session.getAttribute("user");
            int userId = userService.findByLogin(user.getLogin()).get().getId();
            OrderItemDto orderItemDto = new OrderItemDto(
                    0,
                    item.getId(),
                    item.getName(),
                    item.getDose(),
                    item.getConsistency(),
                    amount * item.getPrice(),
                    amount
            );
            return create(orderItemDto, userId);
        }
    }


    /**
     * get all order items, that are stored in session
     * @return {@link List} of {@link OrderItemDto} of order items in session
     */
    public List<OrderItemDto> getSessionCartItems() {
        List<OrderItemDto> orderItems = new ArrayList<>();
        HttpSession session = req.getSession();
        Enumeration<String> params = session.getAttributeNames();
        while(params.hasMoreElements()) {
            String param = params.nextElement();
            OrderItemDto item;
            try{
                item = (OrderItemDto) session.getAttribute(param);
            }catch (ClassCastException e){
                continue;
            }
            orderItems.add(item);
        }
        return orderItems;
    }

    /**
     * add all session order items of user in database, when user get logged in
     */
    public void synchronizeCart(){
        HttpSession session = req.getSession();
        UserService userService = new UserService();
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userService.findByLogin(userDto.getLogin()).get();
        List<OrderItemDto> sessionOrders = getSessionCartItems();
        List<OrderItemDto> dbOrders = findByUserId(user.getId());
        for(OrderItemDto sessionOrder : sessionOrders) {
            boolean isMerged = false;
            for(OrderItemDto dbOrder : dbOrders) {
                if(sessionOrder.getMedicineId() == dbOrder.getMedicineId()){
                    isMerged = true;
                    changeDbAmount(dbOrder, sessionOrder.getAmount() + dbOrder.getAmount());
                    session.removeAttribute("order_" + sessionOrder.getId());
                }
            }
            if(!isMerged){
                create(sessionOrder, user.getId());
                session.removeAttribute("order_" + sessionOrder.getId());
            }
        }
    }

}

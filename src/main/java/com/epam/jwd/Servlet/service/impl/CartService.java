package com.epam.jwd.Servlet.service.impl;


import com.epam.jwd.Servlet.command.RequestContext;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.dao.impl.CartDAO;
import com.epam.jwd.Servlet.dao.impl.MedicineDAO;
import com.epam.jwd.Servlet.model.*;
import com.epam.jwd.Servlet.service.CommonService;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * service for handling with order items
 */
public class CartService implements CommonService<CartItemDto> {

    private final CartDAO cartDAO;
    private static int sessionOrderId = 1;

    private final RequestContext req;

    private final Function<ResultSet, Optional<CartItem>> adder = resultSet -> {
        try {
            return Optional.of(new CartItem(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };

    /**
     * initializes cart DAO {@link CartDAO} and request Context {@link RequestContext}
     * @param req {@link RequestContext}
     */
    public CartService(RequestContext req) {
        cartDAO = new CartDAO();
        this.req = req;
    }


    /**
     * finds all order items in database
     * @return {@link List} of {@link CartItemDto} order items from database
     */
    @Override
    public List<CartItemDto> findAll() {
        return new ArrayList<>();
    }

    /**
     * add new order item to database
     * @param item order item to be added
     * @param userId user id who added item to cart
     * @return true if item added successfully, false otherwise
     */
    public boolean  create(CartItemDto item, int userId) {
        double price = item.getPrice();
        int amount = item.getAmount();
        return cartDAO.create(new CartItem(item.getMedicineId(), userId, amount, price));
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
     * @return {@link Optional} of {@link CartItemDto} founded in database, epmty {@link Optional} otherwise
     */
    @Override
    public Optional<CartItemDto> findById(int id) {
        return cartDAO.findById(id).map(this::convertToDto);
    }


    /**
     * coverts {@link CartItem} to {@link CartItemDto}
     * @param cartItem to be converted
     * @return converted {@link CartItemDto}
     */
    private CartItemDto convertToDto(CartItem cartItem) {
        String medicineName;
        int medicineDose;
        String medicineConsistency;
        MedicineDAO medicineDAO = new MedicineDAO();
        Optional<Medicine> medicine = medicineDAO.findById(cartItem.getMedicineId());
        if(medicine.isPresent()) {
            medicineName = medicine.get().getName();
            medicineDose = medicine.get().getDose();
            medicineConsistency = medicine.get().getConsistency();
        } else {
            throw new IllegalStateException();
        }
        return new CartItemDto(cartItem.getId(),
                cartItem.getMedicineId(),
                medicineName,
                medicineDose,
                medicineConsistency,
                cartItem.getPrice(),
                cartItem.getAmount()
        );
    }


    /**
     * finds all items of concrete user
     * @param userId if of user
     * @return {@link List} list of {@link CartItemDto} items
     */
    public List<CartItemDto> findByUserId(int userId) {
        String st = "select * from pharmacy.Cart where user_id=?";
        List<CartItem> cartItems = AbstractDAO.findByCriteria(st, "i", adder, userId)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        MedicineDAO medicineDAO = new MedicineDAO();
        return cartItems
                .stream()
                .filter(cartItem -> medicineDAO.findById(cartItem.getMedicineId()).isPresent())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * finds all items of concrete medicine id
     * @param medicine_id medicine id of orders
     * @return {@link List} list of {@link CartItem} items
     */
    public List<CartItem> findByMedicineId(int medicine_id) {
        String st = "select * from pharmacy.Cart where medicine_id=?";
        return AbstractDAO.<CartItem>findByCriteria(st, "i", adder, medicine_id)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * finds item on concrete medicine of concrete user
     * @param medicineId id of medicine
     * @param userId id of user
     * @return @return {@link Optional} of {@link CartItemDto} founded in database, epmty {@link Optional} otherwise
     */
    public Optional<CartItemDto> findByMedicineIdUserId(int medicineId, int userId) {
        String st = "select * from pharmacy.Cart where medicine_id=? and user_id=?";
        List<CartItem> items = AbstractDAO.findByCriteria(st, "ii", adder, medicineId, userId)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if(!items.isEmpty()){
            return Optional.of(convertToDto(items.get(0)));
        }else {
            return Optional.empty();
        }
    }


    /**
     * increases amount of concrete order item in database or session
     * @param cartItemDto {@link CartItemDto} order item with all needed info
     * @param amount amount growth of medicines in order
     * @return true if increasing was successful, false otherwise
     */
    private boolean increaseAmountOfOrder(CartItemDto cartItemDto, int amount) {
        HttpSession session = req.getSession();
        int orderId = cartItemDto.getId();
        int newAmount = cartItemDto.getAmount() + amount;
        double newPrice = cartItemDto.getPrice() / cartItemDto.getAmount() * newAmount;
        if(session.getAttribute("user") == null) {
            session.removeAttribute("order_" + orderId);
            CartItemDto newOrderItem = new CartItemDto(cartItemDto.getId(),
                    cartItemDto.getMedicineId(),
                    cartItemDto.getMedicineName(),
                    cartItemDto.getMedicineDose(),
                    cartItemDto.getMedicineConsistency(),
                    newPrice,
                    newAmount);
            session.setAttribute("order_" + orderId, newOrderItem);
            return true;
        }

        return changeDbAmount(cartItemDto, newAmount);
    }

    /**
     * changes amount of medicine in order in database
     * @param cartItemDto {@link CartItemDto} order item with all needed info
     * @param newAmount new amount of medicines in order
     * @return true if changing was successful, false otherwise
     */
    private boolean changeDbAmount(CartItemDto cartItemDto, int newAmount){
        double newPrice = cartItemDto.getPrice() / cartItemDto.getAmount() * newAmount;
        String st = "update pharmacy.Cart set amount=?, price=? where medicine_id=?";
        return AbstractDAO.updateByCriteria(st, "idi", newAmount, newPrice, cartItemDto.getMedicineId());
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
            List<CartItemDto> orderItems = getSessionCartItems();
            for(CartItemDto orderItem : orderItems) {
                if(orderItem.getMedicineId() == item.getId()) {
                    return increaseAmountOfOrder(orderItem, amount);
                }
            }
        } else {
            UserService userService = new UserService();
            UserDto user = (UserDto)session.getAttribute("user");
            int userId = userService.findByLogin(user.getLogin()).get().getId();
            Optional<CartItemDto> elem = findByMedicineIdUserId(item.getId(), userId);
            if(elem.isPresent()) {
                return increaseAmountOfOrder(elem.get(), amount);
            }
        }
        if(session.getAttribute("user") == null) {
            CartItemDto orderItem = new CartItemDto(
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
            CartItemDto cartItemDto = new CartItemDto(
                    0,
                    item.getId(),
                    item.getName(),
                    item.getDose(),
                    item.getConsistency(),
                    amount * item.getPrice(),
                    amount
            );
            return create(cartItemDto, userId);
        }
    }


    /**
     * get all order items, that are stored in session
     * @return {@link List} of {@link CartItemDto} of order items in session
     */
    public List<CartItemDto> getSessionCartItems() {
        List<CartItemDto> orderItems = new ArrayList<>();
        HttpSession session = req.getSession();
        Enumeration<String> params = session.getAttributeNames();
        while(params.hasMoreElements()) {
            String param = params.nextElement();
            CartItemDto item;
            try{
                item = (CartItemDto) session.getAttribute(param);
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
        List<CartItemDto> sessionOrders = getSessionCartItems();
        List<CartItemDto> dbOrders = findByUserId(user.getId());
        for(CartItemDto sessionOrder : sessionOrders) {
            boolean isMerged = false;
            for(CartItemDto dbOrder : dbOrders) {
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


    /**
     * clean user cart
     * @return true if everything was successful, false otherwise
     */
    public boolean clearCart(){
        HttpSession session = req.getSession();
        String st = "delete from pharmacy.Cart where user_id=? and id>0";
        UserService userService = new UserService();
        UserDto userDto = (UserDto)session.getAttribute("user");
        User user = userService.findByLogin(userDto.getLogin()).get();
        return AbstractDAO.updateByCriteria(st, "i", user.getId());

    }
    public boolean clearCart(int id){
        String st = "delete from pharmacy.Cart where user_id=? and id>0";
        return AbstractDAO.updateByCriteria(st, "i", id);
    }

}

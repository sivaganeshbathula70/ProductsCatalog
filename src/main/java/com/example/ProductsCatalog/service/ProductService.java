package com.example.ProductsCatalog.service;
import com.example.ProductsCatalog.exception.ProductException;
import com.example.ProductsCatalog.model.*;
import com.example.ProductsCatalog.repository.BookingRepository;
import com.example.ProductsCatalog.repository.OrderRepository;
import com.example.ProductsCatalog.repository.ProductRepository;
import com.example.ProductsCatalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    public UserProduct findByEmail(String Email) throws ProductException

    {
        try{
            return userRepository.findByemail(Email);
        } catch(ProductException exception){
            throw new ProductException("couldn't save prodsuct....");
        }

    }
    public boolean findByEmail(String Email,String password) throws ProductException{
        try {
            UserProduct userProduct = userRepository.findByemail(Email);
            return userProduct != null && userProduct.getPassword().equals(password);
        }catch(ProductException exception){
            throw new ProductException(" Invalid bRo");
        }
    }
    public UserProduct addUser(UserProduct userProduct) throws ProductException {
        try {
            return userRepository.save(userProduct);
        } catch (DataIntegrityViolationException exception) {
            throw new ProductException("Can't insert duplicate values");
        }
    }
   /* public Order createOrder(Order order)throws ProductException{
        try {
            return orderRepository.save(order);
        }catch(DataIntegrityViolationException exception){
            throw new ProductException("cannot insert such values");
        }
    }

    */


    public List<Products> products()throws ProductException {
        try {
            return productRepository.findAll();
        }catch(ProductException exception){
            throw new ProductException("no product found");
        }
    }

    public Products createProduct(Products product) throws ProductException{

        try {

            Products products = new Products();
            products.setProductName(product.getProductName());
            products.setProductDescription(product.getProductDescription());
            products.setPrice(product.getPrice());
            products.setRating(product.getRating());
            products.setCategory(product.getCategory());
            products.setQuantity(product.getQuantity());
        //   products.setBookingId(product.getBookingId());
          //  products.setStatus(product.getStatus());


            products.setDiscount(product.getDiscount());
            products.setContactNumber(product.getContactNumber());
            products.setSku(product.getSku());
            products.setLocalDate(product.getLocalDate());
            products.setOrder(product.getOrder());
           // products.setOrderStatus(product.getOrderStatus());


            return productRepository.save(products);
        }catch(ProductException productException)
        {
            throw new ProductException("cannot create with null values");
        }
    }

    public List<Products> createProducts(List<Products> products) throws ProductException{
        try {
            return productRepository.saveAll(products);
        }catch(ProductException productException){
            throw new ProductException("cannot insert");
        }
    }

    public Optional<Products> findProductById(int productId) throws ProductException {
       try {
           return productRepository.findById(productId);
       }catch(ProductException exception)
       {
           throw new ProductException("product with Id not found");
       }
    }

    public List<Products> currentDateProducts()throws ProductException {
        try {
            LocalDate localDate = LocalDate.now();
            List<Products> currentDate = productRepository.findAll();
            List<Products> currentProducts = currentDate.stream()
                    .filter(u -> u.getLocalDate().isEqual(localDate))
                    .toList();
            return currentProducts;
        }catch(ProductException exception){
            throw new ProductException("no products avilable");
        }

    }

    public List<Products> pastDateProducts() throws ProductException{
        try {
            LocalDate localDate = LocalDate.now();
            List<Products> pastDateProducts = productRepository.findAll();
            List<Products> pastProducts = pastDateProducts.stream()
                    .filter(u -> u.getLocalDate().isBefore(localDate))
                    .toList();
            return pastProducts;
        }catch(ProductException exception){
            throw new ProductException("no products avilable");
        }
    }

    public List<Products> productsGreaterThanToday() {
        try {
            LocalDate localDate = LocalDate.now();
            List<Products> GraeterThanToday = productRepository.findAll();
            List<Products> GreaterProducts = GraeterThanToday
                    .stream()
                    .filter(u -> u.getLocalDate().isAfter(localDate))
                    .toList();
            return GreaterProducts;
        }catch(ProductException exception){
            throw new ProductException("no products available");
        }

    }

    //sort
    public List<Products> sortProducts(String field) {


        return productRepository.findAll(Sort.by(field));
    }

    //paging
    public Page<Products> paginationProducts(int pageSize, int offSet) {
        Page<Products> paging = productRepository.findAll(PageRequest.of(pageSize, offSet));
        return paging;
    }

    public Products updateProducts(Products products, int productId)  throws ProductException {
        try {
            Products product = productRepository.findById(productId).orElseThrow(() -> new ProductException("Couldn't update "));
            product.setProductName(products.getProductName());
            product.setProductDescription(products.getProductDescription());
            product.setPrice(products.getPrice());
            product.setRating(products.getRating());
            product.setCategory(products.getCategory());
            product.setOrder(products.getOrder());


            return productRepository.save(products);
        } catch(ProductException productException){
            throw new ProductException(" couldn't update it.....");
        }
    }

    public String deletesProduct(int productId) {
        productRepository.deleteById(productId);
        return "product Deleted Successfully...........";
    }

    /* public Products> displayAll(){
         Products products = productRepository.findAll();
         products.g
     }

     */
    public Order updateOrderStatus(int orderId, Order order, OrderStatus orderStatus) throws ProductException{
        try {
            Order orders = orderRepository.findById(orderId).orElseThrow(()->new ProductException("Cannot update the status..."));
          //  products.setOrderStatus(producs.getOrderStatus());
            orders.setOrderStatus(order.getOrderStatus());
          //  return productRepository.save(products);
            return orderRepository.save(orders);
        }catch(ProductException productException)
        {
            throw new ProductException("no data found cannot update such data because it is not present in our DB");
        }
    }

    public Products findByproductName(String field) throws ProductException{
        try {
            Products products = productRepository.findByproductName(field);
            return products;
        }catch(ProductException productException){
            throw new ProductException("no product found");
        }
    }

    public Booking createBooking(Booking booking)throws ProductException {
        try {
            return bookingRepository.save(booking);
        }catch(ProductException exception){
            throw new ProductException("cannot done with null values");
        }
    }

    public List<Booking> calculateProduct()throws ProductException {
        try {
            List<Booking> book = bookingRepository.findAll();
            book.stream().map(b -> b.getTotalCost() > 150).collect(Collectors.toList());
            return book;
        }catch(ProductException exception){
            throw new ProductException("no product found to calculate.");
        }


    }

    public Booking calculateBooking(Products products,Booking bookingOrder, int quantity) {
        Optional<Products> booking = productRepository.findById(products.getProductId());

        if (booking.isPresent()) {
            Products product = booking.get();
            Booking book = performBooking(product, bookingOrder,quantity);
            return book;

        } else {
            return null;
        }


    }

    public Booking performBooking(Products products,Booking bookingOrder, int quantity) {
        Booking booking = new Booking();
        booking.setBookingId(bookingOrder.getBookingId());
        booking.setBookingdate(LocalDate.now());
        booking.setProductName(products.getProductName());
    //   booking.setBookingId(products.getBookingId());
        booking.setContactNumber(products.getContactNumber());
        booking.setOrderId(products.getOrder().getOrderId());
        booking.setOrderDescription(products.getOrder().getOrderDescription());
        booking.setBookingStatus(products.getProductDescription());
        booking.setBookingdate(LocalDate.now());
        booking.setProducts(products);
       // Random random = new Random();

       // booking.setBookingId(products.getOrder().);
       // booking.setProducts
        double totalCost = calculteTotal(products.getPrice() ,quantity);
        booking.setTotalCost(totalCost);
        return bookingRepository.save(booking);


    }
    public double calculteTotal(int price,int quantity){
        return price*quantity;
    }


    public String  cancelBooking(int bookingId) throws ProductException
    {
        try{
            bookingRepository.deleteById(bookingId);
            return "booking cancelled successfully....";

        }catch(ProductException productException){
            throw new ProductException("no Booking Info found to cancel...");
        }
    }







}

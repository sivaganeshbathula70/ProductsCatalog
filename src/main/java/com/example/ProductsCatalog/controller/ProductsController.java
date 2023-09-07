package com.example.ProductsCatalog.controller;
import com.example.ProductsCatalog.exception.ProductException;
import com.example.ProductsCatalog.model.*;
import com.example.ProductsCatalog.service.Jwtservice;
import com.example.ProductsCatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductService productService;

    @Autowired
    Jwtservice jwtservice;
    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/display")
    public List<Products> display()throws ProductException

    {
        List<Products>products = productService.products();
        if(products.isEmpty())

        {
            throw new ProductException("no products found...");
        }

        return products;
    }
    @PostMapping("/singleproduct")
    public Products create(@RequestBody Products product)throws ProductException{
        Products products = productService.createProduct(product);
        if(products==null)
        {
            throw new ProductException("cannot insert values");
        }
        return products;
    }
    @PostMapping("/multipleproducts")
    public List<Products> craeteMultiple(@RequestBody List<Products> product)throws ProductException
    {
        List<Products>products = productService.createProducts(product);
        if(products.isEmpty())
        {
            throw new ProductException("cannot insert...");
        }
        return products;
    }

    @GetMapping("/findBy/{productId}")
    public Optional<Products> findByIdProduct(@PathVariable int productId) throws ProductException{
        Optional<Products>products = productService.findProductById(productId);
        if(!products.isPresent())
        {
            throw new ProductException("product not found");
        }
        return products;
    }
    @GetMapping("/currentdate")
    public List<Products> currentDayProducts() throws ProductException
    {
        List<Products>products = productService.currentDateProducts();
        if(products.isEmpty())
        {
            throw new ProductException("no products found");
        }


       return  products;
    }
    @GetMapping("/pastdate")
    public List<Products> pastDateProducts(){

        List<Products>products = productService.pastDateProducts();
        if(products.isEmpty())
        {
            throw new ProductException("no products found");
        }
       return products;
    }
    @GetMapping("/futureproducts")
    public List<Products> futureDateProducts(){
        List<Products>products = productService.productsGreaterThanToday();
        if(products.isEmpty()){
            throw new ProductException("no products found");
        }

        return products;
    }
    @GetMapping("/{field}")
    @Cacheable(key = "#field",value ="Products" )
    public List<Products> sortProducts(@PathVariable String field)
    {
        return productService.sortProducts(field);
    }
    @GetMapping("/{pageSize}/{offSet}")
    public Page<Products> pagingProducts(@PathVariable int pageSize, @PathVariable int offSet) throws ProductException
    {
        return productService.paginationProducts(pageSize,offSet);
    }
    @PutMapping("/update/{productId}")
    public Products updateProduct(@PathVariable int productId,@RequestBody Products products)throws ProductException{
        try {
            return productService.updateProducts(products, productId);
        }catch(ProductException exception){
            throw new ProductException("cannot update");
        }
    }
    @DeleteMapping("/delete/{productId}")
    public String deletepR(@PathVariable int productId){
        productService.deletesProduct(productId);
        return "product deleted successfully...........";
    }

    @PutMapping("/statusupdate/{orderId}")
    public Order updateorderStatus(@PathVariable int orderId, @RequestBody Order orders, OrderStatus orderStatus)throws ProductException{
       // Products product = productService.updateOrderStatus(productId,products, orderStatus);
        Order order =  productService.updateOrderStatus(orderId,orders,orderStatus);
        if(order.getOrderStatus()==null){
            throw new ProductException("cannot update it is not present in Database");
        }
        return order;
    }
    @GetMapping("/search/{field}")
    public Products findByproductName(@PathVariable String field) throws ProductException{
        Products products = productService.findByproductName(field);
        if(products==null){
            throw new ProductException("no products found Entity cannot be left Empty");
        }
        return products;
    }

    @PostMapping("/booking")
    public Booking createBooking(@RequestBody Booking booking)throws ProductException{
        Booking book = productService.createBooking(booking);
        if(book==null){
            throw new ProductException("cannot be completed booking with null");
        }
        return book;
       // return productService.createBooking(booking);
    }
    @GetMapping("/filterproducts")
    public List<Booking> filterBooking() throws ProductException{
        List<Booking>book = productService.calculateProduct();

                if(book.isEmpty())
                {
                    throw new ProductException("cannot calculate there was no product.");
                }
                return book;
    }

    @PostMapping("/calculatebooking/{productId}/{quantity}")
    public Booking calculateBooking(
            @PathVariable int productId,
            @PathVariable int quantity,@RequestBody Booking bookingOrder) {
        Products products = productService.findProductById(productId).orElseThrow(()-> new ProductException("no Booking info found"));

        if (products != null) {
            Booking bookingResult = productService.calculateBooking(products,bookingOrder, quantity);
            double totalCost = productService.calculteTotal(products.getPrice(), quantity);
            bookingResult.setTotalCost(totalCost);
            return bookingResult;
        } else {
            return  null;
        }
    }



    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserProduct userProduct) {
        if (productService.findByEmail(userProduct.getEmail(), userProduct.getPassword())) {
            return ResponseEntity.ok("{\"message\": \"Logged in successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials\"}");
        }
    }

    @PostMapping("/save")
    public UserProduct saveUser(@RequestBody UserProduct userProduct)throws ProductException{

        UserProduct user = productService.addUser(userProduct);
        if(user==null)

        {
            throw new ProductException("cannot save user");
        }
        return user;


    }

  /*  @PostMapping("/orders")
    public Order saveOrder(@RequestBody Order order)throws ProductException{
        Order orders = productService.createOrder(order);
        if(orders==null){
            throw new ProductException("cannot add null values");
        }
        return orders;
    }

   */
    @PostMapping("/addbooking")
    public Booking addBooking(@RequestBody Booking booking){
        Booking book = productService.createBooking(booking);
        //if(book==null){
        //    throw new ProductException("Null values cannot be inserted...");
        //}
        return book;
    }


    @DeleteMapping("/cancelbooking/{bookingId}")
    public String cancelBooking(@PathVariable int bookingId){
        productService.cancelBooking(bookingId);
        return "booking cancelled successfully.....";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserProduct userProduct){
        boolean isAuthenticated = productService.findByEmail(userProduct.getEmail(),userProduct.getPassword());
        if (isAuthenticated){
            String  token = jwtservice.generateToken(userProduct.getEmail());
            return ResponseEntity.ok(token);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid details not exists");
        }
    }





}



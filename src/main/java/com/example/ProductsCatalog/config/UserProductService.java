package com.example.ProductsCatalog.config;
import com.example.ProductsCatalog.model.UserProduct;
import com.example.ProductsCatalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserProductService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProduct userProduct = userRepository.findByemail(email);
        if (userProduct == null) {
            throw new UsernameNotFoundException("no users found with this mail id...");
        } else {
            return new UserProductDetails(userProduct.getEmail(), userProduct.getPassword());


        }
    }
}

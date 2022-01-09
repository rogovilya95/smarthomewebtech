package htw.webtech.smarthome.controllers;

import htw.webtech.smarthome.common.APIResponse;
import htw.webtech.smarthome.domain.User;
import htw.webtech.smarthome.dto.cart.AddToCartDto;
import htw.webtech.smarthome.dto.cart.CartDto;
import htw.webtech.smarthome.service.AuthenticationService;
import htw.webtech.smarthome.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    public ResponseEntity<APIResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){

        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);
        return new ResponseEntity<>(new APIResponse(true, "Item added to the cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<APIResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new APIResponse(true, "Item has been successfuly removed"), HttpStatus.OK);
    }


}

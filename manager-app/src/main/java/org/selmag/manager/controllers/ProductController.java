package org.selmag.manager.controllers;

import org.selmag.manager.controllers.payload.UpdateProductPayload;
import org.selmag.manager.entity.Product;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.selmag.manager.services.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/catalogue/products/{productId:\\d+}")
public class ProductController {

    private final ProductService productService;

//  необходим для интернационализации сообщений
    private final MessageSource messageSource;

//    позволяет передавать в модель атрибуд product и теперь в остальных методах не надо использовать @PathVariable и Model
    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") Integer id) {
        return productService.findById(id).orElseThrow(() -> new NoSuchElementException("catalogue.errors.productNotFound"));
    }

    @GetMapping()
    public String getProductPage() {
        return "catalogue/products/product";
    }

    @GetMapping("/edit")
    public String getProductEditPage() {
        return "catalogue/products/edit_product";
    }

    @PatchMapping("/edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                @Valid UpdateProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors",
                    bindingResult
                            .getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList());
            return "catalogue/products/edit_product";

        } else {
            this.productService.update(product.getId(), payload.title(), payload.description());
            List<Product> allProducts = this.productService.findAllProducts();
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }

    @GetMapping("/delete")
    public String getProductDeletePage() {
        return "catalogue/products/delete_product";
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.delete(product.getId());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(NoSuchElementException ex, Model model,
                                          HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(ex.getMessage(), new Object[]{}, ex.getMessage(), locale));
        return "errors/404";
    }
}

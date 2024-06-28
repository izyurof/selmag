package org.selmag.manager.controllers;

import org.selmag.manager.controllers.payload.NewProductPayload;
import org.selmag.manager.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.selmag.manager.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//указывает, что аннотированный класс является контроллером (нельзя помечать @Component, как сервисы и репозитории,
// т.к. сервлеты требуют именно эту аннотацию)
@Controller
//создает конструктор для final полей и полей помеченных @NotNull
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("/create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("/create")
    public String createNewProduct(@Valid NewProductPayload productPayload,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", productPayload);
            model.addAttribute("errors",
                    bindingResult
                            .getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .toList());
            return "catalogue/products/new_product";
        } else {
            Product product = this.productService.createProduct(productPayload.title(), productPayload.description());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }

    }



}

package org.selmag.manager.controllers.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @NotNull(message = "{catalogue.products.edit.errors.title_is_null}")
//        для того, чтобы данное сообщение автоматически перевелось, без ручного использования метода у MessageSource,
//        данное выражение необходимо заключить в фигурные скобки, тогда будет подставлено локализованное сообщение
        @Size(min = 3, max = 50, message = "{catalogue.products.edit.errors.title_size_is_invalid}")
        String title,
        @Size(max = 1000, message = "{catalogue.products.edit.errors.description_size_is_invalid}")
        String description) {
}

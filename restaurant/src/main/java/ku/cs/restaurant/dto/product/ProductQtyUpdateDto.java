package ku.cs.restaurant.dto.product;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductQtyUpdateDto {
    private UUID id;
    private int qty;
}
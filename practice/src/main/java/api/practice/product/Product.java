package api.practice.product;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="products")
@NoArgsConstructor
public class Product {

//    @Id
//    @GeneratedValue(generator="uuid2")
//    @GenericGenerator(name="uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "BINARY(16)")

    private UUID uuid = UUID.randomUUID();

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="product_id")
    private String id = uuid.toString();

    @Column(name="product_name")
    private String name;

    @Column(name="product_price")
    private int price;

    @Column(name="product_stock")
    private int stock;

    public int remove(int count) {
        return stock -= count;
    }
    public String toString() {
        return id.toString();
    }

}

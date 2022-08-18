package api.practice.order;

import api.practice.product.Product;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="order_member")
    private String member;

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> lists = new ArrayList<>();

    //cascade = CascadeType.ALL

    public void addProductOrder(ProductOrder productOrder) {
        lists.add(productOrder);
        productOrder.setOrder(this);
    }

    public static Order create(String member, ProductOrder... lists) {
        Order order = new Order();
        order.setMember(member);
        for(ProductOrder productOrder : lists) {
            order.addProductOrder(productOrder);
        }
        return order;
    }

    public int total() {
        int total = 0;
        for(ProductOrder productOrder : lists) {
            total += productOrder.total();
        }
        return total;
    }

    public String toString(UUID id) {
        return id.toString();
    }
}

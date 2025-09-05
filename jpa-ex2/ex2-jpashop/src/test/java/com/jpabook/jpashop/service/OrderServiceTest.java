package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 주문
     * @throws Exception
     */
    @Test
    public void order() throws Exception {
        // givne
        Member member = createMemer();
        Book book = createBook("book1", 10000, 100);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문 시 상태는 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        Assertions.assertEquals((10000 * orderCount), getOrder.getTotalPrice(), "총 주문 가격은 가격 * 수량이다.");
        Assertions.assertEquals((100 - orderCount), book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    /**
     * 재고 수량 초과 주문
     * @throws Exception
     */
    @Test
    public void overStock()  throws Exception {
        // given
        Member member = createMemer();
        Item item = createBook("book1", 10000, 10);

        int orderCount = 12;

        // when
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        // then
    }

    /**
     * 주문 취소
     * @throws Exception
     */
    @Test
    public void cancel() throws Exception {
        // given
        Member member = createMemer();
        Book book = createBook("book1", 10000, 100);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancel(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 100, book.getStockQuantity());
    }

    // 회원 생성
    private Member createMemer() {
        Member member = new Member();
        member.setName("user1");
        member.setAddress(new Address("서울", "올림픽대로", "12345"));

        em.persist(member);

        return member;
    }

    // 상품 생성
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);

        em.persist(book);

        return book;
    }
}
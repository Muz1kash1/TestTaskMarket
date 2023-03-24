package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Notion;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.OrganisationProduct;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductDiscount;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.UserNotions;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IStoreRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.NotionDto;
import com.muz1kash1.webmarkettesttask.model.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса репозитория под постгрес.
 */
@Data
@Repository
@Slf4j
@AllArgsConstructor
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres")
public class PostgresRepository implements IStoreRepo {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrganisationRepository organisationRepository;
  private final DiscountRepository discountRepository;
  private final NotionsRepository notionsRepository;
  private final UserNotionsRepository userNotionsRepository;
  private final OrganisationProductRepository organisationProductRepository;
  private final ProductDiscountRepository productDiscountRepository;
  private final ProductReviewsRepository productReviewsRepository;
  private final PurchaseRepository purchaseRepository;

  public User getUserById(long id) {

    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserById(id)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );

  }

  public User addUser(SignUpDto signUpDto) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = new
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User(
      signUpDto.getUsername(),
      signUpDto.getMail(),
      signUpDto.getPassword(),
      BigDecimal.ZERO,
      true
    );
    userRepository.save(user);
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User userToReturn = userRepository
      .findUserByUsername(user.getUsername())
      .get();
    return new User(
      userToReturn.getId(),
      userToReturn.getUsername(),
      userToReturn.getMail(),
      userToReturn.getPassword(),
      userToReturn.getBalance(),
      userToReturn.isEnabled()
    );
  }

  public User getUserByUsername(String username) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserByUsername(username)
      .get();
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );
  }

  @Override public List<com.muz1kash1.webmarkettesttask.model.domain.Notion> getNotionsOfUser(final long userid) {
    List<Long> notionIds = userNotionsRepository.getIdOfNotionsOfUser(userid);

    List<Notion> notions = new ArrayList<>();
    for (
      Long notionId : notionIds
    ) {
      notions.add(notionsRepository.getReferenceById(notionId));
    }

    List<com.muz1kash1.webmarkettesttask.model.domain.Notion> notionList = new ArrayList<>();
    for (
      Notion notion : notions
    ) {
      notionList.add(new com.muz1kash1.webmarkettesttask.model.domain.Notion(
        notion.getId(),
        notion.getHeader(),
        notion.getNotionDate(),
        notion.getNotionText()
      ));
    }
    return notionList;
  }

  @Override public List<Product> getAllProducts() {
    List<com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product> products
      = productRepository.findAll();
    List<Product> productList = new ArrayList<>();
    for (
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product product : products
    ) {
      productList.add(new Product(
        product.getId(),
        product.getProductName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars()
      ));
    }
    return productList;
  }

  @Override public Product updateProductById(final long id, final Product product) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product productToSave =
      new
        com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product(
        product.getName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars()
      );
    productToSave.setId(id);
    productRepository.save(productToSave);
    product.setId(id);
    return product;
  }

  @Transactional
  @Override public void deleteProductById(final long id) {
    organisationProductRepository.deleteByProductId(id);
    productRepository.deleteById(id);
  }

  @Transactional
  @Override public Discount changeDiscountToProduct(final long productId, final Discount discount) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Discount discountToSave = new
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Discount(
      discount.getDiscountSize(),
      discount.getDurationOfDiscount()
    );
    discountRepository.save(discountToSave);
    ProductDiscount productDiscount = new ProductDiscount(
      productId, discountRepository.findTopByOrderByIdDesc().getId());
    productDiscountRepository.save(productDiscount);

    Discount discountToReturn = new Discount(
      discountRepository.findTopByOrderByIdDesc().getId(),
      discountToSave.getDiscountSize(),
      discountToSave.getDuration()
    );
    return discountToReturn;
  }

  @Override public List<Product> getPurchasedProducts(final long userId) {
    List<com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product> products
      = productRepository.findAllPurchasedProducts(userId);
    List<Product> productList = new ArrayList<>();
    for (
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product product : products
    ) {
      productList.add(new Product(
        product.getId(),
        product.getProductName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars()
      ));
    }
    return productList;
  }

  @Override public Review addReview(final Review review) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductReview reviewToSave =
      new com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductReview(
        review.getUserId(),
        review.getProductId(),
        review.getReviewText(),
        review.getRating()
      );
    productReviewsRepository.save(reviewToSave);
    review.setId(productReviewsRepository.findTopByOrderByIdDesc().get().getId());
    return review;
  }

  @Override public Review updateProductAndReviewById(final Review review) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductReview reviewToSave =
      new com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductReview(
        review.getUserId(),
        review.getProductId(),
        review.getReviewText(),
        review.getRating()
      );
    reviewToSave.setId(review.getId());

    if (productReviewsRepository.findById(reviewToSave.getId()).isPresent()) {
      productReviewsRepository.save(reviewToSave);
      return review;
    } else {
      throw new RuntimeException("нельзя менять несуществующие ревью");
    }

  }


  @Override public Review getReviewById(final long reviewId) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductReview review =
      productReviewsRepository.getReferenceById(reviewId);
    return new Review(
      review.getId(),
      review.getUserId(),
      review.getProductId(),
      review.getReviewText(),
      review.getRating()
    );
  }

  @Override public void deleteReviewById(final long id, final long reviewId) {
    productReviewsRepository.deleteById(reviewId);
  }

  @Transactional
  @Override public Purchase addPurchase(final MakePurchaseDto makePurchaseDto) {
    log.info(makePurchaseDto.toString());
    userRepository.decrementUserBalanceByProductCost(makePurchaseDto.getUserId(),makePurchaseDto.getProductId());
    productRepository.updateProductQuantityByOne(makePurchaseDto.getProductId());
//    purchaseRepository.savePurchase(
//        makePurchaseDto.getProductId(),
//        makePurchaseDto.getUserId(),
//        makePurchaseDto.getPurchaseDate()
//    );
//    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase purchase = purchaseRepository.findTopByOrderByIdDesc().get();

    return new Purchase(
//      purchase.getId(),
//      purchase.getUserId(),
//      purchase.getProductId(),
//      purchase.isRefunded(),
//      purchase.getPurchaseDate()
      1L,
      1L,
      1L,
      false,
      LocalDate.now()
    );
  }

  @Override
  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User disableUser(long id) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserById(id)
      .get();
    user.setEnabled(true);
    userRepository.save(user);
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );
  }

  @Override
  public User changeUserBalance(long id, BigDecimal value) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository
      .findUserById(id)
      .get();
    user.setBalance(user.getBalance().add(value));
    userRepository.save(user);
    return new User(
      user.getId(),
      user.getUsername(),
      user.getMail(),
      user.getPassword(),
      user.getBalance(),
      user.isEnabled()
    );
  }

  @Override
  public Product getProductById(long id) {
    try {

      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product product = productRepository
        .findProductById(id)
        .get();
      return new Product(
        product.getId(),
        product.getProductName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars()
      );
    } catch (java.util.NoSuchElementException e) {
      return new Product();
    }
  }


  @Override
  @Transactional
  public Product addProduct(Product product, long organisationId) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product productToSave
      = persistantProductFromDomain(product);
    productRepository.save(productToSave);
    product.setId(productRepository.findTopByOrderByIdDesc().get().getId());
    organisationProductRepository.save(new OrganisationProduct(organisationId, product.getId()));
    return product;
  }

  @Transactional
  @Override
  public com.muz1kash1.webmarkettesttask.model.domain.Notion sendNotionToUser(final long userid,
                                                                              final NotionDto notionDto) {
    Notion notion = new Notion(
      notionDto.getHeader(),
      notionDto.getNotionDate(),
      notionDto.getNotionText()
    );
    notionsRepository.save(notion);
    userNotionsRepository.save(new UserNotions(
      userid,
      notion.getId()
    ));
    return new com.muz1kash1.webmarkettesttask.model.domain.Notion(
      notion.getId(),
      notion.getHeader(),
      notion.getNotionDate(),
      notion.getNotionText()
    );
  }


  private static com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product persistantProductFromDomain(
    final Product product) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product productToSave
      = new com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product(
      product.getName(),
      product.getDescription(),
      product.getOrganisationName(),
      product.getPrice(),
      product.getQuantity(),
      product.getKeywords(),
      product.getChars()
    );
    return productToSave;
  }

}

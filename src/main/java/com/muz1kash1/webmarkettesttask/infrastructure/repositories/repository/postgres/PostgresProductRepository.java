package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Organisation;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.OrganisationProduct;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.ProductDiscount;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.ProductReview;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.User;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IProductRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.DiscountRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.OrganisationProductRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.OrganisationRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.ProductDiscountRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.ProductReviewsRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres.jparepositories.UserRepository;
import com.muz1kash1.webmarkettesttask.model.domain.Discount;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Review;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Repository
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres")
public class PostgresProductRepository implements IProductRepo {
  private final com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.postgres
          .jparepositories.ProductRepository
      productRepository;
  private final OrganisationProductRepository organisationProductRepository;
  private final DiscountRepository discountRepository;
  private final ProductDiscountRepository productDiscountRepository;
  private final ProductReviewsRepository productReviewsRepository;
  private final UserRepository userRepository;
  private final OrganisationRepository organisationRepository;

  @Override
  public Product getProductById(long id) {
    try {

      com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product product =
          productRepository.findProductById(id).get();
      return new Product(
          product.getId(),
          product.getProductName(),
          product.getDescription(),
          product.getOrganisationName(),
          product.getPrice(),
          product.getQuantity(),
          product.getKeywords(),
          product.getChars());
    } catch (java.util.NoSuchElementException e) {
      return new Product();
    }
  }

  @Override
  @Transactional
  public Product addProduct(Product product, long organisationId, String username) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product
        productToSave = persistantProductFromDomain(product);

    User user = userRepository.findUserByUsername(username).get();
    List<Organisation> organisations =
        organisationRepository.findAllByOrganisationOwnerId(user.getId());
    if (!organisations.isEmpty()) {

      if (organisations.stream().anyMatch(organisation -> organisation.getId() == organisationId)) {
        productRepository.save(productToSave);
        product.setId(productRepository.findTopByOrderByIdDesc().get().getId());
        organisationProductRepository.save(
            new OrganisationProduct(organisationId, product.getId(), false));
        return product;
      } else
        throw new RuntimeException(
            "У этого пользователя нет доступа к добалению товаров от имени этой организации: "
                + organisationId);
    } else throw new RuntimeException("У текущего пользователя нет открытых организаций");
  }

  @Override
  public List<Product> getAllProducts() {
    List<com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product>
        products = productRepository.findAll();
    List<Product> productList = new ArrayList<>();
    for (com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product
        product : products) {
      productList.add(
          new Product(
              product.getId(),
              product.getProductName(),
              product.getDescription(),
              product.getOrganisationName(),
              product.getPrice(),
              product.getQuantity(),
              product.getKeywords(),
              product.getChars()));
    }
    return productList;
  }

  @Override
  public Product updateProductById(final long id, final Product product) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product
        productToSave =
            new com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product(
                product.getName(),
                product.getDescription(),
                product.getOrganisationName(),
                product.getPrice(),
                product.getQuantity(),
                product.getKeywords(),
                product.getChars());
    productToSave.setId(id);
    productRepository.save(productToSave);
    product.setId(id);
    return product;
  }

  @Transactional
  @Override
  public void deleteProductById(final long id) {
    productDiscountRepository.deleteProductDiscountByProductId(id);
    organisationProductRepository.deleteByProductId(id);
    productRepository.deleteById(id);
  }

  @Transactional
  @Override
  public Discount changeDiscountToProduct(final long productId, final Discount discount) {
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Discount
        discountToSave =
            new com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres
                .Discount(discount.getDiscountSize(), discount.getDurationOfDiscount());
    discountRepository.save(discountToSave);
    ProductDiscount productDiscount =
        new ProductDiscount(productId, discountRepository.findTopByOrderByIdDesc().getId());
    productDiscountRepository.save(productDiscount);

    return new Discount(
        discountRepository.findTopByOrderByIdDesc().getId(),
        discountToSave.getDiscountSize(),
        discountToSave.getDuration());
  }

  @Override
  public List<Product> getPurchasedProducts(final String username) {
    User user = userRepository.findUserByUsername(username).get();
    List<com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product>
        products = productRepository.findAllPurchasedProducts(user.getId());
    List<Product> productList = new ArrayList<>();
    for (com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product
        product : products) {
      productList.add(
          new Product(
              product.getId(),
              product.getProductName(),
              product.getDescription(),
              product.getOrganisationName(),
              product.getPrice(),
              product.getQuantity(),
              product.getKeywords(),
              product.getChars()));
    }
    return productList;
  }

  @Override
  public Review addReview(final Review review) {
    ProductReview reviewToSave =
        new ProductReview(
            review.getUserId(), review.getProductId(), review.getReviewText(), review.getRating());
    productReviewsRepository.save(reviewToSave);
    review.setId(productReviewsRepository.findTopByOrderByIdDesc().get().getId());
    return review;
  }

  @Override
  public Review updateProductAndReviewById(final Review review) {
    ProductReview reviewToSave =
        new ProductReview(
            review.getUserId(), review.getProductId(), review.getReviewText(), review.getRating());
    reviewToSave.setId(review.getId());

    if (productReviewsRepository.findById(reviewToSave.getId()).isPresent()) {
      productReviewsRepository.save(reviewToSave);
      return review;
    } else {
      throw new RuntimeException("нельзя менять несуществующие ревью");
    }
  }

  @Override
  public Review getReviewById(final long reviewId) {
    ProductReview review = productReviewsRepository.getReferenceById(reviewId);
    return new Review(
        review.getId(),
        review.getUserId(),
        review.getProductId(),
        review.getReviewText(),
        review.getRating());
  }

  @Override
  public void deleteReviewById(final long id, final long reviewId) {
    productReviewsRepository.deleteById(reviewId);
  }

  @Override
  public Long getIdOfLastReview() {
    if (productReviewsRepository.findTopByOrderByIdDesc().isPresent()) {
      return productReviewsRepository.findTopByOrderByIdDesc().get().getId();
    } else return 1L;
  }

  @Override
  public Product enableOrganisationProduct(final long id) {
    OrganisationProduct organisationProduct = organisationProductRepository.findByProductId(id);
    organisationProduct.setEnabled(true);
    organisationProductRepository.save(organisationProduct);
    com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product product =
        productRepository.findProductById(id).get();
    return new Product(
        product.getId(),
        product.getOrganisationName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars());
  }

  private static com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product
      persistantProductFromDomain(final Product product) {
    return new com.muz1kash1.webmarkettesttask.infrastructure.repositories.entity.postgres.Product(
        product.getName(),
        product.getDescription(),
        product.getOrganisationName(),
        product.getPrice(),
        product.getQuantity(),
        product.getKeywords(),
        product.getChars());
  }
}

package com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Organisation;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.OrganisationProduct;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.ProductDiscount;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IPurchaseRepo;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.DiscountRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.OrganisationProductRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.OrganisationRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.ProductDiscountRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.ProductRepository;
import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.UserRepository;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@ComponentScan("com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres")
@Repository
public class PostgresPurchaseRepository implements IPurchaseRepo {
  private final ProductDiscountRepository productDiscountRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final DiscountRepository discountRepository;
  private final OrganisationRepository organisationRepository;
  private final OrganisationProductRepository organisationProductRepository;
  private final com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.postgres.jparepositories.PurchaseRepository
    purchaseRepository;

  @Transactional
  @Override public Purchase addPurchase(final MakePurchaseDto makePurchaseDto) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Product product = productRepository
      .findProductById(makePurchaseDto.getProductId())
      .get();
    List<ProductDiscount> productDiscounts
      = productDiscountRepository.findByProductId(product.getId());
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Discount discount
      = discountRepository.getReferenceById(productDiscounts.get(0).getDiscountId());
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User user = userRepository.getReferenceById(
      makePurchaseDto.getUserId());
    OrganisationProduct organisationProduct = organisationProductRepository.findByProductId(product.getId());
    Organisation organisation = organisationRepository.getReferenceById(organisationProduct.getOrganisationId());
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.User userReceiver
      = userRepository.getReferenceById(organisation.getOrganisationOwnerId());
    log.info(userReceiver.toString());
    log.info(user.toString());

    BigDecimal price;
    if (LocalDate.now().isBefore(discount.getDuration())) {
      price = product.getPrice().multiply(BigDecimal.valueOf(discount.getDiscountSize()));
    } else {
      price = product.getPrice();
    }

    product.setQuantity(product.getQuantity() - 1);
    productRepository.save(product);

    BigDecimal userBalance = user.getBalance().subtract(price);
    log.info(userBalance.toString());
    user.setBalance(userBalance);
    userRepository.save(user);

    BigDecimal receiverIncome = price.multiply(BigDecimal.valueOf(0.95));
    BigDecimal userRecieverBalance = userReceiver.getBalance().add(receiverIncome);
    log.info(userRecieverBalance.toString());
    userReceiver.setBalance(userRecieverBalance);
    userRepository.save(userReceiver);

    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase purchase
      = new com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase(
      makePurchaseDto.getUserId(),
      makePurchaseDto.getProductId(),
      false,
      price,
      makePurchaseDto.getPurchaseDate()
    );
    purchaseRepository.save(purchase);
    purchase = purchaseRepository.findTopByOrderByIdDesc().get();
    return new Purchase(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }

  @Override
  public Purchase getPurchaseById(final long id) {
    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase purchase
      = purchaseRepository.getReferenceById(id);
    return new Purchase(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }

  @Override
  public List<Purchase> getPurchasesOfUser(final long Userid) {
    List<com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase> purchases
      = purchaseRepository.findAllByUserId(Userid);
    log.info("вот что вернулось с бека" + purchases.toString());
    List<Purchase> purchasesToReturn = new ArrayList<>();
    for (
      com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase purchase : purchases
    ) {
      purchasesToReturn.add(
        new Purchase(
          purchase.getId(),
          purchase.getUserId(),
          purchase.getProductId(),
          purchase.isRefunded(),
          purchase.getPrice(),
          purchase.getPurchaseDate()
        )
      );
    }
    return purchasesToReturn;
  }

  @Transactional
  @Override public Purchase refundPurchase(final long id) {

    com.muz1kash1.webmarkettesttask.infrastructure.persistent.postgres.Purchase purchase
      = purchaseRepository.getReferenceById(id);
    log.info(purchase.toString());
    purchase.setRefunded(true);
    log.info(purchase.toString());
    purchaseRepository.save(purchase);
    return new Purchase(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }

}

package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IPurchaseRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PurchaseService {
  private final IPurchaseRepo purchaseRepository;

  public PurchaseDto makePurchase(final MakePurchaseDto makePurchaseDto) {
    Purchase purchase = purchaseRepository.addPurchase(makePurchaseDto);
    return new PurchaseDto(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }


  public PurchaseDto getPurchase(final long id) {
    Purchase purchase = purchaseRepository.getPurchaseById(id);
    return new PurchaseDto(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }

  public List<PurchaseDto> getPurchasesOfUser(final long userId) {
    log.info("Зашло в контроллерный метод");
    List<Purchase> purchases = purchaseRepository.getPurchasesOfUser(userId);
    List<PurchaseDto> purchaseDtos = new ArrayList<>();
    for (
      Purchase purchase : purchases
    ) {
      purchaseDtos.add(
        new PurchaseDto(
          purchase.getId(),
          purchase.getUserId(),
          purchase.getProductId(),
          purchase.isRefunded(),
          purchase.getPrice(),
          purchase.getPurchaseDate()
        )
      );
    }
    return purchaseDtos;
  }

  public PurchaseDto refundPurchase(final long id) {
    Purchase purchase = purchaseRepository.refundPurchase(id);
    return new PurchaseDto(
      purchase.getId(),
      purchase.getUserId(),
      purchase.getProductId(),
      purchase.isRefunded(),
      purchase.getPrice(),
      purchase.getPurchaseDate()
    );
  }
}

package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository.IPurchaseRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PurchaseService {
  private final IPurchaseRepo purchaseRepository;

  public PurchaseDto makePurchase(final MakePurchaseDto makePurchaseDto, String name)throws ChangeSetPersister.NotFoundException {
    Purchase purchase = purchaseRepository.addPurchase(makePurchaseDto, name);
    return new PurchaseDto(
        purchase.getId(),
        purchase.getUserId(),
        purchase.getProductId(),
        purchase.isRefunded(),
        purchase.getPrice(),
        purchase.getPurchaseDate());
  }

  public PurchaseDto getPurchase(final long id) {
    Purchase purchase = purchaseRepository.getPurchaseById(id);
    return new PurchaseDto(
        purchase.getId(),
        purchase.getUserId(),
        purchase.getProductId(),
        purchase.isRefunded(),
        purchase.getPrice(),
        purchase.getPurchaseDate());
  }

  public List<PurchaseDto> getPurchasesOfUser(final long userId) {
    List<Purchase> purchases = purchaseRepository.getPurchasesOfUser(userId);
    List<PurchaseDto> purchaseDtos = new ArrayList<>();
    for (Purchase purchase : purchases) {
      purchaseDtos.add(
          new PurchaseDto(
              purchase.getId(),
              purchase.getUserId(),
              purchase.getProductId(),
              purchase.isRefunded(),
              purchase.getPrice(),
              purchase.getPurchaseDate()));
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
        purchase.getPurchaseDate());
  }

  public List<PurchaseDto> getPurchasesOfUserByUsername(final String name)throws ChangeSetPersister.NotFoundException {
    List<Purchase> purchases = purchaseRepository.getPurchasesOfUserByUsername(name);
    List<PurchaseDto> purchaseDtos = new ArrayList<>();
    for (Purchase purchase : purchases) {
      purchaseDtos.add(
          new PurchaseDto(
              purchase.getId(),
              purchase.getUserId(),
              purchase.getProductId(),
              purchase.isRefunded(),
              purchase.getPrice(),
              purchase.getPurchaseDate()));
    }
    return purchaseDtos;
  }
}

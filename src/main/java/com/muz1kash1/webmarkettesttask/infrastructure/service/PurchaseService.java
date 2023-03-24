package com.muz1kash1.webmarkettesttask.infrastructure.service;

import com.muz1kash1.webmarkettesttask.infrastructure.persistent.repository.IStoreRepo;
import com.muz1kash1.webmarkettesttask.model.domain.Product;
import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.domain.User;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PurchaseService {
  private final IStoreRepo marketRepository;

  // TODO когда срабатывает в таблице purchase создается новая запись в которой
  // TODO user_id, product_id ,purchase_date из пришедшего дто
  // TODO price подтягивается из таблицы products refunded по дефолту false
  // TODO при срабатывании у записи из таблицы users balance уменьшается на величину price
  // TODO quantity в таблице products уменьшается на один
  public PurchaseDto makePurchase(final MakePurchaseDto makePurchaseDto) {
      User user = marketRepository.getUserById(makePurchaseDto.getUserId());
      Product product = marketRepository.getProductById(makePurchaseDto.getProductId());
      if (product.getPrice().compareTo(user.getBalance()) < 0&&
         product.getQuantity() >=1){
        Purchase purchase = marketRepository.addPurchase(makePurchaseDto);
        return new PurchaseDto(purchase.getId(),
          purchase.getUserId(),
          purchase.getProductId(),
          purchase.isRefunded(),
          purchase.getPurchaseDate());
      }
      throw new RuntimeException("Баланс меньше цены");
  }
}

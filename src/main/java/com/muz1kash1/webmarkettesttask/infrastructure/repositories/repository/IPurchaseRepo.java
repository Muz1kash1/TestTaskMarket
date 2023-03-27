package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import java.util.List;

public interface IPurchaseRepo {
  Purchase addPurchase(MakePurchaseDto makePurchaseDto);

  Purchase getPurchaseById(long id);

  List<Purchase> getPurchasesOfUser(long id);

  Purchase refundPurchase(long id);

}

package com.muz1kash1.webmarkettesttask.infrastructure.repositories.repository;

import com.muz1kash1.webmarkettesttask.model.domain.Purchase;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface IPurchaseRepo {
  Purchase addPurchase(MakePurchaseDto makePurchaseDto, String username)throws ChangeSetPersister.NotFoundException;

  Purchase getPurchaseById(long id);

  List<Purchase> getPurchasesOfUser(long id);

  Purchase refundPurchase(long id);

  List<Purchase> getPurchasesOfUserByUsername(String name)throws ChangeSetPersister.NotFoundException;
}

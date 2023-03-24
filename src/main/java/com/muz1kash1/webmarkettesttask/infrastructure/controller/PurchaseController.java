package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.PurchaseService;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class PurchaseController {
  private final PurchaseService purchaseService;
  @PostMapping("/purchases")
  public void purchaseProduct(@RequestBody @Valid MakePurchaseDto makePurchaseDto) {
      purchaseService.makePurchase(makePurchaseDto);
  }

  @GetMapping("/purchases/{id}")
  public ResponseEntity<PurchaseDto> getPurchase(@PathVariable long id) {
    return null;
  }

  @PutMapping("/purchases/{id}/refund")
  public ResponseEntity<PurchaseDto> requestRefund(@PathVariable long id) {
    return null;
  }

}

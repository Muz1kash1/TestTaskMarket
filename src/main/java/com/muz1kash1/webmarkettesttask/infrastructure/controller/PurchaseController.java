package com.muz1kash1.webmarkettesttask.infrastructure.controller;

import com.muz1kash1.webmarkettesttask.infrastructure.service.PurchaseService;
import com.muz1kash1.webmarkettesttask.model.dto.MakePurchaseDto;
import com.muz1kash1.webmarkettesttask.model.dto.PurchaseDto;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PurchaseController {
  private final PurchaseService purchaseService;

  @PostMapping("/purchases")
  public ResponseEntity<PurchaseDto> purchaseProduct(
      @RequestBody @Valid MakePurchaseDto makePurchaseDto, JwtAuthenticationToken principle) {
    PurchaseDto purchaseDto = purchaseService.makePurchase(makePurchaseDto, principle.getName());
    return ResponseEntity.ok().body(purchaseDto);
  }

  @GetMapping("/users/{id}/purchaseshistory")
  public ResponseEntity<List<PurchaseDto>> getPurchasesOfUser(@PathVariable long id) {
    return ResponseEntity.ok().body(purchaseService.getPurchasesOfUser(id));
  }

  @GetMapping("/purchases/{id}")
  public ResponseEntity<PurchaseDto> getPurchase(@PathVariable long id) {
    return ResponseEntity.ok().body(purchaseService.getPurchase(id));
  }

  @PutMapping("/purchases/{id}/refund")
  public ResponseEntity<PurchaseDto> requestRefund(@PathVariable long id) {
    return ResponseEntity.ok().body(purchaseService.refundPurchase(id));
  }

  @GetMapping("/purchases/history")
  public ResponseEntity<List<PurchaseDto>> getPurchasesOfAuthorizedUser(
      JwtAuthenticationToken principal) {
    return ResponseEntity.ok()
        .body(purchaseService.getPurchasesOfUserByUsername(principal.getName()));
  }
}
